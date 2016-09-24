package io.dangernoodle.slack.client;

import static io.dangernoodle.slack.client.SlackJsonTransformer.ID;
import static io.dangernoodle.slack.client.SlackJsonTransformer.PING;
import static io.dangernoodle.slack.client.SlackJsonTransformer.TIME;
import static io.dangernoodle.slack.client.SlackJsonTransformer.TYPE;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ConnectException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dangernoodle.slack.client.rtm.SlackObserverRegistry;
import io.dangernoodle.slack.client.rtm.SlackWebSocketClient;
import io.dangernoodle.slack.client.web.SlackWebApiClient;
import io.dangernoodle.slack.objects.SlackMessageable;
import io.dangernoodle.slack.objects.SlackStartBotResponse;


public class SlackClient
{
    private static final Logger logger = LoggerFactory.getLogger(SlackClient.class);

    private final SlackWebApiClient apiClient;

    private final AtomicLong messageId;

    private final SlackConnectionMonitor monitor;

    private final SlackObserverRegistry registry;

    private final SlackWebSocketClient rtmClient;

    private final SlackConnectionSession session;

    private final SlackClientSettings settings;

    public SlackClient(SlackClientBuilder builder)
    {
        this.session = new SlackConnectionSession();
        this.messageId = new AtomicLong();

        /*-
         * this is a little weird - right now the 'SlackRtmAssistant' is created in the builder as
         * it needs the json transformer, etc and also contains all the logic for dispatching events
         *
         * in the future that may get extracted, this method will most likely aways need something
         * passed from this class in order to link things together.
         */
        this.apiClient = builder.getWebClient();
        this.rtmClient = builder.getRtmClient(this);

        this.settings = builder.getClientSettings();

        this.monitor = createConnectionMonitor();
        this.registry = createObserverRegistry();

        registerObservers();
    }

    public void connect()
    {
        monitor.start();
    }

    public void disconnet() throws UncheckedIOException
    {
        try
        {
            monitor.stop();
            rtmClient.disconnect();
        }
        catch (IOException e)
        {
            throw new UncheckedIOException(e);
        }
    }

    public SlackObserverRegistry getObserverRegistry()
    {
        return registry;
    }

    public SlackConnectionSession getSession()
    {
        return session;
    }

    public boolean isConnected()
    {
        return rtmClient.isConnected();
    }

    public long send(SlackMessageable.Id id, String text) throws UncheckedIOException
    {
        return send(new SimpleMessage(id.value(), text)).id;
    }

    SlackConnectionMonitor createConnectionMonitor()
    {
        return new SlackConnectionMonitor(this, settings.getHeartbeat(), settings.getReconnect());
    }

    SlackObserverRegistry createObserverRegistry()
    {
        return new SlackObserverRegistry();
    }

    SlackWebSocketClient createRtmClient(SlackClientBuilder builder)
    {

        return builder.getRtmClient(this);
    }

    SlackWebApiClient createWebClient(SlackClientBuilder builder)
    {
        return builder.getWebClient();
    }

    long reconnect() throws ConnectException, IOException
    {
        logger.trace("initiating slack rtm initiation request...");
        SlackStartBotResponse response = apiClient.initiateRtmConnection();

        if (!response.isOk())
        {
            String error = response.getError();
            logger.error("failed to establish rtm session to slack: {}", error);

            throw new ConnectException(error);
        }

        synchronized (this)
        {
            session.updateSession(response);
            rtmClient.connect(response.getUrl());
        }

        if (rtmClient.isConnected())
        {
            // TODO: dispatch a "slack connected" event w/ 'SlackSelf'
            logger.info("slack session established!");
        }

        long nextMessageId = nextMessageId();
        session.updateLastPingId(nextMessageId);

        return nextMessageId;
    }

    long sendPing()
    {
        // ok b/c it's always a copy
        Map<String, String> args = settings.getPingArgs();

        args.put(TYPE, PING);
        args.put(ID, String.valueOf(nextMessageId()));
        args.put(TIME, String.valueOf(System.currentTimeMillis()));

        return Long.valueOf(send(args).get(ID)).longValue();
    }

    private long nextMessageId()
    {
        return messageId.incrementAndGet();
    }

    private void registerObservers()
    {
        registry.addPongObserver(SlackSessionObservers.pongObserver);

        // session channel updates
        registry.addGroupJoinedObserver(SlackSessionObservers.groupJoinedObserver);
        registry.addChannelCreatedObserver(SlackSessionObservers.channelCreatedObserver);

        // session user updates
        // registry.add
    }

    private <T> T send(T object) throws UncheckedIOException
    {
        try
        {
            rtmClient.send(object);
            return object;
        }
        catch (IOException e)
        {
            throw new UncheckedIOException(e);
        }
    }

    @SuppressWarnings("unused")
    private class SimpleMessage
    {
        private final String channel;

        private final long id = nextMessageId();

        private final String text;

        SimpleMessage(String channel, String text)
        {
            this.channel = channel;
            this.text = text;
        }
    }
}
