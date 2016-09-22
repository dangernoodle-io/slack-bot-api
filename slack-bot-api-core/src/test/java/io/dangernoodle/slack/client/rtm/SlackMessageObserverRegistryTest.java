package io.dangernoodle.slack.client.rtm;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import io.dangernoodle.slack.events.SlackMessageEvent;
import io.dangernoodle.slack.events.SlackMessageEventType;


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
    public void testMessageChangedObserver()
    {
        SlackEventObserver<SlackMessageEvent> observer = (event, client) -> {
            // no-op
        };

        registry.addMessageChangedObserver(observer);
        assertTrue(registry.isObserverRegistered(SlackMessageEventType.MESSAGE_CHANGED, observer));

        this.registry.removeMessageChangedObserver(observer);
        assertFalse(registry.isObserverRegistered(SlackMessageEventType.MESSAGE_CHANGED, observer));
    }

    @Test
    public void testMessageDeletedObserver()
    {
        SlackEventObserver<SlackMessageEvent> observer = (event, client) -> {
            // no-op
        };

        registry.addMessageDeletedObserver(observer);
        assertTrue(registry.isObserverRegistered((Object) SlackMessageEventType.MESSAGE_DELETED, observer));

        this.registry.removeMessageDeletedObserver(observer);
        assertFalse(registry.isObserverRegistered((Object) SlackMessageEventType.MESSAGE_DELETED, observer));
    }
}
