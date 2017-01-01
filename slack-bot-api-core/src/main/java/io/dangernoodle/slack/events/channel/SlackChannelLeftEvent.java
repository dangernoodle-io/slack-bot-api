package io.dangernoodle.slack.events.channel;

import io.dangernoodle.slack.events.SlackEvent;
import io.dangernoodle.slack.objects.SlackChannel;


/**
 * Represents a <code>channel_left</code> or <code>group_left<code> event
 *
 * @since 0.1.0
 */
public class SlackChannelLeftEvent extends SlackEvent
{
    private SlackChannel.Id channel;

    private String eventTs;

    public SlackChannel.Id getChannel()
    {
        return channel;
    }

    public String getEventTimestamp()
    {
        return eventTs;
    }
}
