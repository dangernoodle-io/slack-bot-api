package io.dangernoodle.slack.objects;

import java.util.Objects;

import io.dangernoodle.slack.events.SlackEventType;


/**
 * Base class for the messageable object types.
 *
 * @since 0.1.0
 */
public abstract class SlackMessageable
{
    private long created;

    private boolean hasPins;

    private SlackMessageable.Id id;

    private boolean isChannel;

    private boolean isGroup;

    private boolean isIm;

    private boolean isMpim;

    private String lastRead;

    private Latest latest;

    private int unreadCount;

    private int unreadCountDisplay;

    public long getCreated()
    {
        return created;
    }

    public SlackMessageable.Id getId()
    {
        return id;
    }

    public String getLastRead()
    {
        return lastRead;
    }

    public String getLastReadTimestamp()
    {
        return lastRead;
    }

    public Latest getLatest()
    {
        return latest;
    }

    public int getUnreadCount()
    {
        return unreadCount;
    }

    public int getUnreadCountDisplay()
    {
        return unreadCountDisplay;
    }

    public boolean hasPins()
    {
        return hasPins;
    }

    public boolean isChannel()
    {
        return isChannel;
    }

    public boolean isGroup()
    {
        return isGroup;
    }

    public boolean isDirectMessage()
    {
        return isIm;
    }

    public boolean isMultiDirectMessage()
    {
        return isMpim;
    }

    public static class Id
    {
        private String id;

        public Id(String id)
        {
            this.id = id;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (obj == null || getClass() != obj.getClass())
            {
                return false;
            }

            return Objects.equals(this.id, ((Id) obj).id);
        }

        @Override
        public int hashCode()
        {
            return Objects.hash(id);
        }

        public String value()
        {
            return id;
        }

        @Override
        public String toString()
        {
            return String.format("SlackMessageable.Id [id=%s]", id);
        }
    }

    public static class Latest
    {
        private String text;

        private String ts;

        private SlackEventType type;

        private SlackMessageable.Id user;

        public String getText()
        {
            return text;
        }

        public String getTimepstamp()
        {
            return ts;
        }

        public SlackEventType getType()
        {
            return type;
        }

        public SlackMessageable.Id getUserId()
        {
            return user;
        }
    }
}
