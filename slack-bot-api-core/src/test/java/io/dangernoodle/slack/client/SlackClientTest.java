package io.dangernoodle.slack.client;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ConnectException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.dangernoodle.slack.client.SlackClient.SimpleMessage;
import io.dangernoodle.slack.client.rtm.SlackObserverRegistry;
import io.dangernoodle.slack.client.rtm.SlackWebSocketClient;
import io.dangernoodle.slack.client.web.SlackWebClient;
import io.dangernoodle.slack.events.SlackEventType;
import io.dangernoodle.slack.objects.SlackMessageable;
import io.dangernoodle.slack.objects.api.SlackStartRtmResponse;


@RunWith(value = JUnitPlatform.class)
public class SlackClientTest
{
    private static final String ERROR = "error";
    private static final SlackMessageable.Id ID = new SlackMessageable.Id("id");

    private static final String URL = "ws://endpoint.com";

    private Exception actualException;

    private ArgumentCaptor<SlackClient.SimpleMessage> captor;

    private SlackClient client;

    private long messageId;

    @Mock
    private SlackWebClient mockApiClient;

    @Mock
    private SlackConnectionMonitor mockMonitor;

    @Mock
    private SlackObserverRegistry mockRegistry;

    @Mock
    private SlackStartRtmResponse mockResponse;

    @Mock
    private SlackWebSocketClient mockRtmClient;

    @Mock
    private SlackConnectionSession mockSession;

    @Mock
    private SlackClientSettings mockSettings;

    private long pingId;

    private SlackConnectionMonitor realMonitor;

    private SlackObserverRegistry realRegistry;

    private SlackConnectionSession realSession;

    @BeforeEach
    public void before()
    {
        MockitoAnnotations.initMocks(this);
        client = createSlackClient();
    }

    @Test
    public void testConnect() throws Exception
    {
        whenIssueConnect();
        thenMonitorIsStarted();
    }

    @Test
    public void testCreateMonitor()
    {
        givenClientSettingsForMonitoring();

        // re-create the client so it picks up the mock settings
        client = createSlackClient();

        thenMonitorIsConfiguredCorrectly();
    }

    @Test
    public void testDefaultObserversRegistered()
    {
        thenPongListenerRegistered();
        thenGroupJoinedObserverRegistered();
        thenChannelCreatedOberserverRegistered();

        // safe guard to make sure this test is updated for all default observers
        thenNoOtherObserversRegistered();
    }

    @Test
    public void testDisconnect() throws Exception
    {
        whenDisconnect();
        thenMonitorWasStopped();
        thenRtmClientDisconnected();
    }

    @Test
    public void testGetRegistry()
    {
        assertNotNull(realRegistry);
    }

    @Test
    public void testGetSession()
    {
        assertNotNull(realSession);
    }

    @Test
    public void testIsConnected()
    {
        whenCheckIfConnected();
        thenRtmConnectionChecked();
    }

    @Test
    public void testReconnectFailedRtm() throws Exception
    {
        givenAFailedRtmConnection();
        whenReconnect();
        thenConnectionExceptionThrown();
        thenExceptionMessageMatches();
    }

    @Test
    public void testReconnectFailedWebSocket() throws Exception
    {
        givenAnSuccessulRtmConnection();
        givenAFailedWebSocketConnection();
        whenReconnect();
        thenConnectionExceptionThrown();
    }

    @Test
    public void testReconnectSuccess() throws Exception
    {
        givenAnSuccessulRtmConnection();
        givenASuccessfulWebSocketConnection();
        whenReconnect();
        thenSessionIsUpdated();
        thenRtmClientIsConnected();
        thenNextMessageIdMatches();
    }

    @Test
    public void testSendMessage() throws Exception
    {
        givenAMessageWillBeSent();
        whenSendMessage();
        thenMessageWasSent();
    }

    @Test
    public void testSendPing() throws Exception
    {
        whenSendPing();
        thenPingWasSent();
        thenPingIdWasReturned();
    }

    @Test
    public void testSendThrowsError() throws Exception
    {
        givenSendWillThrowAnException();
        whenSendMessage();
        thenUncheckedIOExceptionThrown();
    }

    void thenExceptionMessageMatches()
    {
        assertEquals(ERROR, actualException.getMessage());
    }

    private SlackClientBuilder createBuilder()
    {
        return new SlackClientBuilder(mockSettings)
        {
            @Override
            SlackWebSocketClient getRtmClient(SlackClient slackClient)
            {
                return mockRtmClient;
            }

            @Override
            SlackWebClient getWebClient()
            {
                return mockApiClient;
            }
        };
    }

