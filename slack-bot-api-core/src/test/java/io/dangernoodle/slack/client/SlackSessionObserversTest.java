package io.dangernoodle.slack.client;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.dangernoodle.slack.events.SlackPongEvent;
import io.dangernoodle.slack.events.channel.SlackChannelCreatedEvent;
import io.dangernoodle.slack.objects.SlackChannel;


@RunWith(value = JUnitPlatform.class)
public class SlackSessionObserversTest
{
    @Mock
    private SlackChannelCreatedEvent mockChannelCreatedEvent;
    @Mock
    private SlackClient mockClient;
    @Mock
    private SlackPongEvent mockPongEvent;
    @Mock
    private SlackConnectionSession mockSession;
    @Mock
    private SlackChannel mockSlackChannel;

    @BeforeEach
    public void before()
    {
        MockitoAnnotations.initMocks(this);
        Mockito.when(mockClient.getSession()).thenReturn(mockSession);
        Mockito.when(mockSlackChannel.getName()).thenReturn("mock-channel");
    }

    @Test
    public void testChannelCreatedObserver()
    {
        this.givenAChannelCreatedEvent();
        this.whenChannelCreatedOnEvent();
        this.thenChannelCreatedEventHandled();
    }

    @Test
    public void testPongObserver()
    {
        this.givenAPongEvent();
        this.whenPongOnEvent();
        this.thenPongEventHandled();
    }

    private void givenAChannelCreatedEvent()
    {
       when(mockChannelCreatedEvent.getChannel()).thenReturn(mockSlackChannel);
    }

    private void givenAPongEvent()
    {
        when(mockPongEvent.getId()).thenReturn(1L);
        when(mockPongEvent.getTime()).thenReturn(System.currentTimeMillis() - 100);
    }

    private void thenChannelCreatedEventHandled()
    {
        ((SlackConnectionSession) Mockito.verify((Object) this.mockSession)).updateChannels(this.mockSlackChannel);
    }

    private void thenPongEventHandled()
    {
        verify(mockSession).updateLastPingId(mockPongEvent.getId());
    }

    private void whenChannelCreatedOnEvent()
    {
        SlackSessionObservers.channelCreatedObserver.onEvent(mockChannelCreatedEvent, this.mockClient);
    }

    private void whenPongOnEvent()
    {
        SlackSessionObservers.pongObserver.onEvent(mockPongEvent, mockClient);
    }
}
