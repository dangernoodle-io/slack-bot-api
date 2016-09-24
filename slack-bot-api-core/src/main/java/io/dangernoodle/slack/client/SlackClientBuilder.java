package io.dangernoodle.slack.client;

import io.dangernoodle.slack.client.rtm.SlackWebSocketAssistant;
import io.dangernoodle.slack.client.rtm.SlackWebSocketClient;
import io.dangernoodle.slack.client.web.SlackWebApiClient;
import io.dangernoodle.slack.utils.ProxySettings;


public class SlackClientBuilder
{
    private SlackClientSettings clientSettings;

    private SlackProviderFactory providerFactory;

    private ProxySettings proxySettings;

    public SlackClient build() throws IllegalStateException
    {
        validate(providerFactory, "an implementation of 'SlackProviderFactory' is required");

        return new SlackClient(this);
    }

    public SlackClientBuilder with(ProxySettings settings)
    {
        this.proxySettings = settings;
        return this;
    }

    public SlackClientBuilder with(SlackClientSettings settings)
    {
        this.clientSettings = settings;
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
        SlackJsonTransformer transformer = providerFactory.createJsonTransformer();
        SlackWebSocketAssistant assistant = new SlackWebSocketAssistant(slackClient, transformer, clientSettings);

        return providerFactory.createClient(assistant, proxySettings);
    }

    SlackWebApiClient getWebClient()
    {
        return new SlackWebApiClient(clientSettings, providerFactory.createHttpDelegate(proxySettings));
    }

    private void validate(Object required, String message) throws IllegalStateException
    {
        if (required == null)
        {
            throw new IllegalStateException(message);
        }
    }
}
