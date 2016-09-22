package io.dangernoodle.slack.client;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

import io.dangernoodle.slack.objects.SlackChannel;
import io.dangernoodle.slack.objects.SlackIntegration;
import io.dangernoodle.slack.objects.SlackMessageable;
import io.dangernoodle.slack.objects.SlackSelf;
import io.dangernoodle.slack.objects.SlackStartBotResponse;
import io.dangernoodle.slack.objects.SlackUser;


public class SlackConnectionSession
{
    private final Map<SlackMessageable.Id, SlackMessageable> channels;
    private final Map<SlackIntegration.Id, SlackIntegration> integrations;

    private AtomicLong lastSentPingId;

    private SlackSelf self;

    private final Map<SlackUser.Id, SlackUser> users;

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

    long getLastPingId()
    {
        return lastSentPingId.get();
    }

    void updateChannels(SlackChannel channel)
    {
        channels.put(channel.getId(), channel);
    }

    void updateLastPingId(long id)
    {
        lastSentPingId.set(id);
    }

    synchronized void updateSession(SlackStartBotResponse session)
    {
        self = session.getSelf();

        updateChannels(session);

        addToMap(users, session.getUsers(), SlackUser::getId);
        addToMap(integrations, session.getIntegrations(), SlackIntegration::getId);
    }

    void updateUsers(SlackUser user)
    {
        users.put(user.getId(), user);
    }

    private void addToChannels(Collection<? extends SlackMessageable> collection)
    {
        collection.forEach(channel -> channels.put(channel.getId(), channel));
    }

    private <I, T> void addToMap(Map<I, T> map, Collection<T> collection, Function<T, I> function)
    {
        map.clear();
        collection.forEach(o -> map.put(function.apply(o), o));
    }

    private <I, T> T findById(I id, Map<I, T> map)
    {
        if (map.containsKey(id))
        {
            return map.get(id);
        }

        throw new IllegalArgumentException();
    }

    /*-
     * the compiler doesn't seem to like 'Map<SlackMessageable.MessageId, ? extends SlackMessageable>' on the field,
     * nor does it like using the 'addToMap' method.
     *
     * the followiing does work until a time, if ever, i feel like coming back to investigate futher
     */
    private void updateChannels(SlackStartBotResponse session)
    {
        channels.clear();

        // for now these are all all lumped together as a 'SlackMessageable'
        addToChannels(session.getGroups());
        addToChannels(session.getChannels());
        addToChannels(session.getDirectMessages());
    }
}
