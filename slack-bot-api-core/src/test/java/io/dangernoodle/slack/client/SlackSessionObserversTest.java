package io.dangernoodle.slack.client;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.dangernoodle.slack.events.SlackPongEvent;
import io.dangernoodle.slack.events.channel.SlackChannelCreatedEvent;
import io.dangernoodle.slack.events.channel.SlackChannelJoinedEvent;
import io.dangernoodle.slack.events.channel.SlackChannelLeftEvent;
import io.dangernoodle.slack.events.user.SlackUserChangeEvent;
import io.dangernoodle.slack.objects.SlackChannel;
import io.dangernoodle.slack.objects.SlackMessageable;
import io.dangernoodle.slack.objects.SlackUser;


@RunWith(value = JUnitPlatform.class)
public class SlackSessionObserversTest
{
    @Mock
    private SlackChannelCreatedEvent mockChannelCreatedEvent;

    @Mock
    private SlackClient mockClient;

    @Mock
    private SlackChannelJoinedEvent mockGroupJoinedEvent;

    @Mock
    private SlackChannelLeftEvent mockGroupLeftEvent;

    @Mock
    private SlackMessageable.Id mockMessageableId;

    @Mock
    private SlackPongEvent mockPongEvent;

    @Mock
    private SlackConnectionSession mockSession;

    @Mock
    private SlackChannel mockSlackChannel;

    @Mock
    private SlackUser mockSlackUser;

    @Mock
    private SlackUserChangeEvent mockUserChangedEvent;

    @BeforeEach
    public void before()
    {
        MockitoAnnotations.initMocks(this);

        when(mockClient.getSession()).thenReturn(mockSession);
        when(mockSlackChannel.getName()).thenReturn("mock-channel");
    }

    @Test
    public void testChannelCreatedObserver()
    {
        givenAChannelCreatedEvent();
        whenChannelCreatedOnEvent();
        thenChannelCreatedEventHandled();
    }

    @Test
    public void testGroupJoinedObserver()
    {
        givenAGroupJoinedEvent();
        whenGroupJoinedOnEvent();
        thenGroupJoinedEventHandled();
    }

    @Test
    public void testGroupLeftObserver()
    {
        givenAGroupLeftEvent();
        whenGroupLeftOnEvent();
        thenGroupLeftEventHandled();
    }

    @Test
    public void testPongObserver()
    {
        givenAPongEvent();
        whenPongOnEvent();
        thenPongEventHandled();
    }

    @Test
    public void testUserChangedObserver()
    {
        givenAUserChangedEvent();
        whenUserChangedOnEvent();
        thenUserChangedEventHandled();
    }

    private void givenAChannelCreatedEvent()
    {
       when(mockChannelCreatedEvent.getChannel()).thenReturn(mockSlackChannel);
    }

    private void givenAGroupJoinedEvent()
    {
        when(mockGroupJoinedEvent.getChannel()).thenReturn(mockSlackChannel);
    }

    private void givenAGroupLeftEvent()
    {
        when(mockGroupLeftEvent.getChannel()).thenReturn(mockMessageableId);
    }

    private void givenAPongEvent()
    {
        when(mockPongEvent.getId()).thenReturn(1L);
        when(mockPongEvent.getTime()).thenReturn(System.currentTimeMillis() - 100);
    }

    private void givenAUserChangedEvent()
    {
        when(mockUserChangedEvent.getUser()).thenReturn(mockSlackUser);
    }

    private void thenChannelCreatedEventHandled()
    {
        verify(mockSession).updateChannels(mockSlackChannel);
    }

    private void thenGroupJoinedEventHandled()
    {
        verify(mockSession).updateChannels(mockSlackChannel);
    }

    private void thenGroupLeftEventHandled()
    {
        verify(mockSession).removeChannel(mockMessageableId);
    }

    private void thenPongEventHandled()
    {
        verify(mockSession).updateLastPingId(mockPongEvent.getId());
    }

    private void thenUserChangedEventHandled()
    {
        verify(mockSession).updateUsers(mockSlackUser);
    }

    private void whenChannelCreatedOnEvent()
    {
        SlackSessionObservers.channelCreatedObserver.onEvent(mockChannelCreatedEvent, mockClient);
    }

    private void whenGroupJoinedOnEvent()
    {
        SlackSessionObservers.groupJoinedObserver.onEvent(mockGroupJoinedEvent, mockClient);
    }

    private void whenGroupLeftOnEvent()
    {
       SlackSessionObservers.groupLeftObserver.onEvent(mockGroupLeftEvent, mockClient);
    }

    private void whenPongOnEvent()
    {
        SlackSessionObservers.pongObserver.onEvent(mockPongEvent, mockClient);
    }

    private void whenUserChangedOnEvent()
    {
        SlackSessionObservers.userChangedObserver.onEvent(mockUserChangedEvent, mockClient);
    }
}
