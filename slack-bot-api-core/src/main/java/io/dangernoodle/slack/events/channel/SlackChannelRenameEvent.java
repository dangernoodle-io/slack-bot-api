package io.dangernoodle.slack.events.channel;

import io.dangernoodle.slack.events.SlackEvent;
import io.dangernoodle.slack.events.SlackEventType;
import io.dangernoodle.slack.objects.SlackMessageable;


/**
 * Represents a <code>channel_rename</code> event
 *
 * @since 0.1.0
 */
public class SlackChannelRenameEvent extends SlackEvent
{
    private final long created;

    private final SlackMessageable.Id id;

    private final String name;

    public SlackChannelRenameEvent(SlackMessageable.Id id, String name, long created)
    {
        this.id = id;
        this.name = name;

        this.created = created;
        setType(SlackEventType.CHANNEL_RENAME);
    }

    public long getCreated()
    {
        return created;
    }

    public SlackMessageable.Id getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }
}
