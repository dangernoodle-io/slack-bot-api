package io.dangernoodle.slack.client;

import io.dangernoodle.slack.client.rtm.SlackWebSocketAssistant;
import io.dangernoodle.slack.client.rtm.SlackWebSocketClient;
import io.dangernoodle.slack.client.rtm.TyrusSlackWebSocketClient;
import io.dangernoodle.slack.utils.DefaultsProviderFactory;


public class TyrusSlackProviderFactory extends DefaultsProviderFactory
{
    @Override
    public SlackWebSocketClient createClient(SlackWebSocketAssistant assistant)
    {
        return new TyrusSlackWebSocketClient(assistant);
    }
}
