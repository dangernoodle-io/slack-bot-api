package io.dangernoodle.slack.events;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import io.dangernoodle.slack.SlackJsonTestFiles;


@RunWith(value = JUnitPlatform.class)
public class SlackPongEventTest
{
    private SlackPongEvent event;
    private SlackJsonTestFiles testFile;

    @Test
    public void testParsePongEventWithArgs()
    {
        this.givenAnEventWithArgs();
        this.whenParseIntoObject();
        this.thenPongEventIsCorrect();
        this.thenAdditionalIsNotEmpty();
        this.thenAdditionalContainsArgs();
    }

    @Test
    public void testParsePongEventWithoutArgs()
    {
        this.givenAnEventWithNoArgs();
        this.whenParseIntoObject();
        this.thenPongEventIsCorrect();
        this.thenAdditionalIsEmpty();
    }

    private void givenAnEventWithArgs()
    {
        this.testFile = SlackJsonTestFiles.pongArgs;
    }

    private void givenAnEventWithNoArgs()
    {
        this.testFile = SlackJsonTestFiles.pong;
    }

    private void thenAdditionalContainsArgs()
    {
        assertThat(event.getAdditional().keySet(), hasItems("a", "b", "c"));
    }

    private void thenAdditionalIsEmpty()
    {
        assertTrue(event.getAdditional().isEmpty());
    }

    private void thenAdditionalIsNotEmpty()
    {
        assertFalse(event.getAdditional().isEmpty());
    }

    private void thenPongEventIsCorrect()
    {
        assertEquals(1L, this.event.getId());
        assertEquals(1474155711601L, this.event.getTime());
        assertEquals(SlackEventType.PONG, event.getType());
    }

    private void whenParseIntoObject()
    {
        this.event = testFile.parseIntoObject(SlackPongEvent.class);
    }
}
