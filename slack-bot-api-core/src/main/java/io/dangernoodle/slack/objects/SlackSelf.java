package io.dangernoodle.slack.objects;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class SlackSelf
{
    private long created;

    private Id id;

    private SlackPresence manualPresence;

    private String name;

    private Map<String, Object> prefs;

    public long getCreated()
    {
        return created;
    }

    public Id getId()
    {
        return id;
    }

    public SlackPresence getManualPresence()
    {
        return manualPresence;
    }

    public String getName()
    {
        return name;
    }

    public Map<String, Object> getPrefs()
    {
        return (prefs == null) ? Collections.emptyMap() : new HashMap<>(prefs);
    }

    public static class Id
    {
        private String id;

        public Id(String id)
        {
            this.id = id;
        }

        public String value()
        {
            return id;
        }
    }
}
