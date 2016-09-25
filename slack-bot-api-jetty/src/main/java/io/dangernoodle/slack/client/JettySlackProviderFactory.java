package io.dangernoodle.slack.client;

import io.dangernoodle.slack.client.rtm.JettySlackWebSocketClient;
import io.dangernoodle.slack.client.rtm.SlackWebSocketAssistant;
import io.dangernoodle.slack.client.rtm.SlackWebSocketClient;
import io.dangernoodle.slack.utils.DefaultsProviderFactory;
import io.dangernoodle.slack.utils.ProxySettings;


public class JettySlackProviderFactory extends DefaultsProviderFactory
{
    @Override
    public SlackWebSocketClient createClient(SlackWebSocketAssistant assistant, ProxySettings proxySettings)
    {
        return new JettySlackWebSocketClient(assistant, proxySettings);
    }
}
