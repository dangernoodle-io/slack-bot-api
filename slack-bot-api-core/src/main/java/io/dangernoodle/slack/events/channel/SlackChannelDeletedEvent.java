package io.dangernoodle.slack.events.channel;

import io.dangernoodle.slack.events.SlackEvent;
import io.dangernoodle.slack.objects.SlackMessageable;


/**
 * Represents a <code>channel_deleted</code> event
 *
 * @since 0.1.0
 */
public class SlackChannelDeletedEvent extends SlackEvent
{
    private SlackMessageable.Id channel;

    public SlackMessageable.Id getChannel()
    {
        return channel;
    }
}
