package io.dangernoodle.slack.objects;

import java.util.Map;
import java.util.Objects;


public class SlackUser
{
    private String color;

    private boolean deleted;

    private Id id;

    private boolean isAdmin;
    private boolean isBot;
    private boolean isOwner;
    private boolean isPrimaryOwner;
    private boolean isRestricted;
    private boolean isUltraRestricted;

    private String name;

    private SlackPresence presense;

    private Map<String, Object> profile;
    private String realName;

    private String status;
    private String teamId;
    private String tz;

    private String tzLabel;
    private int tzOffset;

    public String getColor()
    {
        return color;
    }

    public Id getId()
    {
        return id;
    }

    public String asLink()
    {
        return id.asLink();
    }

    public String getName()
    {
        return name;
    }

    public SlackPresence getPresense()
    {
        return presense;
    }

    public Map<String, Object> getProfile()
    {
        return profile;
    }

    public String getRealName()
    {
        return realName;
    }

    public String getStatus()
    {
        return status;
    }

    public String getTeamId()
    {
        return teamId;
    }

    public String getTimeZone()
    {
        return tz;
    }

    public String getTimeZoneLabel()
    {
        return tzLabel;
    }

    public int getTimeZoneOffset()
    {
        return tzOffset;
    }

    public boolean isAdmin()
    {
        return isAdmin;
    }

    public boolean isBot()
    {
        return isBot;
    }

    public boolean isDeleted()
    {
        return deleted;
    }

    public boolean isOwner()
    {
        return isOwner;
    }

    public boolean isPrimaryOwner()
    {
        return isPrimaryOwner;
    }

    public boolean isRestricted()
    {
        return isRestricted;
    }

    public boolean isUltraRestricted()
    {
        return isUltraRestricted;
    }

    public static class Id
    {
        private String id;

        public Id(String id)
        {
            this.id = id;
        }

        public String asLink()
        {
            return String.format("<@%s>", id);
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

        @Override
        public String toString()
        {
            return String.format("SlackUser.Id [id=%s]", id);
        }

        public String value()
        {
            return id;
        }
    }
}
