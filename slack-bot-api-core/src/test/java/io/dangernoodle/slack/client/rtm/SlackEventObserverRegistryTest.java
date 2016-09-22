package io.dangernoodle.slack.client.rtm;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import io.dangernoodle.slack.events.SlackEventType;
import io.dangernoodle.slack.events.SlackHelloEvent;
import io.dangernoodle.slack.events.SlackMessageEvent;
import io.dangernoodle.slack.events.SlackPongEvent;
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
        SlackEventObserver<SlackChannelCreatedEvent> observer = (event, client) -> {
            // no-op
        };

        registry.addChannelCreatedObserver(observer);
        assertTrue(registry.isObserverRegistered((Object) SlackEventType.CHANNEL_CREATED, observer));

        registry.removeChannelCreatedObserver(observer);
        assertFalse(registry.isObserverRegistered((Object) SlackEventType.CHANNEL_CREATED, observer));
    }

    @Test
    public void testChannelDeletedObserver()
    {
        SlackEventObserver<SlackChannelDeletedEvent> observer = (event, client) -> {
            // no-op
        };

        registry.addChannelDeletedObserver(observer);
        assertTrue(registry.isObserverRegistered((Object) SlackEventType.CHANNEL_DELETED, observer));

        registry.removeChannelDeletedObserver(observer);
        Assertions.assertFalse(registry.isObserverRegistered((Object) SlackEventType.CHANNEL_DELETED, observer));
    }

    @Test
    public void testChannelJoinedObserver()
    {
        SlackEventObserver<SlackChannelJoinedEvent> observer = (event, client) -> {
            // no-op
        };

        registry.addChannelJoinedObserver(observer);
        assertTrue(registry.isObserverRegistered((Object) SlackEventType.CHANNEL_JOINED, observer));

        registry.removeChannelJoinedObserver(observer);
        assertFalse(registry.isObserverRegistered((Object) SlackEventType.CHANNEL_JOINED, observer));
    }

    @Test
    public void testChannelLeftObserver()
    {
        SlackEventObserver<SlackChannelLeftEvent> observer = (event, client) -> {
            // no-op
        };

        registry.addChannelLeftObserver(observer);
        assertTrue(registry.isObserverRegistered((Object) SlackEventType.CHANNEL_LEFT, observer));

        registry.removeChannelLeftObserver(observer);
        assertFalse(registry.isObserverRegistered((Object) SlackEventType.CHANNEL_LEFT, observer));
    }

    @Test
    public void testChannelRenameObserver()
    {
        SlackEventObserver<SlackChannelRenameEvent> observer = (event, client) -> {
            // no-op
        };

        registry.addChannelRenameObserver(observer);
        Assertions.assertTrue(registry.isObserverRegistered((Object) SlackEventType.CHANNEL_RENAME, observer));

        registry.removeChannelRenameObservers(observer);
        Assertions.assertFalse(registry.isObserverRegistered((Object) SlackEventType.CHANNEL_RENAME, observer));
    }

    @Test
    public void testGroupJoinedObserver()
    {
        SlackEventObserver<SlackChannelJoinedEvent> observer = (event, client) -> {
            // no-op
        };

        registry.addGroupJoinedObserver(observer);
        assertTrue(registry.isObserverRegistered(SlackEventType.GROUP_JOINED, observer));

        registry.removeGroupJoinedObserver(observer);
        assertFalse(registry.isObserverRegistered((Object) SlackEventType.GROUP_JOINED, observer));
    }

    @Test
    public void testGroupLeftObserver()
    {
        SlackEventObserver<SlackChannelLeftEvent> observer = (event, client) -> {
            // no-op
        };

        registry.addGroupLeftObserver(observer);
        assertTrue(registry.isObserverRegistered((Object) SlackEventType.GROUP_LEFT, observer));

        registry.removeGroupLeftObserver(observer);
        assertFalse(registry.isObserverRegistered((Object) SlackEventType.GROUP_LEFT, observer));
    }

    @Test
    public void testGroupRenameObserver()
    {
        SlackEventObserver<SlackChannelRenameEvent> observer = (event, client) -> {
            // no-op
        };

        registry.addGroupRenameObserver(observer);
        assertTrue(registry.isObserverRegistered((Object) SlackEventType.GROUP_RENAME, observer));

        registry.removeGroupRenameObservers(observer);
        assertFalse(registry.isObserverRegistered((Object) SlackEventType.GROUP_RENAME, observer));
    }

    @Test
    public void testHelloObserver()
    {
        SlackEventObserver<SlackHelloEvent> observer = (event, client) -> {
            // no-op
        };

        registry.addHelloObserver(observer);
        assertTrue(registry.isObserverRegistered((Object) SlackEventType.HELLO, observer));

        registry.removeHelloObservers(observer);
        assertFalse(registry.isObserverRegistered((Object) SlackEventType.HELLO, observer));
    }

    @Test
    public void testMessagePostedObserver()
    {
        SlackEventObserver<SlackMessageEvent> observer = (event, client) -> {
            // no-op
        };

        registry.addMessagePostedObserver(observer);
        assertTrue(registry.isObserverRegistered((Object) SlackEventType.MESSAGE, observer));

        registry.removeMessagePostedObserver(observer);
        Assertions.assertFalse(registry.isObserverRegistered((Object) SlackEventType.MESSAGE, observer));
    }

    @Test
    public void testPongObserver()
    {
        SlackEventObserver<SlackPongEvent> observer = (event, client) -> {
            // no-op
        };

        registry.addPongObserver(observer);
        assertTrue(registry.isObserverRegistered((Object) SlackEventType.PONG, observer));

        registry.removePongObserver(observer);
        assertFalse(registry.isObserverRegistered((Object) SlackEventType.PONG, observer));
    }

    @Test
    public void testUserChangeObserver()
    {
        SlackEventObserver<SlackUserChangeEvent> observer = (event, client) -> {
            // no-op
        };

        registry.addUserChangeObserver(observer);
        assertTrue(registry.isObserverRegistered(SlackEventType.USER_CHANGE, observer));

        registry.removeUserChangeObserver(observer);
        Assertions.assertFalse(registry.isObserverRegistered((Object) SlackEventType.USER_CHANGE, observer));
    }

    @Test
    public void testUserTypingObserver()
    {
        SlackEventObserver<SlackUserTypingEvent> observer = (event, client) -> {
            // no-op
        };

        registry.addUserTypingObserver(observer);
        assertTrue(registry.isObserverRegistered((Object) SlackEventType.USER_TYPING, observer));

        registry.removeUserTypingObserver(observer);
        assertFalse(registry.isObserverRegistered((Object) SlackEventType.USER_TYPING, observer));
    }
}
