package io.dangernoodle.slack.client.rtm;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import io.dangernoodle.slack.events.SlackErrorEvent;
import io.dangernoodle.slack.events.SlackEvent;
import io.dangernoodle.slack.events.SlackEventType;
import io.dangernoodle.slack.events.SlackHelloEvent;
import io.dangernoodle.slack.events.SlackMessageEvent;
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


@RunWith(value = JUnitPlatform.class)
public class SlackEventObserverRegistryTest
{
    private SlackObserverRegistry registry;

    @BeforeEach
    public void before()
    {
        this.registry = new SlackObserverRegistry();
    }

    @Test
    public void testChannelCreatedObserver()
    {
        SlackEventObserver<SlackChannelCreatedEvent> observer = createObserver();

        registry.addChannelCreatedObserver(observer);
        assertTrue(registry.isObserverRegistered(SlackEventType.CHANNEL_CREATED, observer));

        registry.removeChannelCreatedObserver(observer);
        assertFalse(registry.isObserverRegistered(SlackEventType.CHANNEL_CREATED, observer));
    }

    @Test
    public void testChannelDeletedObserver()
    {
        SlackEventObserver<SlackChannelDeletedEvent> observer = createObserver();

        registry.addChannelDeletedObserver(observer);
        assertTrue(registry.isObserverRegistered(SlackEventType.CHANNEL_DELETED, observer));

        registry.removeChannelDeletedObserver(observer);
        assertFalse(registry.isObserverRegistered(SlackEventType.CHANNEL_DELETED, observer));
    }

    @Test
    public void testChannelJoinedObserver()
    {
        SlackEventObserver<SlackChannelJoinedEvent> observer = createObserver();

        registry.addChannelJoinedObserver(observer);
        assertTrue(registry.isObserverRegistered(SlackEventType.CHANNEL_JOINED, observer));

        registry.removeChannelJoinedObserver(observer);
        assertFalse(registry.isObserverRegistered(SlackEventType.CHANNEL_JOINED, observer));
    }

    @Test
    public void testChannelLeftObserver()
    {
        SlackEventObserver<SlackChannelLeftEvent> observer = createObserver();

        registry.addChannelLeftObserver(observer);
        assertTrue(registry.isObserverRegistered(SlackEventType.CHANNEL_LEFT, observer));

        registry.removeChannelLeftObserver(observer);
        assertFalse(registry.isObserverRegistered(SlackEventType.CHANNEL_LEFT, observer));
    }

    @Test
    public void testChannelRenameObserver()
    {
        SlackEventObserver<SlackChannelRenameEvent> observer = createObserver();

        registry.addChannelRenameObserver(observer);
        Assertions.assertTrue(registry.isObserverRegistered(SlackEventType.CHANNEL_RENAME, observer));

        registry.removeChannelRenameObservers(observer);
        Assertions.assertFalse(registry.isObserverRegistered(SlackEventType.CHANNEL_RENAME, observer));
    }

    @Test
    public void testErrorObserver()
    {
        SlackEventObserver<SlackErrorEvent> observer = createObserver();

        registry.addErrorObserver(observer);
        assertTrue(registry.isObserverRegistered(SlackEventType.ERROR, observer));

        registry.removeErrorObserver(observer);
        assertFalse(registry.isObserverRegistered(SlackEventType.ERROR, observer));
    }

    @Test
    public void testGroupJoinedObserver()
    {
        SlackEventObserver<SlackChannelJoinedEvent> observer = createObserver();

        registry.addGroupJoinedObserver(observer);
        assertTrue(registry.isObserverRegistered(SlackEventType.GROUP_JOINED, observer));

        registry.removeGroupJoinedObserver(observer);
        assertFalse(registry.isObserverRegistered(SlackEventType.GROUP_JOINED, observer));
    }

    @Test
    public void testGroupLeftObserver()
    {
        SlackEventObserver<SlackChannelLeftEvent> observer = createObserver();

        registry.addGroupLeftObserver(observer);
        assertTrue(registry.isObserverRegistered(SlackEventType.GROUP_LEFT, observer));

        registry.removeGroupLeftObserver(observer);
        assertFalse(registry.isObserverRegistered(SlackEventType.GROUP_LEFT, observer));
    }

    @Test
    public void testGroupRenameObserver()
    {
        SlackEventObserver<SlackChannelRenameEvent> observer = createObserver();

        registry.addGroupRenameObserver(observer);
        assertTrue(registry.isObserverRegistered(SlackEventType.GROUP_RENAME, observer));

        registry.removeGroupRenameObservers(observer);
        assertFalse(registry.isObserverRegistered(SlackEventType.GROUP_RENAME, observer));
    }

    @Test
    public void testHelloObserver()
    {
        SlackEventObserver<SlackHelloEvent> observer = createObserver();

        registry.addHelloObserver(observer);
        assertTrue(registry.isObserverRegistered(SlackEventType.HELLO, observer));

        registry.removeHelloObservers(observer);
        assertFalse(registry.isObserverRegistered(SlackEventType.HELLO, observer));
    }

    @Test
    public void testMessagePostedObserver()
    {
        SlackEventObserver<SlackMessageEvent> observer = createObserver();

        registry.addMessagePostedObserver(observer);
        assertTrue(registry.isObserverRegistered(SlackEventType.MESSAGE, observer));

        registry.removeMessagePostedObserver(observer);
        assertFalse(registry.isObserverRegistered(SlackEventType.MESSAGE, observer));
    }

    @Test
    public void testPongObserver()
    {
        SlackEventObserver<SlackPongEvent> observer = createObserver();

        registry.addPongObserver(observer);
        assertTrue(registry.isObserverRegistered(SlackEventType.PONG, observer));

        registry.removePongObserver(observer);
        assertFalse(registry.isObserverRegistered(SlackEventType.PONG, observer));
    }

    @Test
    public void testReplyToObserver()
    {
        SlackEventObserver<SlackReplyToEvent> observer = createObserver();

        registry.addReplyToObserver(observer);
        assertTrue(registry.isObserverRegistered(SlackEventType.REPLY_TO, observer));

        registry.removeReplyToObserver(observer);
        assertFalse(registry.isObserverRegistered(SlackEventType.REPLY_TO, observer));
    }

    @Test
    public void testUnknownObserver()
    {
        SlackEventObserver<SlackUnknownEvent> observer = createObserver();

        registry.addUnknownObserver(observer);
        assertTrue(registry.isObserverRegistered(SlackEventType.UNKNOWN, observer));

        registry.removeUnknownObserver(observer);
        assertFalse(registry.isObserverRegistered(SlackEventType.UNKNOWN, observer));
    }

    @Test
    public void testUserChangeObserver()
    {
        SlackEventObserver<SlackUserChangeEvent> observer = createObserver();

        registry.addUserChangeObserver(observer);
        assertTrue(registry.isObserverRegistered(SlackEventType.USER_CHANGE, observer));

        registry.removeUserChangeObserver(observer);
        assertFalse(registry.isObserverRegistered(SlackEventType.USER_CHANGE, observer));
    }

    @Test
    public void testUserTypingObserver()
    {
        SlackEventObserver<SlackUserTypingEvent> observer = createObserver();

        registry.addUserTypingObserver(observer);
        assertTrue(registry.isObserverRegistered(SlackEventType.USER_TYPING, observer));

        registry.removeUserTypingObserver(observer);
        assertFalse(registry.isObserverRegistered(SlackEventType.USER_TYPING, observer));
    }

    private <T extends SlackEvent> SlackEventObserver<T> createObserver()
    {
        return (event, client) -> {
            // no-op
        };
    }
}
