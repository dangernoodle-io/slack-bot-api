package io.dangernoodle.slack.objects;

/**
 * Represents the message portions of the <code>message</code> event.
 *
 * @since 0.1.0
 */
public class SlackMessage
{
    private SlackChannel.Id channel;

    private String deletedTs;

    private String eventTs;

    private boolean hidden;

    private SlackUser.Id inviter;

    private SlackMessage message;

    private SlackMessage previousMessage;

    private SlackTeam.Id team;

    private String text;

    private String ts;

    private SlackUser.Id user;

    public SlackChannel.Id getChannel()
    {
        return channel;
    }

    public String getDeletedTimestamp()
    {
        return deletedTs;
    }

    public SlackMessage getEditted()
    {
        return message;
    }

    public String getEventTimestamp()
    {
        return eventTs;
    }

    public SlackUser.Id getInviter()
    {
        return inviter;
    }

    public SlackMessage getPrevious()
    {
        return previousMessage;
    }

    public SlackTeam.Id getTeam()
    {
        return team;
    }

    public String getText()
    {
        return text;
    }

    public String getTimestamp()
    {
        return ts;
    }

    public SlackUser.Id getUser()
    {
        return user;
    }

    public boolean isHidden()
    {
        return hidden;
    }
}
