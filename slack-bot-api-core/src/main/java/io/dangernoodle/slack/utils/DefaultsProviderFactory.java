package io.dangernoodle.slack.utils;

import io.dangernoodle.slack.client.SlackHttpDelegate;
import io.dangernoodle.slack.client.SlackJsonTransformer;
import io.dangernoodle.slack.client.SlackProviderFactory;


public abstract class DefaultsProviderFactory implements SlackProviderFactory
{
    private final SlackHttpDelegate httpDelegate = new OkHttpDelegate();
    private final SlackJsonTransformer jsonTransformer = new GsonTransformer();

    @Override
    public SlackHttpDelegate createHttpDelegate()
    {
        return httpDelegate;
    }

    @Override
    public SlackJsonTransformer createJsonTransformer()
    {
        return jsonTransformer;
    }
}
