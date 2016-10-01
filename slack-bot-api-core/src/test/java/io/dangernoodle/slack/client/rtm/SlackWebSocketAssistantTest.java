package io.dangernoodle.slack.client.rtm;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.dangernoodle.slack.SlackJsonTestFiles;
import io.dangernoodle.slack.client.SlackClient;
import io.dangernoodle.slack.client.SlackClientSettings;
import io.dangernoodle.slack.client.SlackJsonTransformer;
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
import io.dangernoodle.slack.utils.GsonTransformer;


@RunWith(value = JUnitPlatform.class)
public class SlackWebSocketAssistantTest
{
    private static final SlackJsonTransformer transformer = new GsonTransformer();

    private SlackWebSocketAssistant assistant;

    private ArgumentCaptor<? extends SlackEvent> eventCaptor;

    private Class<? extends SlackEvent> eventClass;

    private Object eventType;

    private ArgumentCaptor<Object> eventTypeCaptor;

    @Mock
    private SlackClient mockClient;

    @Mock
    private SlackClientSettings mockClientSettings;

    @Mock
    private SlackEventObserver<SlackEvent> mockObserver;

    @Mock

    private SlackObserverRegistry mockRegistry;

    private SlackJsonTestFiles testFile;

    @BeforeEach
    public void before()
    {
        MockitoAnnotations.initMocks(this);

        when(mockClient.getObserverRegistry()).thenReturn(mockRegistry);
        when(mockRegistry.findObservers(any())).thenReturn(Arrays.asList(mockObserver));

        assistant = new SlackWebSocketAssistant(mockClient, transformer, mockClientSettings);
    }

    @Test
    public void testDispatchChannelCreated()
    {
        testDispatchEvent(SlackJsonTestFiles.channelCreated, SlackEventType.CHANNEL_CREATED, SlackChannelCreatedEvent.class);
    }

    @Test
    public void testDispatchChannelDeleted()
    {
        testDispatchEvent(SlackJsonTestFiles.channelDeleted, SlackEventType.CHANNEL_DELETED, SlackChannelDeletedEvent.class);
    }

    @Test
    public void testDispatchChannelJoined()
    {
        testDispatchEvent(SlackJsonTestFiles.channelJoined, SlackEventType.CHANNEL_JOINED, SlackChannelJoinedEvent.class);
    }

    @Test
    public void testDispatchChannelLeft()
    {
        testDispatchEvent(SlackJsonTestFiles.channelLeft, SlackEventType.CHANNEL_LEFT, SlackChannelLeftEvent.class);
    }

    @Test
    public void testDispatchChannelRename()
    {
        testDispatchEvent(SlackJsonTestFiles.channelRename, SlackEventType.CHANNEL_RENAME, SlackChannelRenameEvent.class);
    }

    @Test
    public void testDispatchGroupJoined()
    {
        testDispatchEvent(SlackJsonTestFiles.groupJoined, SlackEventType.GROUP_JOINED, SlackChannelJoinedEvent.class);
    }

    @Test
    public void testDispatchGrouplLeft()
    {
        testDispatchEvent(SlackJsonTestFiles.groupLeft, SlackEventType.GROUP_LEFT, SlackChannelLeftEvent.class);
    }

    @Test
    public void testDispatchGroupRename()
    {
        testDispatchEvent(SlackJsonTestFiles.groupRename, SlackEventType.GROUP_RENAME, SlackChannelRenameEvent.class);
    }

    @Test
    public void testDispatchHello()
    {
        testDispatchEvent(SlackJsonTestFiles.hello, SlackEventType.HELLO, SlackHelloEvent.class);
    }

    @Test
    public void testDispatchMessage()
    {
        testDispatchEvent(SlackJsonTestFiles.message, SlackEventType.MESSAGE, SlackMessageEvent.class);
    }

    @Test
    public void testDispatchMessageChanged()
    {
        testDispatchEvent(SlackJsonTestFiles.messageChanged, SlackMessageEventType.MESSAGE_CHANGED, SlackMessageEvent.class);
    }

    @Test
    public void testDispatchMessageDeleted()
    {
        testDispatchEvent(SlackJsonTestFiles.messageDeleted, SlackMessageEventType.MESSAGE_DELETED, SlackMessageEvent.class);
    }

    @Test
    public void testDispatchPong()
    {
        testDispatchEvent(SlackJsonTestFiles.pong, SlackEventType.PONG, SlackPongEvent.class);
    }

    @Test
    public void testReployTo()
    {
        testDispatchEvent(SlackJsonTestFiles.replyTo, SlackEventType.REPLY_TO, SlackReplyToEvent.class);
    }

    @Test
    public void testReployToError()
    {
        testDispatchEvent(SlackJsonTestFiles.replyToError, SlackEventType.REPLY_TO, SlackReplyToEvent.class);
    }

    @Test
    public void testUnknownEvent()
    {
        testDispatchEvent(SlackJsonTestFiles.unknownEvent, SlackEventType.UNKNOWN, SlackUnknownEvent.class);
    }

    @Test
    public void testUnknownMessageEvent()
    {
        testDispatchEvent(SlackJsonTestFiles.unknownMessageEvent, SlackMessageEventType.UNKNOWN, SlackUnknownEvent.class);
    }

    @Test
    public void testUserChange()
    {
        testDispatchEvent(SlackJsonTestFiles.userChange, SlackEventType.USER_CHANGE, SlackUserChangeEvent.class);
    }

    @Test
    public void testUserTyping()
    {
        testDispatchEvent(SlackJsonTestFiles.userTyping, SlackEventType.USER_TYPING, SlackUserTypingEvent.class);
    }

    private void givenAJsonTestFile(SlackJsonTestFiles testFile)
    {
        this.testFile = testFile;
    }

    private void givenAnEventType(Object eventType)
    {
        this.eventType = eventType;
    }

    private void givenASlackEventClass(Class<? extends SlackEvent> eventClass)
    {
        this.eventClass = eventClass;
    }

    private void givenDisatchMessageSubtypesEnabled()
    {
        when(mockClientSettings.dispatchMessageSubtypes()).thenReturn(true);
    }

    private void testDispatchEvent(SlackJsonTestFiles testFile, SlackEventType eventType, Class<? extends SlackEvent> clazz)
    {
        givenAJsonTestFile(testFile);
        givenASlackEventClass(clazz);
        givenAnEventType(eventType);
        whenHandleSlackEvent();
        thenObserverWasFound();
        thenObserverWasInvoked();
    }

    private void testDispatchEvent(SlackJsonTestFiles testFile, SlackMessageEventType eventType, Class<? extends SlackEvent> clazz)
    {
        givenDisatchMessageSubtypesEnabled();
        givenAJsonTestFile(testFile);
        givenASlackEventClass(clazz);
        givenAnEventType(eventType);
        whenHandleSlackEvent();
        thenObserverWasFound();
        thenObserverWasInvoked();
    }

    private void thenObserverWasFound()
    {
        verify(mockClient.getObserverRegistry()).findObservers(eventTypeCaptor.capture());
        assertEquals(eventType, eventTypeCaptor.getValue());
    }

    private void thenObserverWasInvoked()
    {
        verify(mockObserver).onEvent(eventCaptor.capture(), any());
        assertThat(eventCaptor.getValue(), instanceOf(eventClass));
    }

    private void whenHandleSlackEvent()
    {
        eventCaptor = ArgumentCaptor.forClass(eventClass);
        eventTypeCaptor = ArgumentCaptor.forClass(Object.class);

        assistant.handleEvent(testFile.loadJson());
    }
}
