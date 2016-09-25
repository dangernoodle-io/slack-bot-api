package io.dangernoodle.slack.client;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import io.dangernoodle.slack.client.rtm.SlackWebSocketAssistant;
import io.dangernoodle.slack.client.rtm.SlackWebSocketClient;
import io.dangernoodle.slack.client.web.SlackWebApiClient;
import io.dangernoodle.slack.utils.ProxySettings;


public class SlackClientBuilder
{
    private static final ServiceLoaderDelegate serviceLoaderDelegate = new ServiceLoaderDelegate();

    private final SlackClientSettings clientSettings;

    private SlackProviderFactory providerFactory;

    private ProxySettings proxySettings;

    public SlackClientBuilder(SlackClientSettings clientSettings)
    {
        this.clientSettings = clientSettings;
    }

    public SlackClient build() throws IllegalStateException
    {
        return new SlackClient(this);
    }

    public SlackClientBuilder with(ProxySettings settings)
    {
        this.proxySettings = settings;
        return this;
    }

    public SlackClientBuilder with(SlackProviderFactory providerFactory)
    {
        this.providerFactory = providerFactory;
        return this;
    }

    SlackClientSettings getClientSettings()
    {
        return clientSettings;
    }

    SlackWebSocketClient getRtmClient(SlackClient slackClient)
    {
        SlackProviderFactory providerFactory = getSlackProviderFactory();

        SlackJsonTransformer transformer = providerFactory.createJsonTransformer();
        SlackWebSocketAssistant assistant = new SlackWebSocketAssistant(slackClient, transformer, clientSettings);

        return providerFactory.createClient(assistant, proxySettings);
    }

    SlackWebApiClient getWebClient()
    {
        return new SlackWebApiClient(clientSettings, getSlackProviderFactory().createHttpDelegate(proxySettings));
    }

    private SlackProviderFactory getSlackProviderFactory()
    {
        return (providerFactory != null) ? providerFactory : serviceLoaderDelegate.getSlackProviderFactory();
    }

    public static SlackClient createClient(SlackClientSettings settings)
    {
        return new SlackClientBuilder(settings).build();
    }

    private static class ServiceLoaderDelegate
    {
        SlackProviderFactory getSlackProviderFactory() throws IllegalStateException
        {
            ServiceLoader<SlackProviderFactory> pfLoader = ServiceLoader.load(SlackProviderFactory.class);

            List<SlackProviderFactory> factories = new ArrayList<>();
            pfLoader.forEach(factories::add);

            if (factories.size() == 0)
            {
                throw new IllegalStateException("no instances of 'SlackProviderFactory' found on classpath");
            }

            if (factories.size() != 1)
            {
                throw new IllegalStateException("multiple instances of 'SlackProviderFactory' found on classpath");
            }

            return factories.get(0);
        }
    }
}
