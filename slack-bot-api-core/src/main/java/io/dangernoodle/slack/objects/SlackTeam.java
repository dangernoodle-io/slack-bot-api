package io.dangernoodle.slack.objects;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class SlackTeam
{
    private Id id;

    private String name;

    private boolean overIntegrationsLimit;

    private boolean overStorageLimit;

    private Map<String, Object> prefs;

    public Id getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public Map<String, Object> getPrefs()
    {
        // TODO: these may need to be ordered
        return (prefs == null) ? Collections.emptyMap() : new HashMap<>(prefs);
    }

    public boolean isOverIntegrationsLimit()
    {
        return overIntegrationsLimit;
    }

    public boolean isOverStorageLimit()
    {
        return overStorageLimit;
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
