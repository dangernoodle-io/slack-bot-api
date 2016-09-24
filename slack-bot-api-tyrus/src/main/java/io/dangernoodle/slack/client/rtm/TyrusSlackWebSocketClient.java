package io.dangernoodle.slack.client.rtm;

import java.io.IOException;
import java.net.URI;

import javax.websocket.DeploymentException;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.client.ClientProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dangernoodle.slack.utils.ProxySettings;


/**
 * WebSocket client implementation using <a href="https://tyrus.java.net">Project Tyrus</a>
 *
 * @since 0.1.0
 */
public class TyrusSlackWebSocketClient implements SlackWebSocketClient, MessageHandler.Whole<String>
{
    private static final Logger logger = LoggerFactory.getLogger(TyrusSlackWebSocketClient.class);

    private final SlackWebSocketAssistant assistant;

    private final WebSocketContainer container;

    private Session session;

    public TyrusSlackWebSocketClient(SlackWebSocketAssistant assistant, ProxySettings proxySettings)
    {
        this.assistant = assistant;
        this.container = createWebSocketContainer(proxySettings);
    }

    @Override
    public synchronized void connect(String url) throws IOException
    {
        try
        {
            session = container.connectToServer(new Endpoint()
            {
                @Override
                public void onError(Session session, Throwable thr)
                {
                    logger.error("tyrus 'onError', message processing failed", thr);
                }

                @Override
                public void onOpen(Session session, EndpointConfig config)
                {
                    session.addMessageHandler(TyrusSlackWebSocketClient.this);
                }

            }, URI.create(url));
        }
        catch (DeploymentException e)
        {
            logger.warn("failed to establish connection to [{}]", url, e);
        }
    }

    @Override
    public synchronized void disconnect() throws IOException
    {
        if (session != null)
        {
            session.close();
            session = null;
        }
    }

    @Override
    public synchronized boolean isConnected()
    {
        return (session == null) ? false : session.isOpen();
    }

    @Override
    public synchronized boolean isSecure()
    {
        return (session == null) ? false : session.isSecure();
    }

    @Override
    public void onMessage(String message)
    {
        assistant.handleEvent(message);
    }

    @Override
    public synchronized void send(Object object) throws IOException
    {
        session.getBasicRemote().sendText(assistant.serialize(object));
    }

    // visible for testing
    ClientManager createClientManager()
    {
        return ClientManager.createClient();
    }

    private WebSocketContainer createWebSocketContainer(ProxySettings proxySettings)
    {
        ClientManager clientManager = createClientManager();

        if (proxySettings != null)
        {
            clientManager.getProperties().put(ClientProperties.PROXY_URI, proxySettings.toProxyUrl());
        }

        return clientManager;
    }
}
