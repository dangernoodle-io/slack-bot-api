package io.dangernoodle.slack.client.rtm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import io.dangernoodle.slack.events.SlackErrorEvent;
import io.dangernoodle.slack.events.SlackEvent;
import io.dangernoodle.slack.events.SlackEventType;
import io.dangernoodle.slack.events.SlackHelloEvent;
import io.dangernoodle.slack.events.SlackMessageEvent;
import io.dangernoodle.slack.events.SlackMessageEventType;
import io.dangernoodle.slack.events.SlackPongEvent;
import io.dangernoodle.slack.events.SlackReplyToEvent;
import io.dangernoodle.slack.events.SlackUnknownEvent;
import io.dangernoodle.slack.events.channel.SlackChannelCreatedEvent;
import io.dangernoodle.slack.events.channel.SlackChannelDeletedEvent;
import io.dangernoodle.slack.events.channel.SlackChannelJoinedEvent;
import io.dangernoodle.slack.events.channel.SlackChannelLeftEvent;
import io.dangernoodle.slack.events.channel.SlackChannelRenameEvent;
import io.dangernoodle.slack.events.user.SlackUserChangeEvent;
import io.dangernoodle.slack.events.user.SlackUserTypingEvent;


/**
 * Registry for <code>SlackEventObserver</code>s
 *
 * @since 0.1.0
 */
public class SlackObserverRegistry
{
    private final Map<SlackMessageEventType, Vector<SlackEventObserver<? extends SlackEvent>>> messageObservers;

    private final Map<SlackEventType, Vector<SlackEventObserver<? extends SlackEvent>>> observers;

    public SlackObserverRegistry()
    {
        this.observers = new ConcurrentHashMap<>(8, 0.9f, 1);
        this.messageObservers = new ConcurrentHashMap<>(8, 0.9f, 1);
    }

    /**
     * @param observer asdf
     * @since 0.1.0
     */
    public void addChannelCreatedObserver(SlackEventObserver<SlackChannelCreatedEvent> observer)
    {
        addObserver(SlackEventType.CHANNEL_CREATED, observer);
    }

    /**
     * @since 0.1.0
     */
    public void addChannelDeletedObserver(SlackEventObserver<SlackChannelDeletedEvent> observer)
    {
        addObserver(SlackEventType.CHANNEL_DELETED, observer);
    }

    /**
     * @since 0.1.0
     */
    public void addChannelJoinedObserver(SlackEventObserver<SlackChannelJoinedEvent> observer)
    {
        addObserver(SlackEventType.CHANNEL_JOINED, observer);
    }

    /**
     * @since 0.1.0
     */
    public void addChannelLeftObserver(SlackEventObserver<SlackChannelLeftEvent> observer)
    {
        addObserver(SlackEventType.CHANNEL_LEFT, observer);
    }

    /**
     * @since 0.1.0
     */
    public void addChannelRenameObserver(SlackEventObserver<SlackChannelRenameEvent> observer)
    {
        addObserver(SlackEventType.CHANNEL_RENAME, observer);
    }

    /**
     * @since 0.1.0
     */
    public void addErrorObserver(SlackEventObserver<SlackErrorEvent> observer)
    {
        addObserver(SlackEventType.ERROR, observer);
    }

    /**
     * @since 0.1.0
     */
    public void addGroupJoinedObserver(SlackEventObserver<SlackChannelJoinedEvent> observer)
    {
        addObserver(SlackEventType.GROUP_JOINED, observer);
    }

    /**
     * @since 0.1.0
     */
    public void addGroupLeftObserver(SlackEventObserver<SlackChannelLeftEvent> observer)
    {
        addObserver(SlackEventType.GROUP_LEFT, observer);
    }

    /**
     * @since 0.1.0
     */
    public void addGroupRenameObserver(SlackEventObserver<SlackChannelRenameEvent> observer)
    {
        addObserver(SlackEventType.GROUP_RENAME, observer);
    }

    /**
     * @since 0.1.0
     */
    public void addHelloObserver(SlackEventObserver<SlackHelloEvent> observer)
    {
        addObserver(SlackEventType.HELLO, observer);
    }

