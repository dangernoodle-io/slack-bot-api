package io.dangernoodle.slack.client;

import io.dangernoodle.slack.client.rtm.SlackWebSocketAssistant;
import io.dangernoodle.slack.client.rtm.SlackWebSocketClient;


public interface SlackProviderFactory
{
    SlackHttpDelegate createHttpDelegate();

    SlackJsonTransformer createJsonTransformer();

    SlackWebSocketClient createClient(SlackWebSocketAssistant delegate);
}
