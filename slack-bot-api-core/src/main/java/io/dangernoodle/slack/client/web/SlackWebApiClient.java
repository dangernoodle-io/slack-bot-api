package io.dangernoodle.slack.client.web;

import java.io.IOException;

import io.dangernoodle.slack.client.SlackClientSettings;
import io.dangernoodle.slack.client.SlackHttpDelegate;
import io.dangernoodle.slack.objects.SlackStartBotResponse;


public class SlackWebApiClient
{
    private static final String SLACK_API_URL = "https://slack.com/api";
    private static final String SLACK_AUTH_URL = "https://slack.com/api/rtm.start?token=";

    private final SlackClientSettings settings;
    private final SlackHttpDelegate httpDelegate;

    public SlackWebApiClient(SlackClientSettings settings, SlackHttpDelegate httpDelegate)
    {
        this.settings = settings;
        this.httpDelegate = httpDelegate;
    }

    public SlackStartBotResponse initiateRtmConnection() throws IOException
    {
        return httpDelegate.get(SLACK_AUTH_URL + settings.getAuthToken(), SlackStartBotResponse.class);
    }
}