    /**
     * @since 0.1.0
     */
    public void addMessageChangedObserver(SlackEventObserver<SlackMessageEvent> observer)
    {
        addObserver(SlackMessageEventType.MESSAGE_CHANGED, observer);
    }

    /**
     * @since 0.1.0
     */
    public void addMessageDeletedObserver(SlackEventObserver<SlackMessageEvent> observer)
    {
        addObserver(SlackMessageEventType.MESSAGE_DELETED, observer);
    }

    /**
     * @since 0.1.0
     */
    public void addMessagePostedObserver(SlackEventObserver<SlackMessageEvent> observer)
    {
        addObserver(SlackEventType.MESSAGE, observer);
    }

    /**
     * @since 0.1.0
     */
    public void addPongObserver(SlackEventObserver<SlackPongEvent> observer)
    {
        addObserver(SlackEventType.PONG, observer);
    }

    /**
     * @since 0.1.0
     */
    public void addReplyToObserver(SlackEventObserver<SlackReplyToEvent> observer)
    {
        addObserver(SlackEventType.REPLY_TO, observer);
    }

    /**
     * @since 0.1.0
     */
    public void addUnknownMessageObserver(SlackEventObserver<SlackUnknownEvent> observer)
    {
       addObserver(SlackMessageEventType.UNKNOWN, observer);
    }

    /**
     * @since 0.1.0
     */
    public void addUnknownObserver(SlackEventObserver<SlackUnknownEvent> observer)
    {
        addObserver(SlackEventType.UNKNOWN, observer);
    }

    /**
     * @since 0.1.0
     */
    public void addUserChangeObserver(SlackEventObserver<SlackUserChangeEvent> observer)
    {
        addObserver(SlackEventType.USER_CHANGE, observer);
    }

    /**
     * @since 0.1.0
     */
    public void addUserTypingObserver(SlackEventObserver<SlackUserTypingEvent> observer)
    {
        addObserver(SlackEventType.USER_TYPING, observer);
    }

    /**
     * @since 0.1.0
     */
    public void removeChannelCreatedObserver(SlackEventObserver<SlackChannelCreatedEvent> observer)
    {
        removeObserver(SlackEventType.CHANNEL_CREATED, observer);
    }

    /**
     * @since 0.1.0
     */
    public void removeChannelDeletedObserver(SlackEventObserver<SlackChannelDeletedEvent> observer)
    {
        removeObserver(SlackEventType.CHANNEL_DELETED, observer);
    }

    /**
     * @since 0.1.0
     */
    public void removeChannelJoinedObserver(SlackEventObserver<SlackChannelJoinedEvent> observer)
    {
        removeObserver(SlackEventType.CHANNEL_JOINED, observer);
    }

    /**
     * @since 0.1.0
     */
    public void removeChannelLeftObserver(SlackEventObserver<SlackChannelLeftEvent> observer)
    {
        removeObserver(SlackEventType.CHANNEL_LEFT, observer);
    }

    /**
     * @since 0.1.0
     */
    public void removeChannelRenameObservers(SlackEventObserver<SlackChannelRenameEvent> observer)
    {
        removeObserver(SlackEventType.CHANNEL_RENAME, observer);
    }

    /**
     * @since 0.1.0
     */
    public void removeErrorObserver(SlackEventObserver<SlackErrorEvent> observer)
    {
        removeObserver(SlackEventType.ERROR, observer);
    }

    /**
     * @since 0.1.0
     */
    public void removeGroupJoinedObserver(SlackEventObserver<SlackChannelJoinedEvent> observer)
    {
        removeObserver(SlackEventType.GROUP_JOINED, observer);
    }

    /**
     * @since 0.1.0
     */
    public void removeGroupLeftObserver(SlackEventObserver<SlackChannelLeftEvent> observer)
    {
        removeObserver(SlackEventType.GROUP_LEFT, observer);
    }

    /**
     * @since 0.1.0
     */
    public void removeGrouplJoinedObserver(SlackEventObserver<SlackChannelJoinedEvent> observer)
    {
        removeObserver(SlackEventType.GROUP_JOINED, observer);
    }

    /**
     * @since 0.1.0
     */
    public void removeGroupRenameObservers(SlackEventObserver<SlackChannelRenameEvent> observer)
    {
        removeObserver(SlackEventType.GROUP_RENAME, observer);
    }

