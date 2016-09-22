package io.dangernoodle.slack.objects;

/**
 * This class is used to represent <code>direct messages</code> between another user
 *
 * @since 0.1.0
 */
public class SlackDirectMessage extends SlackMessageable
{
    private boolean isOpen;

    private SlackUser.Id user;

    public boolean isOpen()
    {
        return isOpen;
    }

    public SlackUser.Id getUser()
    {
        return user;
    }
}
