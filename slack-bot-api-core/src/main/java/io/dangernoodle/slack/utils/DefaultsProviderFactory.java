package io.dangernoodle.slack.utils;

import io.dangernoodle.slack.client.SlackHttpDelegate;
import io.dangernoodle.slack.client.SlackJsonTransformer;
import io.dangernoodle.slack.client.SlackProviderFactory;


public abstract class DefaultsProviderFactory implements SlackProviderFactory
{
    private final SlackJsonTransformer jsonTransformer = new GsonTransformer();

    @Override
    public SlackHttpDelegate createHttpDelegate(ProxySettings proxySettings)
    {
        return new GoogleHttpDelegate(jsonTransformer, proxySettings);
    }

    @Override
    public SlackJsonTransformer createJsonTransformer()
    {
        return jsonTransformer;
    }
}