    private SlackClient createSlackClient()
    {
        return new SlackClient(createBuilder())
        {
            @Override
            public SlackConnectionSession createConnectionSession()
            {
                realSession = super.createConnectionSession();
                return mockSession;
            }

            @Override
            SlackConnectionMonitor createConnectionMonitor()
            {
                realMonitor = super.createConnectionMonitor();
                return mockMonitor;
            }

            @Override
            SlackObserverRegistry createObserverRegistry()
            {
                realRegistry = super.createObserverRegistry();
                return mockRegistry;
            }

            @Override
            void logSessionEstablished(SlackStartRtmResponse response)
            {
                // i don't feel like mocking out the calls for log statements
            }
        };
    }

    private void givenAFailedRtmConnection() throws IOException
    {
        when(mockResponse.getError()).thenReturn(ERROR);
        when(mockApiClient.initiateRtmConnection()).thenReturn(mockResponse);
    }

    private void givenAFailedWebSocketConnection()
    {
        when(mockRtmClient.isConnected()).thenReturn(false);
    }

    private void givenAMessageWillBeSent()
    {
        captor = ArgumentCaptor.forClass(SlackClient.SimpleMessage.class);
    }

    private void givenAnSuccessulRtmConnection() throws IOException
    {
        when(mockResponse.isOk()).thenReturn(true);
        when(mockResponse.getUrl()).thenReturn(URL);

        when(mockApiClient.initiateRtmConnection()).thenReturn(mockResponse);
    }

    private void givenASuccessfulWebSocketConnection()
    {
        when(mockRtmClient.isConnected()).thenReturn(true);
    }

    private void givenClientSettingsForMonitoring()
    {
        when(mockSettings.getHeartbeat()).thenReturn(1);
        when(mockSettings.getReconnect()).thenReturn(true);
    }

    private void givenSendWillThrowAnException() throws IOException
    {
        doThrow(IOException.class).when(mockRtmClient).send(any());
    }

    private void thenChannelCreatedOberserverRegistered()
    {
        verify(mockRegistry).addGroupJoinedObserver(SlackSessionObservers.groupJoinedObserver);
    }

    private void thenConnectionExceptionThrown()
    {
        assertNotNull(actualException);
        assertThat(actualException, instanceOf(ConnectException.class));
    }

    private void thenGroupJoinedObserverRegistered()
    {
        verify(mockRegistry).addChannelCreatedObserver(SlackSessionObservers.channelCreatedObserver);
    }

    private void thenMessageWasSent() throws Exception
    {
        verify(mockRtmClient).send(captor.capture());

        SimpleMessage message = captor.getValue();
        assertNotNull(message);

        assertEquals(messageId, message.id);

        assertEquals("message", message.text);
        assertEquals(ID.value(), message.channel);

        assertEquals(SlackEventType.MESSAGE.toType(), message.type);
    }

    private void thenMonitorIsConfiguredCorrectly()
    {
        assertEquals(1, realMonitor.getHeartbeat());
        assertEquals(true, realMonitor.getReconnect());
    }

    private void thenMonitorIsStarted()
    {
        verify(mockMonitor).start();
    }

    private void thenMonitorWasStopped()
    {
        verify(mockMonitor).stop();
    }

    private void thenNextMessageIdMatches()
    {
        verify(mockSession).updateLastPingId(messageId);
    }

    private void thenNoOtherObserversRegistered()
    {
        verifyNoMoreInteractions(mockRegistry);
    }

    private void thenPingIdWasReturned()
    {
        assertEquals(1L, pingId);
    }

    private void thenPingWasSent() throws IOException
    {
        verify(mockRtmClient).send(any());
    }

    private void thenPongListenerRegistered()
    {
        verify(mockRegistry).addPongObserver(SlackSessionObservers.pongObserver);
    }

    private void thenRtmClientDisconnected() throws IOException
    {
        verify(mockRtmClient).disconnect();
    }

    private void thenRtmClientIsConnected() throws IOException
    {
        verify(mockRtmClient).connect(URL);
    }

    private void thenRtmConnectionChecked()
    {
        verify(mockRtmClient).isConnected();
    }

    private void thenSessionIsUpdated()
    {
        verify(mockSession).updateSession(mockResponse);
    }

    private void thenUncheckedIOExceptionThrown()
    {
        assertNotNull(actualException);
        assertThat(actualException, instanceOf(UncheckedIOException.class));
    }

    private void whenCheckIfConnected()
    {
        client.isConnected();
    }

    private void whenDisconnect()
    {
        try
        {
            client.disconnet();
        }
        catch (Exception e)
        {
            actualException = e;
        }
    }

    private void whenIssueConnect()
    {
        client.connect();
    }

    private void whenReconnect()
    {
        try
        {
            messageId = client.reconnect();
        }
        catch (Exception e)
        {
            actualException = e;
        }
    }

    private void whenSendMessage()
    {
        try
        {
            messageId = client.send(ID, "message");
        }
        catch (Exception e)
        {
            actualException = e;
        }
    }

    private void whenSendPing()
    {
        pingId = client.sendPing();
    }
}
