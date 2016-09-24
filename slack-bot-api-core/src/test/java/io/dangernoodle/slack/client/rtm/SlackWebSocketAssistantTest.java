package io.dangernoodle.slack.client.rtm;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
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
import io.dangernoodle.slack.utils.GsonTransformer;


@RunWith(value = JUnitPlatform.class)
public class SlackWebSocketAssistantTest
{
    private static final SlackJsonTransformer transformer = new GsonTransformer();

    private SlackWebSocketAssistant assistant;

    private ArgumentCaptor<? extends SlackEvent> eventCaptor;

    private Class<? extends SlackEvent> eventClass;

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
        testDispatchEvent(SlackJsonTestFiles.channelCreated, SlackChannelCreatedEvent.class);
    }

    @Test
    public void testDispatchChannelDeleted()
    {
        testDispatchEvent(SlackJsonTestFiles.channelDeleted, SlackChannelDeletedEvent.class);
    }

    @Test
    public void testDispatchChannelJoined()
    {
        testDispatchEvent(SlackJsonTestFiles.channelJoined, SlackChannelJoinedEvent.class);
    }

    @Test
    public void testDispatchChannelLeft()
    {
        testDispatchEvent(SlackJsonTestFiles.channelLeft, SlackChannelLeftEvent.class);
    }

    @Test
    public void testDispatchChannelRename()
    {
        testDispatchEvent(SlackJsonTestFiles.channelRename, SlackChannelRenameEvent.class);
    }

    @Test
    public void testDispatchGroupJoined()
    {
        testDispatchEvent(SlackJsonTestFiles.groupJoined, SlackChannelJoinedEvent.class);
    }

    @Test
    public void testDispatchGrouplLeft()
    {
        testDispatchEvent(SlackJsonTestFiles.groupLeft, SlackChannelLeftEvent.class);
    }

    @Test
    public void testDispatchGroupRename()
    {
        testDispatchEvent(SlackJsonTestFiles.groupRename, SlackChannelRenameEvent.class);
    }

    @Test
    public void testDispatchHello()
    {
        testDispatchEvent(SlackJsonTestFiles.hello, SlackHelloEvent.class);
    }

    @Test
    public void testDispatchMessage()
    {
        testDispatchEvent(SlackJsonTestFiles.message, SlackMessageEvent.class);
    }

    @Test
    public void testDispatchPong()
    {
        testDispatchEvent(SlackJsonTestFiles.pong, SlackPongEvent.class);
    }

    @Test
    public void testUserChange()
    {
        testDispatchEvent(SlackJsonTestFiles.userChange, SlackUserChangeEvent.class);
    }

    @Test
    public void testUserTyping()
    {
        testDispatchEvent(SlackJsonTestFiles.userTyping, SlackUserTypingEvent.class);
    }

    private void givenAJsonTestFile(SlackJsonTestFiles testFile)
    {
        this.testFile = testFile;
    }

    private void givenASlackEventClass(Class<? extends SlackEvent> eventClass)
    {
        this.eventClass = eventClass;
    }

    private void testDispatchEvent(SlackJsonTestFiles testFile, Class<? extends SlackEvent> clazz)
    {
        this.givenAJsonTestFile(testFile);
        this.givenASlackEventClass(clazz);
        this.whenHandleSlackEvent();
        this.thenObserverWasInvoked();
    }

    private void thenObserverWasInvoked()
    {
        verify(mockObserver).onEvent(eventCaptor.capture(), any());
        assertThat(eventCaptor.getValue(), instanceOf(eventClass));
    }

    private void whenHandleSlackEvent()
    {
        eventCaptor = ArgumentCaptor.forClass(eventClass);
        assistant.handleEvent(testFile.loadJson());
    }
}
