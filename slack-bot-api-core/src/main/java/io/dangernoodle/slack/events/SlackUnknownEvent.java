package io.dangernoodle.slack.events;

import java.util.HashMap;
import java.util.Map;


public class SlackUnknownEvent extends SlackEvent
{
    private final String rawResponse;

    private final Map<String, Object> response;

    public SlackUnknownEvent(Map<String, Object> response, String rawResponse)
    {
        this.response = response;
        this.rawResponse = rawResponse;

        setType(SlackEventType.UNKNOWN);
    }

    public String getRawResponse()
    {
        return rawResponse;
    }

    public Map<String, Object> getDeserialized()
    {
        // always a copy...
        return new HashMap<>(response);
    }
}
