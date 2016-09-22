package io.dangernoodle.slack.events.user;

import io.dangernoodle.slack.events.SlackEvent;
import io.dangernoodle.slack.objects.SlackPresence;
import io.dangernoodle.slack.objects.SlackUser;


/**
 * Represents a <code>presence_change</code> event
 *
 * @since 0.1.0
 */
public class SlackPresenceChangeEvent extends SlackEvent
{
    private SlackPresence presence;

    private SlackUser.Id user;

    public SlackPresence getPresence()
    {
        return presence;
    }

    public SlackUser.Id getUser()
    {
        return user;
    }
}
