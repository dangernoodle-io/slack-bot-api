package io.dangernoodle.slack.events.channel;

import io.dangernoodle.slack.events.SlackEvent;
import io.dangernoodle.slack.objects.SlackChannel;


/**
 * Represents a <code>channel_created</code> event
 *
 * @since 0.1.0
 */
public class SlackChannelCreatedEvent extends SlackEvent
{
    private SlackChannel channel;

    public SlackChannel getChannel()
    {
        return channel;
    }
}
