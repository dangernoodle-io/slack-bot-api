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
import io.dangernoodle.slack.client.web.SlackWebClient;
import io.dangernoodle.slack.events.SlackEventType;
import io.dangernoodle.slack.objects.SlackMessageable;
import io.dangernoodle.slack.objects.SlackUser;
import io.dangernoodle.slack.objects.api.SlackPostMessage;
import io.dangernoodle.slack.objects.api.SlackStartRtmResponse;
import io.dangernoodle.slack.objects.api.SlackWebResponse;


/**
 * Slack Client - It all happens here!
 *
 * @since 0.1.0
 */
public class SlackClient
{
    private static final Logger logger = LoggerFactory.getLogger(SlackClient.class);

    private final AtomicLong messageId;

    private final SlackConnectionMonitor monitor;

    private final SlackObserverRegistry registry;

    private final SlackWebSocketClient rtmClient;

    private final SlackConnectionSession session;

    private final SlackClientSettings settings;

    private final SlackWebClient webClient;

    SlackClient(SlackClientBuilder builder)
    {
        this.messageId = new AtomicLong();
        this.session = createConnectionSession();

        /*-
         * this is a little weird - right now the 'SlackWebSocketAssistant' is created in the builder as
         * it needs the json transformer, etc and also contains all the logic for dispatching events
         *
         * in the future that may get extracted, this method will most likely aways need something
         * passed from this class in order to link things together.
         */
        this.rtmClient = builder.getRtmClient(this);
        this.webClient = builder.getWebClient();

        this.settings = builder.getClientSettings();

        this.monitor = createConnectionMonitor();
        this.registry = createObserverRegistry();

        registerObservers();
    }

    /**
     * Connect to slack
     */
    public void connect()
    {
        monitor.start();
    }

    /**
     * Disconnect from slack
     */
    public void disconnet()
    {
        try
        {
            monitor.stop();
            rtmClient.disconnect();
        }
        catch (IOException e)
        {
            logger.warn("unexpected error disconnecting", e);
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

    public SlackWebClient getWebClient()
    {
        return webClient;
    }

    public SlackUser getSelfUser()
    {
        return session.getSelfUser();
    }

    public boolean isConnected()
    {
        return rtmClient.isConnected();
    }

    // TODO: typing indicator

    /**
     * Send a simple message over the websocket
     */
    public long send(SlackMessageable.Id id, String text) throws UncheckedIOException
    {
        return send(new SimpleMessage(nextMessageId(), id.value(), text)).id;
    }

    public long send(SlackMessageable.Id id, String format, Object... args) throws UncheckedIOException
    {
        return send(id, String.format(format, args));
    }

    /**
     * Send a message with an attachment using the web api
     */
    public SlackWebResponse send(SlackMessageable.Id id, SlackPostMessage.Builder builder) throws UncheckedIOException
    {
        try
        {
            return webClient.send(id, builder);
        }
        catch (IOException e)
        {
            throw new UncheckedIOException(e);
        }
    }

    // visible for testing
    SlackConnectionMonitor createConnectionMonitor()
    {
        return new SlackConnectionMonitor(this, settings.getHeartbeat(), settings.reconnect());
    }

    // visible for testing
    SlackConnectionSession createConnectionSession()
    {
        return new SlackConnectionSession();
    }

    // visible for testing
    SlackObserverRegistry createObserverRegistry()
    {
        return new SlackObserverRegistry();
    }

    long reconnect() throws ConnectException, IOException
    {
        logger.trace("initiating slack rtm initiation request...");
        SlackStartRtmResponse response = webClient.initiateRtmConnection();

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

        if (!rtmClient.isConnected())
        {
            throw new ConnectException("websocket is not connected");
        }

        long nextMessageId = nextMessageId();
        session.updateLastPingId(nextMessageId);

        return nextMessageId;
    }

    long sendPing()
    {
        // ok b/c it's always a copy
        Map<String, String> args = settings.pingArgs();

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
        registry.addHelloObserver(SlackSessionObservers.helloObserver);
        registry.addPongObserver(SlackSessionObservers.pongObserver);

        // session channel updates
        registry.addGroupLeftObserver(SlackSessionObservers.groupLeftObserver);
        registry.addGroupJoinedObserver(SlackSessionObservers.groupJoinedObserver);
        registry.addChannelCreatedObserver(SlackSessionObservers.channelCreatedObserver);

        // user updates
        registry.addUserChangeObserver(SlackSessionObservers.userChangedObserver);
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

    static class SimpleMessage
    {
        final String channel;

        final long id;

        final String text;

        final String type;

        SimpleMessage(long id, String channel, String text)
        {
            this.id = id;
            this.channel = channel;
            this.text = text;
            this.type = SlackEventType.MESSAGE.toType();
        }
    }
}
