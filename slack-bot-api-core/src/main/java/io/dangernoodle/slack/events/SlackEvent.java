package io.dangernoodle.slack.events;

public abstract class SlackEvent
{
    private SlackEventType type;

    public SlackEventType getType()
    {
        return type;
    }

    protected void setType(SlackEventType type)
    {
        this.type = type;
    }
}
