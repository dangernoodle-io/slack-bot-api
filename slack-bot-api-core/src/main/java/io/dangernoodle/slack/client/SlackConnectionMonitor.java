package io.dangernoodle.slack.client;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


class SlackConnectionMonitor
{
    private static final Logger logger = LoggerFactory.getLogger(SlackConnectionMonitor.class);

    private final SlackClient client;

    private final ScheduledExecutorService executorService;

    private ScheduledFuture<?> future;

    private volatile int heartbeat;

    private long lastPingId;

    private volatile boolean reconnect;

    SlackConnectionMonitor(SlackClient client, int heartbeat, boolean reconnect)
    {
        this.client = client;
        this.heartbeat = heartbeat;
        this.reconnect = reconnect;

        this.executorService = createExecutorService();
    }

    public int getHeartbeat()
    {
        return heartbeat;
    }

    public boolean getReconnect()
    {
        return reconnect;
    }

// TODO: jmx support
//    public void adjustHeartbeat(int heartbeat)
//    {
//        this.heartbeat = heartbeat;
//
//        stop();
//        start();
//    }

    @Override
    protected void finalize() throws Throwable
    {
        executorService.shutdownNow();
        super.finalize();
    }

    ScheduledExecutorService createExecutorService()
    {
        return Executors.newSingleThreadScheduledExecutor(this::createThread);
    }

    // visible for testing
    long getLastPingId()
    {
        return lastPingId;
    }

    void run()
    {
        if (client.isConnected() && lastPingId == client.getSession().getLastPingId())
        {
            lastPingId = client.sendPing();
            logger.trace("connection ok, ping sent - id [{}]", lastPingId);
        }
        else if (reconnect)
        {
            logger.trace("issue with connection, reconnecting...");
            reconnect();
        }
        else
        {
            logger.warn("disconnected from slack, reconnect not enabled");
        }
    }

    synchronized void start()
    {
        logger.info("heartbeat thread started at interval of [{}] seconds", heartbeat);
        future = executorService.scheduleAtFixedRate(this::run, 0, heartbeat, TimeUnit.SECONDS);
    }

    synchronized void stop()
    {
        if (future != null)
        {
            future.cancel(true);
            future = null;

            logger.info("hearbeat thread stopped");
        }
    }

    private Thread createThread(Runnable runnable)
    {
        Thread thread = new Thread(runnable);
        thread.setName("Slack-Hearbeat");

        return thread;
    }

    private void reconnect()
    {
        try
        {
            lastPingId = client.reconnect();
            logger.trace("recconnected, ping id [{}]", lastPingId);
        }
        catch (IOException e)
        {
            logger.warn("reconnection failed", e);
        }
    }
}
