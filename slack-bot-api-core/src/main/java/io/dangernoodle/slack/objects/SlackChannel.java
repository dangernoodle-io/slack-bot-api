package io.dangernoodle.slack.objects;

import java.util.Collection;


/**
 * This class is used to represent <code>public</code> channels and <code>groups</code>, also known as
 * <code>private</code> channels.
 *
 * @since 0.1.0
 */
public class SlackChannel extends SlackMessageable
{
    private SlackUser.Id creator;

    private boolean isArchived;

    private boolean isGeneral;

    private boolean isMember;

    private Collection<SlackUser.Id> members;

    private String name;

    private Purpose purpose;

    private Topic topic;

    public SlackUser.Id getCreator()
    {
        return creator;
    }

    public Collection<SlackUser.Id> getMembers()
    {
        return members;
    }

    public String getName()
    {
        return name;
    }

    public Purpose getPurpose()
    {
        return purpose;
    }

    public Topic getTopic()
    {
        return topic;
    }

    public boolean isArchived()
    {
        return isArchived;
    }

    public boolean isGeneral()
    {
        return isGeneral;
    }

    public boolean isMember()
    {
        // we only know about a 'group' if we're in it, which makes us a member
        return isMember || isGroup();
    }

    public static class Purpose
    {
        private long created;

        private SlackUser.Id creator;

        private String value;

        public long getCreated()
        {
            return created;
        }

        public SlackUser.Id getCreator()
        {
            return creator;
        }

        public String getValue()
        {
            return value;
        }
    }

    public static class Topic
    {
        private long created;

        private SlackUser.Id creator;

        private String value;

        public long getCreated()
        {
            return created;
        }

        public SlackUser.Id getCreator()
        {
            return creator;
        }

        public String getValue()
        {
            return value;
        }
    }
}
