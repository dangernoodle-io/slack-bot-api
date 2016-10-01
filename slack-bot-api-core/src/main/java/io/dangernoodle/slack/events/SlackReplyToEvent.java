package io.dangernoodle.slack.events;

import io.dangernoodle.slack.objects.SlackError;


public class SlackReplyToEvent extends SlackEvent
{
    private SlackError error;

    private boolean ok;

    private long replyTo;

    private String text;

    private String ts;

    public SlackReplyToEvent()
    {
        setType(SlackEventType.REPLY_TO);
    }

    public SlackError getError()
    {
        return error;
    }

    public long getReplyTo()
    {
        return replyTo;
    }

    public String getText()
    {
        return text;
    }

    public String getTimestamp()
    {
        return ts;
    }

    public boolean isOk()
    {
        return ok;
    }
}
