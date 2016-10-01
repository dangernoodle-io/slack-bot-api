package io.dangernoodle.slack.client.rtm;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import io.dangernoodle.slack.events.SlackEvent;
import io.dangernoodle.slack.events.SlackMessageEvent;
import io.dangernoodle.slack.events.SlackMessageEventType;
import io.dangernoodle.slack.events.SlackUnknownEvent;


@RunWith(value = JUnitPlatform.class)
public class SlackMessageObserverRegistryTest
{
    private SlackObserverRegistry registry;

    @BeforeEach
    public void before()
    {
        this.registry = new SlackObserverRegistry();
    }

    @Test
    public void testUnknownMessageEventObserver()
    {
        SlackEventObserver<SlackUnknownEvent> observer = createObserver();

        registry.addUnknownMessageObserver(observer);
        assertTrue(registry.isObserverRegistered(SlackMessageEventType.UNKNOWN, observer));

        registry.removeUnknownMessageObserver(observer);
        assertFalse(registry.isObserverRegistered(SlackMessageEventType.UNKNOWN, observer));
    }

    @Test
    public void testMessageChangedObserver()
    {
        SlackEventObserver<SlackMessageEvent> observer = createObserver();

        registry.addMessageChangedObserver(observer);
        assertTrue(registry.isObserverRegistered(SlackMessageEventType.MESSAGE_CHANGED, observer));

        registry.removeMessageChangedObserver(observer);
        assertFalse(registry.isObserverRegistered(SlackMessageEventType.MESSAGE_CHANGED, observer));
    }

    @Test
    public void testMessageDeletedObserver()
    {
        SlackEventObserver<SlackMessageEvent> observer = createObserver();

        registry.addMessageDeletedObserver(observer);
        assertTrue(registry.isObserverRegistered(SlackMessageEventType.MESSAGE_DELETED, observer));

        registry.removeMessageDeletedObserver(observer);
        assertFalse(registry.isObserverRegistered(SlackMessageEventType.MESSAGE_DELETED, observer));
    }

    private <T extends SlackEvent> SlackEventObserver<T> createObserver()
    {
        return (event, client) -> {
            // no-op
        };
    }
}
