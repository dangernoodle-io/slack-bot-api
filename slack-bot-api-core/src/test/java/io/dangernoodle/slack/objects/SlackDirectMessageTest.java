package io.dangernoodle.slack.objects;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import io.dangernoodle.slack.SlackJsonTestFiles;


@RunWith(value = JUnitPlatform.class)
public class SlackDirectMessageTest
{
    private SlackDirectMessage directMessage;

    private SlackJsonTestFiles jsonFile;

    @Test
    public void testParseIntoDirectMessage()
    {
        this.givenADirectMessage();
        this.whenParseIntoObject();
        this.thenIsDirectMessage();
        this.thenHasUnreadCounts();
    }

    private void givenADirectMessage()
    {
        this.jsonFile = SlackJsonTestFiles.im;
    }

    private void thenHasUnreadCounts()
    {
        assertTrue(directMessage.getUnreadCount() > 0);
        assertTrue(directMessage.getUnreadCountDisplay() > 0);
    }

    private void thenIsDirectMessage()
    {
        assertTrue(directMessage.isDirectMessage());
        assertFalse(directMessage.isChannel());
        assertFalse(directMessage.isGroup());
        assertFalse(directMessage.isMultiDirectMessage());
    }

    private void whenParseIntoObject()
    {
        this.directMessage = jsonFile.parseIntoObject(SlackDirectMessage.class);
    }
}
