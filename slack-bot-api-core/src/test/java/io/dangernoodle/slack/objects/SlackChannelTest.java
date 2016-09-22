package io.dangernoodle.slack.objects;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import io.dangernoodle.slack.SlackJsonTestFiles;


@RunWith(value = JUnitPlatform.class)
public class SlackChannelTest
{
    private SlackChannel channel;
    private SlackJsonTestFiles testFile;

    @Test
    public void testParseIntoChannel()
    {
        this.givenAChannel();
        this.whenParseIntoObject();
        this.thenIsSlackChannel();
        this.thenIsNotMemberOfChannel();
        this.thenHasUnreadCounts();
    }

    @Test
    public void testParseIntoGroup()
    {
        this.givenAGroup();
        this.whenParseIntoObject();
        this.thenIsSlackGroup();
        this.thenIsMemberOfChannel();
        this.thenHasUnreadCounts();
    }

    private void assertNotDirectMessages()
    {
        assertFalse(channel.isDirectMessage());
        assertFalse(channel.isMultiDirectMessage());
    }

    private void givenAChannel()
    {
        this.testFile = SlackJsonTestFiles.channel;
    }

    private void givenAGroup()
    {
        this.testFile = SlackJsonTestFiles.group;
    }

    private void thenHasUnreadCounts()
    {
        assertTrue(channel.getUnreadCount() > 0);
        assertTrue(channel.getUnreadCountDisplay() > 0);
    }

    private void thenIsMemberOfChannel()
    {
        assertTrue(channel.isMember());
    }

    private void thenIsNotMemberOfChannel()
    {
        assertFalse(channel.isMember());
    }

    private void thenIsSlackChannel()
    {
        assertTrue(channel.isChannel());
        assertFalse(channel.isGroup());
        assertNotDirectMessages();
    }

    private void thenIsSlackGroup()
    {
        assertTrue(channel.isGroup());
        assertFalse(channel.isChannel());
        assertNotDirectMessages();
    }

    private void whenParseIntoObject()
    {
        this.channel = testFile.parseIntoObject(SlackChannel.class);
    }
}
