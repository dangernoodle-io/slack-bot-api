package io.dangernoodle.slack.objects;

import java.util.Collection;
import java.util.Collections;


public class SlackStartBotResponse
{
    private Collection<SlackIntegration> bots;

    private Collection<SlackChannel> channels;

    private Collection<SlackChannel> groups;

    private Collection<SlackDirectMessage> ims;

    private String error;

    private boolean ok;

    private SlackSelf self;

    private SlackTeam team;

    private String url;

    private Collection<SlackUser> users;

    private SlackStartBotResponse()
    {
        // use the builder
    }

    public Collection<SlackDirectMessage> getDirectMessages()
    {
        return ims;
    }

    public Collection<SlackChannel> getChannels()
    {
        return returnCollection(channels);
    }

    public Collection<SlackChannel> getGroups()
    {
        return returnCollection(groups);
    }

    public String getError()
    {
        return error;
    }

    public Collection<SlackIntegration> getIntegrations()
    {
        return returnCollection(bots);
    }

    public SlackSelf getSelf()
    {
        return self;
    }

    public SlackTeam getTeam()
    {
        return team;
    }

    public String getUrl()
    {
        return url;
    }

    public Collection<SlackUser> getUsers()
    {
        return returnCollection(users);
    }

    public boolean isOk()
    {
        return ok;
    }

    private <T> Collection<T> returnCollection(Collection<T> collection)
    {
        return (collection == null) ? Collections.emptyList() : collection;
    }
}
