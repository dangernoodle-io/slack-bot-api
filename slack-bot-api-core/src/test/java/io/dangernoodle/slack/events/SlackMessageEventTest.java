package io.dangernoodle.slack.events;

import io.dangernoodle.slack.SlackJsonTestFiles;
import io.dangernoodle.slack.events.SlackEventType;
import io.dangernoodle.slack.events.SlackMessageEvent;
import io.dangernoodle.slack.events.SlackMessageEventType;
import io.dangernoodle.slack.objects.SlackMessage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;


@RunWith(value = JUnitPlatform.class)
public class SlackMessageEventTest
{
    private SlackMessageEvent event;
    private SlackMessageEventType subType;
    private SlackJsonTestFiles testFile;

    @Test
    public void testParseChangeEvent()
    {
        this.givenAChangedMessage();
        this.whenParseIntoObject();
        this.thenSlackEventIsCorrect();
        this.thenMessageChangedEventIsCorrect();
        this.thenSubtypeIsCorrect();
    }

    @Test
    public void testParseMessageDeletedEvent()
    {
        this.givenADeletedMessage();
        this.whenParseIntoObject();
        this.thenSlackEventIsCorrect();
        this.thenMessageDeletedEventIsCorrect();
        this.thenSubtypeIsCorrect();
    }

    @Test
    public void testParseMessageEvent()
    {
        this.givenAMessage();
        this.whenParseIntoObject();
        this.thenSlackEventIsCorrect();
        this.thenMessageEventTextIsCorrect();
        this.thenSubtypeIsNull();
    }

    private void givenAChangedMessage()
    {
        this.subType = SlackMessageEventType.MESSAGE_CHANGED;
        this.testFile = SlackJsonTestFiles.messageChanged;
    }

    private void givenADeletedMessage()
    {
        this.subType = SlackMessageEventType.MESSAGE_DELETED;
        this.testFile = SlackJsonTestFiles.messageDeleted;
    }

    private void givenAMessage()
    {
        this.testFile = SlackJsonTestFiles.message;
    }

    private void thenMessageChangedEventIsCorrect()
    {
        assertNotNull(event.getMessage());

        SlackMessage message = event.getMessage().getEditted();
        assertEquals("editted", message.getText());
    }

    private void thenMessageDeletedEventIsCorrect()
    {
        assertNotNull(event.getMessage().getPrevious());

        SlackMessage message = this.event.getMessage().getPrevious();
        Assertions.assertEquals("test editted again", message.getText());
    }

    private void thenMessageEventTextIsCorrect()
    {
        assertEquals("Hello world", event.getMessage().getText());
    }

    private void thenSlackEventIsCorrect()
    {
        assertNotNull(event.getMessage());
        assertEquals(SlackEventType.MESSAGE, event.getType());
    }

    private void thenSubtypeIsCorrect()
    {
        assertEquals(subType, event.getSubType());
    }

    private void thenSubtypeIsNull()
    {
        assertNull(event.getSubType());
    }

    private void whenParseIntoObject()
    {
        this.event = testFile.parseIntoObject(SlackMessageEvent.class);
    }
}