    /**
     * @since 0.1.0
     */
    public void removeHelloObservers(SlackEventObserver<SlackHelloEvent> observer)
    {
        removeObserver(SlackEventType.HELLO, observer);
    }

    /**
     * @since 0.1.0
     */
    public void removeMessageChangedObserver(SlackEventObserver<SlackMessageEvent> observer)
    {
        removeObserver(SlackMessageEventType.MESSAGE_CHANGED, observer);
    }

    /**
     * @since 0.1.0
     */
    public void removeMessageDeletedObserver(SlackEventObserver<SlackMessageEvent> observer)
    {
        removeObserver(SlackMessageEventType.MESSAGE_DELETED, observer);
    }

    /**
     * @since 0.1.0
     */
    public void removeMessagePostedObserver(SlackEventObserver<SlackMessageEvent> observer)
    {
        removeObserver(SlackEventType.MESSAGE, observer);
    }

    /**
     * @since 0.1.0
     */
    public void removePongObserver(SlackEventObserver<SlackPongEvent> observer)
    {
        removeObserver(SlackEventType.PONG, observer);
    }

    /**
     * @since 0.1.0
     */
    public void removeReplyToObserver(SlackEventObserver<SlackReplyToEvent> observer)
    {
        removeObserver(SlackEventType.REPLY_TO, observer);
    }

    /**
     * @since 0.1.0
     */
    public void removeUnknownMessageObserver(SlackEventObserver<SlackUnknownEvent> observer)
    {
        removeObserver(SlackMessageEventType.UNKNOWN, observer);
    }

    /**
     * @since 0.1.0
     */
    public void removeUnknownObserver(SlackEventObserver<SlackUnknownEvent> observer)
    {
        removeObserver(SlackEventType.UNKNOWN, observer);
    }

    /**
     * @since 0.1.0
     */
    public void removeUserChangeObserver(SlackEventObserver<SlackUserChangeEvent> observer)
    {
        removeObserver(SlackEventType.USER_CHANGE, observer);
    }

    /**
     * @since 0.1.0
     */
    public void removeUserTypingObserver(SlackEventObserver<SlackUserTypingEvent> observer)
    {
        removeObserver(SlackEventType.USER_TYPING, observer);
    }

    /**
     * @since 0.1.0
     */
    @SuppressWarnings("unchecked")
    <T, E extends SlackEvent, L extends SlackEventObserver<E>> Collection<L> findObservers(T eventType)
    {
        Collection<L> collection = Collections.emptyList();

        if (observers.containsKey(eventType))
        {
            collection = (Collection<L>) observers.get(eventType);
        }
        else if (messageObservers.containsKey(eventType))
        {
            collection = (Collection<L>) messageObservers.get(eventType);
        }

        // always return a copy in case an event is firing the same time a handle is removed
        return new ArrayList<>(collection);
    }

    <E> boolean isObserverRegistered(E eventType, SlackEventObserver<?> observer)
    {
        if (observers.containsKey(eventType))
        {
            return observers.get(eventType).contains(observer);
        }

        if (messageObservers.containsKey(eventType))
        {
            return messageObservers.get(eventType).contains(observer);
        }

        return false;
    }

    private <E, T extends SlackEvent> void addObserver(SlackEventType eventType, SlackEventObserver<T> observer)
    {
        observers.computeIfAbsent(eventType, k -> new Vector<>()).add(observer);
    }

    /*
     * the 'SlackMessageEvent' class doesn't really lend well to the idea of a 'SlackUnknownMessageEvent', so for now
     * let it be a more relaxed bound
     */
    private <T extends SlackEvent> void addObserver(SlackMessageEventType eventType, SlackEventObserver<T> observer)
    {
        messageObservers.computeIfAbsent(eventType, k -> new Vector<>()).add(observer);
    }

    private <E> void removeObserver(E eventType, SlackEventObserver<?> observer)
    {
        if (observers.containsKey(eventType))
        {
            observers.get(eventType).remove(observer);
        }

        if (messageObservers.containsKey(eventType))
        {
            messageObservers.get(eventType).remove(observer);
        }
    }
}
