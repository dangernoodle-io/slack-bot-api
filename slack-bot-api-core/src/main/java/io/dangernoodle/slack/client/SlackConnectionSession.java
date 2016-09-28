package io.dangernoodle.slack.client;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import io.dangernoodle.slack.objects.SlackIntegration;
import io.dangernoodle.slack.objects.SlackMessageable;
import io.dangernoodle.slack.objects.SlackSelf;
import io.dangernoodle.slack.objects.SlackTeam;
import io.dangernoodle.slack.objects.SlackUser;
import io.dangernoodle.slack.objects.api.SlackStartRtmResponse;


public class SlackConnectionSession
{
    final Map<SlackMessageable.Id, SlackMessageable> channels;

    final Map<SlackIntegration.Id, SlackIntegration> integrations;

    final Map<SlackUser.Id, SlackUser> users;

    private AtomicLong lastSentPingId;

    private SlackSelf self;

    private SlackTeam team;

    public SlackConnectionSession()
    {
        this.lastSentPingId = new AtomicLong();

        this.integrations = new ConcurrentHashMap<>(16, 0.9f, 1);
        this.users = new ConcurrentHashMap<>(16, 0.9f, 1);
        this.channels = new ConcurrentHashMap<>(16, 0.9f, 1);
    }

    public SlackMessageable findChannel(SlackMessageable.Id id) throws IllegalArgumentException
    {
        return findById(id, channels);
    }

    public SlackIntegration findIntegration(SlackIntegration.Id id)
    {
        return findById(id, integrations);
    }

    public SlackUser findUser(SlackUser.Id id) throws IllegalArgumentException
    {
        return findById(id, users);
    }

    public SlackSelf getSelf()
    {
        return self;
    }

    public SlackTeam getTeam()
    {
        return team;
    }

    long getLastPingId()
    {
        return lastSentPingId.get();
    }

    void updateChannels(SlackMessageable channel)
    {
        channels.put(channel.getId(), channel);
    }

    void updateIntegrations(SlackIntegration integration)
    {
        integrations.put(integration.getId(), integration);
    }

    void updateLastPingId(long id)
    {
        lastSentPingId.set(id);
    }

    synchronized void updateSession(SlackStartRtmResponse session)
    {
        self = session.getSelf();
        team = session.getTeam();

        channels.clear();

        // for now these are all all lumped together as a 'SlackMessageable'
        session.getGroups().forEach(this::updateChannels);
        session.getChannels().forEach(this::updateChannels);
        session.getDirectMessages().forEach(this::updateChannels);

        session.getUsers().forEach(this::updateUsers);
        session.getIntegrations().forEach(this::updateIntegrations);
    }

    void updateUsers(SlackUser user)
    {
        users.put(user.getId(), user);
    }

    private <I, T> T findById(I id, Map<I, T> map)
    {
        return map.get(id);
    }
}
