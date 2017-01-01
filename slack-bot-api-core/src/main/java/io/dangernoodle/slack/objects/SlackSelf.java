package io.dangernoodle.slack.objects;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class SlackSelf
{
    private long created;

    private SlackUser.Id id;

    private SlackPresence manualPresence;

    private String name;

    private Map<String, Object> prefs;

    public long getCreated()
    {
        return created;
    }

    public SlackUser.Id getId()
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
}
