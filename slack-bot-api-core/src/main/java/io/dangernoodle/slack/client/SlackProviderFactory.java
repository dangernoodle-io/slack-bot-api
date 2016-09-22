package io.dangernoodle.slack.client;

import io.dangernoodle.slack.client.rtm.SlackRtmApiAssistant;
import io.dangernoodle.slack.client.rtm.SlackRtmApiClient;
import io.dangernoodle.slack.utils.ProxySettings;


public interface SlackProviderFactory
{
    SlackHttpDelegate createHttpDelegate(ProxySettings proxySettings);

    SlackJsonTransformer createJsonTransformer();

    SlackRtmApiClient createClient(SlackRtmApiAssistant delegate, ProxySettings proxySettings);
}
