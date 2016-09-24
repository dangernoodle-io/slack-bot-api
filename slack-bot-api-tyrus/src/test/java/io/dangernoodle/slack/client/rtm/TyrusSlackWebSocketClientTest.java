package io.dangernoodle.slack.client.rtm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.DeploymentException;
import javax.websocket.Endpoint;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;

import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.client.ClientProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.dangernoodle.slack.utils.ProxySettings;


@RunWith(value = JUnitPlatform.class)
public class TyrusSlackWebSocketClientTest
{
    private static final String WS_URL = "ws://endpoint.com";

    private boolean actualBool;

    private TyrusSlackWebSocketClient client;

    @Mock
    private SlackWebSocketAssistant mockAssistant;

    @Mock
    private ClientManager mockContainer;

    private ProxySettings mockProxy;

    @Mock
    private Basic mockRemote;

    private Session mockSession;

    private Map<String, Object> properties = new HashMap<>();

    @BeforeEach
    public void before()
    {
        MockitoAnnotations.initMocks(this);
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
        givenAClientWithProxy();
        whenConnect();
        whenSendMessage();
        thenMessageWasSent();
    }

    @Test
    public void testSessionConnected() throws Exception
    {
        givenAClient();
        whenConnect();
        thenProxyWasNotConfiguredOnClient();
        thenClientConnected();
    }

    @Test
    public void testSessionConnectedWithProxy() throws Exception
    {
        givenAClientWithProxy();
        whenConnect();
        thenProxyWasConfiguredOnClient();
        thenClientConnected();
    }

    private TyrusSlackWebSocketClient createTyrusClient(ProxySettings proxySettings)
    {
        return new TyrusSlackWebSocketClient(mockAssistant, proxySettings)
        {
            @Override
            ClientManager createClientManager()
            {
                return mockContainer;
            }
        };
    }

    private void givenAClient()
    {
        client = createTyrusClient(mockProxy);
    }

    private void givenAClientWithProxy()
    {
        mockProxy = mock(ProxySettings.class);
        when(mockProxy.toProxyUrl()).thenReturn(WS_URL);

        when(mockContainer.getProperties()).thenReturn(properties);

        client = createTyrusClient(mockProxy);
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
        verifyNoMoreInteractions(mockAssistant, mockContainer);
    }

    private void thenClientConnected() throws DeploymentException, IOException
    {
        verify(mockContainer).connectToServer(any(Endpoint.class), any(URI.class));
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
        verify(mockRemote).sendText(WS_URL);
    }

    private void thenNotConnected()
    {
        assertFalse(actualBool);
    }

    private void thenNotSecure()
    {
        assertFalse(actualBool);
    }

    private void thenProxyWasConfiguredOnClient()
    {
        assertTrue(properties.containsKey(ClientProperties.PROXY_URI));
        assertEquals(WS_URL, properties.get(ClientProperties.PROXY_URI));
    }

    private void thenProxyWasNotConfiguredOnClient()
    {
        verify(mockContainer, never()).getProperties();
    }

    private void thenSessionWasClosed() throws IOException
    {
        verify(mockSession).close();
    }

    private void whenCallOnMessage()
    {
        // don't care what this value is...
        client.onMessage(WS_URL);
    }

    private void whenConnect() throws DeploymentException, IOException
    {
        whenConnect(false, false);
    }

    private void whenConnect(boolean open, boolean secure) throws DeploymentException, IOException
    {
        mockSession = mock(Session.class);

        when(mockSession.getBasicRemote()).thenReturn(mockRemote);
        when(mockContainer.connectToServer(any(Endpoint.class), any(URI.class))).thenReturn(mockSession);

        client.connect(WS_URL);
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
