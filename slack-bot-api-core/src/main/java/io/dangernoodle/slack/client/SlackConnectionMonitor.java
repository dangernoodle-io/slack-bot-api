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

    private int heartbeat;

    private long lastPingId;

    private boolean reconnect;

    private final Runnable runnable;

    SlackConnectionMonitor(SlackClient client, int heartbeat, boolean reconnect)
    {
        this.client = client;
        this.heartbeat = heartbeat;
        this.reconnect = reconnect;

        this.runnable = new MonitorRunnable();
        this.executorService = createExecutorService();
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

    void start()
    {
        future = executorService.scheduleAtFixedRate(runnable, heartbeat, heartbeat, TimeUnit.SECONDS);
        logger.info("heartbeat thread started at interval of [{}] seconds", heartbeat);
    }

    void stop()
    {
        future.cancel(true);
        logger.info("hearbeat thread stopped");
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
            client.reconnect();
        }
        catch (IOException e)
        {
            logger.warn("reconnection failed", e);
        }
    }

    private class MonitorRunnable implements Runnable
    {
        @Override
        public void run()
        {
            if (client.isConnected() && lastPingId == client.getSession().getLastPingId())
            {
                lastPingId = client.sendPing();
            }
            else if (reconnect)
            {
                reconnect();
            }
            else
            {
                logger.warn("disconnected from slack, reconnect not enabled");
            }
        }
    }
}
