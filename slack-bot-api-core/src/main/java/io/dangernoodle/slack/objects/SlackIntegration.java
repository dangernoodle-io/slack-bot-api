package io.dangernoodle.slack.objects;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class SlackIntegration
{
    private boolean deleted;

    private Map<String, String> icons;

    private Id id;

    private String name;

    public Map<String, String> getIcons()
    {
        return (icons == null) ? Collections.emptyMap() : new HashMap<>(icons);
    }

    public Id getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public boolean isDeleted()
    {
        return deleted;
    }

//    public static class Builder
//    {
//        private SlackIntegration integration;
//
//        public Builder(String id, String name)
//        {
//            integration = new SlackIntegration();
//
//            integration.id = new Id(id);
//            integration.icons = new HashMap<>();
//
//            integration.name = name;
//        }
//
//        public Builder addIcon(String name, String location)
//        {
//            integration.icons.put(name, location);
//            return this;
//        }
//
//        public SlackIntegration build()
//        {
//            return integration;
//        }
//
//        public Builder isDeleted(boolean deleted)
//        {
//            integration.deleted = deleted;
//            return this;
//        }
//    }

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
            return String.format("SlackIntegration.Id [id=%s]", id);
        }
    }
}
