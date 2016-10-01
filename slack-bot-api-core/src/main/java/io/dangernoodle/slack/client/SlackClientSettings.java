package io.dangernoodle.slack.client;

import java.util.HashMap;
import java.util.Map;


/**
 * Configure the <code>SlackClient</code>
 *
 * @since 0.1.0
 */
public class SlackClientSettings
{
    private String authToken;

    private boolean dispatchMessageSubtypes;

    private boolean filterSelfMessages;

    private int heartbeat = 15;

    private final Map<String, String> pingArgs;

    private boolean reconnect;

    public SlackClientSettings(String authToken)
    {
        this.authToken = authToken;
        this.reconnect = true;

        this.dispatchMessageSubtypes = true;
        this.pingArgs = new HashMap<>();
    }

    public boolean dispatchMessageSubtypes()
    {
        return dispatchMessageSubtypes;
    }

    /**
     * Toggle dispatching message subtypes to individual handlers
     * <p>
     * Default is <code>true</code> - if <code>false</code>, all messages will be dispatched to the
     * <code>message posted</code> event observer.
     * <p>
     */
    public SlackClientSettings dispatchMessageSubtypes(boolean dispatch)
    {
        this.dispatchMessageSubtypes = dispatch;
        return this;
    }

    public boolean filterSelfMessages()
    {
        return filterSelfMessages;
    }

    /**
     * Toggle automatic filtering of messages posted by the bot
     * <p>
     * Default is <code>true</code>
     * <p>
     */
    public SlackClientSettings filterSelfMessages(boolean filterSelfMessages)
    {
        this.filterSelfMessages = filterSelfMessages;
        return this;
    }

    public String getAuthToken()
    {
        return authToken;
    }

    public int getHeartbeat()
    {
        return heartbeat;
    }

    public SlackClientSettings heartbeat(int heartbeat) throws IllegalArgumentException
    {
        if (heartbeat < 1)
        {
            throw new IllegalArgumentException("heartbeat must be greater then 0");
        }

        this.heartbeat = heartbeat;
        return this;
    }

    /**
     * Additional <code>name</code>/<code>value</code> pairs that will be sent with <code>ping</code> requests.
     */
    public SlackClientSettings pingArg(String name, String value)
    {
        pingArgs.put(name, value);
        return this;
    }

    public Map<String, String> pingArgs()
    {
        // always a copy
        return new HashMap<>(pingArgs);
    }

    public boolean reconnect()
    {
        return reconnect;
    }

    public SlackClientSettings reconnect(boolean reconnect)
    {
        this.reconnect = reconnect;
        return this;
    }
}
