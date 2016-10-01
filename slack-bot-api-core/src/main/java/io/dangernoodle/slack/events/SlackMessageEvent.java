package io.dangernoodle.slack.events;

import io.dangernoodle.slack.objects.SlackMessage;


/**
 * Represents a <code>message</code> event.
 * <p>
 * This class deviates slightly from Slack's model by encapsulating all message related data inside a
 * <code>SlackMessage</code>
 * </p>
 *
 * @since 0.1.0
 */
public class SlackMessageEvent extends SlackEvent
{
    private SlackMessage message;

    private SlackMessageEventType subtype;

    public SlackMessageEvent(SlackMessage message, SlackMessageEventType subtype)
    {
        this.message = message;
        this.subtype = subtype;

        setType(SlackEventType.MESSAGE);
    }

    public SlackMessage getMessage()
    {
        return message;
    }

    /**
     * Returns the message subtype.
     * <p>
     * Note: this method will return <code>null</code> if
     * <code>SlackClientSettings.dispatchMessageSubtypes(boolean)</code> is disabled.
     * </p>
     */
    public SlackMessageEventType getSubType()
    {
        return subtype;
    }
}
