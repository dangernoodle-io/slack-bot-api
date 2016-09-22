package io.dangernoodle.slack.events.user;

import io.dangernoodle.slack.events.SlackEvent;
import io.dangernoodle.slack.objects.SlackUser;


/**
 * Represents a <code>user_change</code> event
 *
 * @since 0.1.0
 */
public class SlackUserChangeEvent extends SlackEvent
{
    private SlackUser user;

    public SlackUser getUser()
    {
        return user;
    }
}
