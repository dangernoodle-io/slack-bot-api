package io.dangernoodle.slack.client.web;

import static io.dangernoodle.slack.client.web.SlackWebMethods.chatPostMessage;
import static io.dangernoodle.slack.client.web.SlackWebMethods.filesUpload;
import static io.dangernoodle.slack.client.web.SlackWebMethods.rtmStart;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dangernoodle.slack.client.SlackClientSettings;
import io.dangernoodle.slack.client.SlackHttpDelegate;
import io.dangernoodle.slack.client.SlackJsonTransformer;
import io.dangernoodle.slack.objects.SlackMessageable;
import io.dangernoodle.slack.objects.api.SlackFileUpload;
import io.dangernoodle.slack.objects.api.SlackPostMessage;
import io.dangernoodle.slack.objects.api.SlackStartRtmResponse;
import io.dangernoodle.slack.objects.api.SlackWebResponse;


public class SlackWebClient
{
    private static final String ATTACHMENTS = "attachments";

    private static final Logger logger = LoggerFactory.getLogger(SlackWebClient.class);

    private final SlackHttpDelegate httpDelegate;
    private final SlackJsonTransformer jsonTransformer;

    private final SlackClientSettings settings;

    public SlackWebClient(SlackClientSettings settings, SlackHttpDelegate httpDelegate, SlackJsonTransformer jsonTransformer)
    {
        this.settings = settings;

        this.httpDelegate = httpDelegate;
        this.jsonTransformer = jsonTransformer;
    }

    public SlackStartRtmResponse initiateRtmConnection() throws IOException
    {
        String response = httpDelegate.get(rtmStart.toUrl() + "?token=" + settings.getAuthToken());
        logRequest(rtmStart, null, response);

        return jsonTransformer.deserialize(response, SlackStartRtmResponse.class);
    }

    public SlackWebResponse send(SlackMessageable.Id id, SlackPostMessage.Builder builder) throws IOException
    {
        Map<String, Object> serialized = serialize(builder.build(settings.getAuthToken(), id));
        serialized.put(ATTACHMENTS, jsonTransformer.serialize(serialized.get(ATTACHMENTS)));

        return post(chatPostMessage.toUrl(), serialized, SlackWebResponse.class);
    }

    public SlackWebResponse upload(SlackFileUpload.Builder builder, SlackMessageable.Id... ids) throws IOException
    {
        SlackFileUpload upload = builder.build(settings.getAuthToken(), ids);
        Map<String, Object> serialized = serialize(upload);

        String response = httpDelegate.upload(filesUpload.toUrl(), upload.getFile(), upload.getFilename(), serialized);
        logRequest(filesUpload, serialized, response);

        return jsonTransformer.deserialize(response, SlackWebResponse.class);
    }

    private void logRequest(SlackWebMethods command, Map<String, Object> serialized, String response)
    {
        logRequest(command.toUrl(), serialized, response);
    }

    private void logRequest(String command, Map<String, Object> serialized, String response)
    {
        if (logger.isTraceEnabled())
        {
            logger.trace("url: {} - body: {}", command, serialized);
            logger.trace("response: {}", jsonTransformer.prettyPrint(response));
        }
    }

    private <T> T post(String url, Map<String, Object> serialized, Class<T> clazz) throws IOException
    {
        String response = httpDelegate.post(url, serialized);
        logRequest(url, serialized, response);

        return jsonTransformer.deserialize(response, clazz);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> serialize(Object object)
    {
        return jsonTransformer.deserialize(jsonTransformer.serialize(object), Map.class);
    }
}
