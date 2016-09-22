package io.dangernoodle.slack.client.rtm;

import io.dangernoodle.slack.utils.DefaultsProviderFactory;
import io.dangernoodle.slack.utils.ProxySettings;


public class TyrusSlackProviderFactory extends DefaultsProviderFactory
{
    @Override
    public SlackRtmApiClient createClient(SlackRtmApiAssistant assistant, ProxySettings proxySettings)
    {
        return new TyrusSlackRtmApiClient(assistant, proxySettings);
    }
}
