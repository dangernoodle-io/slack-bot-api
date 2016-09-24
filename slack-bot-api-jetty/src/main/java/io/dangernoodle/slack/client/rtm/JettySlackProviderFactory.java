package io.dangernoodle.slack.client.rtm;

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
