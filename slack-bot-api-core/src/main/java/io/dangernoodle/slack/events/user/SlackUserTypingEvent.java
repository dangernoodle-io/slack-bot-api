package io.dangernoodle.slack.events.user;

import io.dangernoodle.slack.events.SlackEvent;
import io.dangernoodle.slack.objects.SlackMessageable;
import io.dangernoodle.slack.objects.SlackUser;


/**
 * Represents a <code>user_typing</code> event
 *
 * @since 0.1.0
 */
public class SlackUserTypingEvent extends SlackEvent
{
    private SlackMessageable.Id channel;

    private SlackUser.Id user;

    public SlackMessageable.Id getChannel()
    {
        return channel;
    }

    public SlackUser.Id getUser()
    {
        return user;
    }
}
