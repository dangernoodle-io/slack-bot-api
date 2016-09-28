package io.dangernoodle.slack.client;

import static org.mockito.Mockito.mock;

import io.dangernoodle.slack.client.rtm.SlackWebSocketAssistant;
import io.dangernoodle.slack.client.rtm.SlackWebSocketClient;


public class SlackTestProviderFactory implements SlackProviderFactory
{
    static SlackHttpDelegate httpDelegate;
    static SlackJsonTransformer jsonTransformer;
    static SlackWebSocketClient websocketClient;

    @Override
    public SlackHttpDelegate createHttpDelegate()
    {
        httpDelegate = mock(SlackHttpDelegate.class);
        return httpDelegate;
    }

    @Override
    public SlackJsonTransformer createJsonTransformer()
    {
        jsonTransformer = mock(SlackJsonTransformer.class);
        return jsonTransformer;
    }

    @Override
    public SlackWebSocketClient createClient(SlackWebSocketAssistant delegate)
    {
        websocketClient = mock(SlackWebSocketClient.class);
        return websocketClient;
    }
}
