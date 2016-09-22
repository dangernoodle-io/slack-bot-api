package io.dangernoodle.slack.events;

import java.util.Map;


/**
 * Represents a <code>pong</code> event
 *
 * @since 0.1.0
 */
public class SlackPongEvent extends SlackEvent
{
    private final Map<String, String> additional;

    private final long id;

    private final long time;

    public SlackPongEvent(long id, long time, Map<String, String> additional)
    {
        this.id = id;
        this.time = time;

        this.additional = additional;
        setType(SlackEventType.PONG);
    }

    /**
     * @return any additional arguments configured for <code>ping</code> requests via <code>SlackClientSettings</code>
     * @see io.dangernoodle.slack.client.SlackClientSettings
     */
    public Map<String, String> getAdditional()
    {
        return additional;
    }

    /**
     * @return ping id
     */
    public long getId()
    {
        return id;
    }

    /**
     * @return timestamp when ping was sent
     */
    public long getTime()
    {
        return time;
    }
}
