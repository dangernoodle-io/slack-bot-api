package io.dangernoodle.slack.objects.api;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class SlackWebResponse
{
    private final String rawResponse;
    private final Map<String, Object> response;

    public SlackWebResponse(Map<String, Object> response, String rawResponse)
    {
        this.response = response;
        this.rawResponse = rawResponse;
    }

    public String getError()
    {
        return get("error").orElse(null);
    }

    public String getRawResponse()
    {
        return rawResponse;
    }

    public Map<String, Object> getResponse()
    {
        // always a copy...
        return new HashMap<>(response);
    }

    public Collection<String> getWarnings()
    {
        return get("warning").map(this::split).orElse(Collections.emptyList());
    }

    public boolean isOk()
    {
        return get("ok").map(Boolean::valueOf).orElse(false);
    }

    private Optional<String> get(String key)
    {
        return response.containsKey(key) ? Optional.of(response.get(key).toString()) : Optional.empty();
    }

    private Collection<String> split(Object toSplit)
    {
        return Arrays.asList(toSplit.toString().split(","));
    }
}
