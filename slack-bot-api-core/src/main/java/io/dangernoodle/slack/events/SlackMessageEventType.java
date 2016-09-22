package io.dangernoodle.slack.events;

/**
 * Posted message event sub-types
 *
 * @see <a href="https://api.slack.com/events/message">https://api.slack.com/events/message</a>
 */
public enum SlackMessageEventType
{
    /** message posted by an integration */
    BOT_MESSAGE,
    /** channel was archived */
    CHANNEL_ARCHIVE,
    /** user joined a channel */
    CHANNEL_JOIN,
    /** user left a channel */
    CHANNEL_LEAVE,
    /** channel was renamed */
    CHANNEL_NAME,
    /** channel purpose updated */
    CHANNEL_PURPOSE,
    /** channel topic was updated */
    CHANNEL_TOPIC,
    /** channel was unarchived */
    CHANNEL_UNARCHIVE,
    /** comment added to file */
    FILE_COMMENT,
    /** file mentioned in channel */
    FILE_MENTION,
    /** file shared in channel */
    FILE_SHARE,
    /** group (private channel) was archived */
    GROUP_ARCHIVE,
    /** user joined a group (private channel) */
    GROUP_JOIN,
    /** user left a group (private channel) */
    GROUP_LEAVE,
    /** group was renamed (private channel) */
    GROUP_NAME,
    /** group (private channel) purpose updated */
    GROUP_PURPOSE,
    /** group (private channel) topic updated */
    GROUP_TOPIC,
    /** group (private channel) was unarchived */
    GROUP_UNARCHIVE,
    /** /me message sent */
    ME_MESSAGE,
    /** message was changed */
    MESSAGE_CHANGED,
    /** message was deleted */
    MESSAGE_DELETED,
    /** Iitem was pinned in channel */
    PINNED_ITEM,
    /** item was unpinned from channel */
    UNPINNED_ITEM,
    /** unknown event */
    UNKNOWN;

    /**
     * Creates a <code>SlackMessageEventType</code>
     */
    public static SlackMessageEventType toEventType(String type)
    {
        try
        {
            return Enum.valueOf(SlackMessageEventType.class, type.toUpperCase());
        }
        catch (@SuppressWarnings("unused") IllegalArgumentException e)
        {
            return SlackMessageEventType.UNKNOWN;
        }
    }
}
