package io.dangernoodle.slack.client;

import io.dangernoodle.slack.client.rtm.SlackWebSocketAssistant;
import io.dangernoodle.slack.client.rtm.SlackWebSocketClient;
import io.dangernoodle.slack.utils.ProxySettings;


public interface SlackProviderFactory
{
    SlackHttpDelegate createHttpDelegate(ProxySettings proxySettings);

    SlackJsonTransformer createJsonTransformer();

    SlackWebSocketClient createClient(SlackWebSocketAssistant delegate, ProxySettings proxySettings);
}
