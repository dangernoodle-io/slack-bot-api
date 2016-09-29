package io.dangernoodle.slack.client.rtm;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.Future;

import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


@RunWith(value = JUnitPlatform.class)
public class JettySlackWebSocketClientTest
{
    private static final String WS_URL = "ws://endpoint.com";

    private boolean actualBool;

    private Exception actualException;

    private JettySlackWebSocketClient client;

    @Mock
    private SlackWebSocketAssistant mockAssistant;

    @Mock
    private RemoteEndpoint mockRemote;

    private Session mockSession;

    @Mock
    private WebSocketClient mockWebSocket;

    @BeforeEach
    public void before()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Disabled("causes corrupt jacoco.exec files on travis-ci")
    public void testConnectInterrupted() throws Exception
    {
        givenAClient();
        whenConnectIsInterrupted();
        thenThreadIsInterrupted();
        thenWrappedInterruptedExecptionIsThrown();
    }

    @Test
    public void testDisconnect() throws Exception
    {
        givenAClient();
        whenConnect();
        whenDisconnect();
        thenSessionWasClosed();

        whenDisconnect();
        // meh...
        verifyNoMoreInteractions(mockSession);
    }

    @Test
    public void testIsConnected() throws Exception
    {
        givenAClient();
        whenIsConnected();
        thenNotConnected();

        whenConnect();
        whenIsConnected();
        thenNotConnected();

        givenConnected();
        whenIsConnected();
        thenIsConnected();
    }

    @Test
    public void testIsSecure() throws Exception
    {
        givenAClient();
        whenIsSecure();
        thenNotSecure();

        whenConnect();
        whenIsSecure();
        thenNotSecure();

        givenIsSecure();
        whenIsSecure();
        thenIsSecure();
    }

    @Test
    public void testOnMessage()
    {
        givenAClient();
        whenCallOnMessage();
        thenAssistantWasInvoked();
    }

    @Test
    public void testSendMessage() throws Exception
    {
        givenAClient();
        whenConnect();
        whenSendMessage();
        thenMessageWasSent();
    }

    @Test
    public void testSessionConnected() throws Exception
    {
        givenAClient();
        whenConnect();
        thenClientConnected();
    }

    private void givenAClient()
    {
        client = new JettySlackWebSocketClient(mockAssistant)
        {
            @Override
            WebSocketClient createWebSocketClient()
            {
                return mockWebSocket;
            }

            @Override
            void startClient() throws Exception
            {
                // no-op, these methods can't be mocked
            }
        };
    }

    private void givenConnected()
    {
        when(mockSession.isOpen()).thenReturn(true);
    }

    private void givenIsSecure()
    {
        when(mockSession.isSecure()).thenReturn(true);
    }

    private void thenAssistantWasInvoked()
    {
        verify(mockAssistant).handleEvent(WS_URL);
        verifyNoMoreInteractions(mockAssistant, mockWebSocket);
    }

    private void thenClientConnected() throws IOException
    {
        verify(mockWebSocket).connect(any(WebSocketListener.class), any(URI.class));
    }

    private void thenIsConnected()
    {
        assertTrue(actualBool);
        // account for invocation after connection is made
        verify(mockSession, times(2)).isOpen();
    }

    private void thenIsSecure()
    {
        assertTrue(actualBool);
        // account for invocation after connection is made
        verify(mockSession, times(2)).isSecure();
    }

    private void thenMessageWasSent() throws IOException
    {
        verify(mockAssistant).serialize(WS_URL);
        verify(mockRemote).sendString(WS_URL);
    }

    private void thenNotConnected()
    {
        assertFalse(actualBool);
    }

    private void thenNotSecure()
    {
        assertFalse(actualBool);
    }

    private void thenSessionWasClosed()
    {
        verify(mockSession).close();
    }

    private void thenThreadIsInterrupted()
    {
        assertTrue(Thread.currentThread().isInterrupted());
    }

    private void thenWrappedInterruptedExecptionIsThrown()
    {
        assertNotNull(actualException);
        assertThat(actualException, instanceOf(IOException.class));
        assertThat(actualException.getCause(), instanceOf(InterruptedException.class));
    }

    private void whenCallOnMessage()
    {
        // don't care what this value is...
        client.onWebSocketText(WS_URL);
    }

    private void whenConnect() throws Exception
    {
        mockSession = mock(Session.class);

        @SuppressWarnings("unchecked")
        Future<Session> mockFuture = mock(Future.class);

        when(mockFuture.get()).thenReturn(mockSession);
        when(mockSession.getRemote()).thenReturn(mockRemote);

        when(mockWebSocket.connect(any(WebSocketListener.class), any(URI.class))).thenReturn(mockFuture);

        client.connect(WS_URL);
    }

    private void whenConnectIsInterrupted() throws Exception
    {
        doThrow(InterruptedException.class).when(mockWebSocket).connect(any(WebSocketListener.class), any(URI.class));

        try
        {
            client.connect(WS_URL);
        }
        catch (Exception e)
        {
            actualException = e;
        }
    }

    private void whenDisconnect() throws IOException
    {
        client.disconnect();
    }

    private void whenIsConnected()
    {
        actualBool = client.isConnected();
    }

    private void whenIsSecure()
    {
        actualBool = client.isSecure();
    }

    private void whenSendMessage() throws IOException
    {
        // don't care what this value is...
        when(mockAssistant.serialize(WS_URL)).thenReturn(WS_URL);
        client.send(WS_URL);
    }

}
