package io.dangernoodle.slack.events;

/**
 * Event types
 *
 * @see <a href="https://api.slack.com/rtm">https://api.slack.com/rtm</a>
 */
public enum SlackEventType
{
    /** list of signed in accounts has changed */
    ACCOUNTS_CHANGED,
    /** bot added */
    BOT_ADDED,
    /** bot changed */
    BOT_CHANGED,
    /** channel was archived */
    CHANNEL_ARCHIVE,
    /** channel was created */
    CHANNEL_CREATED,
    /** channel was deleted */
    CHANNEL_DELETED,
    /** channel history bulk update */
    CHANNEL_HISTORY_CHANGED,
    /** channel was joined */
    CHANNEL_JOINED,
    /** channel was left */
    CHANNEL_LEFT,
    /** channel read marker updated */
    CHANNEL_MARKED,
    /** channel was renamed */
    CHANNEL_RENAME,
    /** channel was unarchived */
    CHANNEL_UNARCHIVE,
    /** slash command added or changed */
    COMMANDS_CHANGED,
    /** dnd settings for current user changed */
    DND_UPDATED,
    /** dnd setings for team member changed */
    DND_UPDATED_USER,
    /** team email domain changed */
    EMAIL_DOMAIN_CHANGED,
    /** custom emoji added or changed */
    EMOJI_CHANGED,
    /** an error occured */
    ERROR,
    /** file was changed */
    FILE_CHANGE,
    /** file comment was added */
    FILE_COMMENT_ADDED,
    /** file comment was deleted */
    FILE_COMMENT_DELETED,
    /** file comment editted */
    FILE_COMMENT_EDITED,
    /** file was created */
    FILE_CREATED,
    /** file was deleted */
    FILE_DELETED,
    /** file was made public */
    FILE_PUBLIC,
    /** file was shared */
    FILE_SHARED,
    /** file was unshared */
    FILE_UNSHARED,
    /** group channel was archived */
    GROUP_ARCHIVE,
    /** closed group (private channel - you) */
    GROUP_CLOSE,
    /** group history (private channel) bulk update */
    GROUP_HISTORY_CHANGED,
    /** group (private channel) was joined */
    GROUP_JOINED,
    /** group (private channel) was left */
    GROUP_LEFT,
    /** group read (private channel) marker updated */
    GROUP_MARKED,
    /** opened a group (private channel) */
    GROUP_OPEN,
    /** group (private channel) renamed */
    GROUP_RENAME,
    /** group (private channel) was unarchived */
    GROUP_UNARCHIVE,
    /** hello */
    HELLO,
    /** closed direct message (you) */
    IM_CLOSE,
    /** direct message created */
    IM_CREATED,
    /** direct message bulk update */
    IM_HISTORY_CHANGED,
    /** direct message read marker updated */
    IM_MARKED,
    /** direct message opened (you) */
    IM_OPEN,
    /** manual presence update (you) */
    MANUAL_PRESENCE_CHANGE,
    /** message was sent to a channel/group */
    MESSAGE,
    /** a pin was added to a channel */
    PIN_ADDED,
    /** a pin was removed from a channel */
    PIN_REMOVED,
    /** ping response */
    PONG,
    /** preferences updated (yours) */
    PREF_CHANGE,
    /** team member presence change */
    PRESENCE_CHANGE,
    /** emoji reaction added */
    REACTION_ADDED,
    /** emoji reaction removed */
    REACTION_REMOVED,
    /** experimental (not currently supported */
    RECONNECT_URL,
    /** message reply */
    REPLY_TO,
    /** star was added */
    STAR_ADDED,
    /** star was removed */
    STAR_REMOVED,
    /** user group created */
    SUBTEAM_CREATED,
    /** added to user group */
    SUBTEAM_SELF_ADDED,
    /** removed from user group */
    SUBTEAM_SELF_REMOVED,
    /** user group updated or members changed */
    SUBTEAM_UPDATED,
    /** team domain changed */
    TEAM_DOMAIN_CHANGE,
    /** new team member joined */
    TEAM_JOIN,
    /** team server migration in progress */
    TEAM_MIGRATION_STARTED,
    /** team billing plan changed */
    TEAM_PLAN_CHANGE,
    /** team preference added */
    TEAM_PREF_CHANGE,
    /** team profile fields changed */
    TEAM_PROFILE_CHANGE,
    /** team profile fields deleted */
    TEAM_PROFILE_DELETE,
    /** team profile fields reordered */
    TEAM_PROFILE_REORDER,
    /** team renamed */
    TEAM_RENAME,
    /** user's data has changed */
    USER_CHANGE,
    /** user typing */
    USER_TYPING,
    /** unknown event */
    UNKNOWN;

    /**
     * Creates a <code>SlackEventType</code>
     */
    public static SlackEventType toEventType(String type)
    {
        try
        {
            return Enum.valueOf(SlackEventType.class, type.toUpperCase());
        }
        catch (@SuppressWarnings("unused") IllegalArgumentException e)
        {
            return SlackEventType.UNKNOWN;
        }
    }

    public String toType()
    {
        return this.toString().toLowerCase();
    }
}
