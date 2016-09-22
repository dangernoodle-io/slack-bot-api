package io.dangernoodle.slack.client;

import java.util.HashMap;
import java.util.Map;


public class SlackClientSettings
{
    private String authToken;

    private boolean dispatchMessageSubtypes;

    private boolean filterSelfMessages;

    private int heartbeat = 30;

    private Map<String, String> pingArgs = new HashMap<>();

    private boolean reconnect;

    public SlackClientSettings(String authToken)
    {
        this.authToken = authToken;
    }

    public SlackClientSettings addPingArg(String name, String value)
    {
        pingArgs.put(name, value);
        return this;
    }

    public int getHeartbeat()
    {
        return heartbeat;
    }

    public Map<String, String> getPingArgs()
    {
        // always a copy
        return new HashMap<>(pingArgs);
    }

    public boolean getReconnect()
    {
        return reconnect;
    }

    public boolean isDispatchMessageSubtypes()
    {
        return dispatchMessageSubtypes;
    }

    public boolean isFilterSelfMessages()
    {
        return filterSelfMessages;
    }

    public SlackClientSettings setDispatchMessageSubtypes(boolean dispatch)
    {
        this.dispatchMessageSubtypes = dispatch;
        return this;
    }

    public SlackClientSettings setFilterSelfMessages(boolean filterSelfMessages)
    {
        this.filterSelfMessages = filterSelfMessages;
        return this;
    }

    public SlackClientSettings setHeartbeat(int heartbeat)
    {
        this.heartbeat = heartbeat;
        return this;
    }

    public SlackClientSettings setReconnect(boolean reconnect)
    {
        this.reconnect = reconnect;
        return this;
    }

    public String getAuthToken()
    {
        return authToken;
    }
}
