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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dangernoodle.slack.client.rtm.SlackRtmApiClient;
import io.dangernoodle.slack.client.rtm.SlackRtmApiAssistant;
import io.dangernoodle.slack.utils.ProxySettings;


public class TyrusSlackRtmApiClient implements SlackRtmApiClient, MessageHandler.Whole<String>
{
    private static final Logger logger = LoggerFactory.getLogger(TyrusSlackRtmApiClient.class);

    private Session session;

    private final SlackRtmApiAssistant assistant;

    private final WebSocketContainer container;

    public TyrusSlackRtmApiClient(SlackRtmApiAssistant assistant, ProxySettings proxySettings)
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
                public void onOpen(Session session, EndpointConfig config)
                {
                    session.addMessageHandler(TyrusSlackRtmApiClient.this);
                }

                @Override
                public void onError(Session session, Throwable thr)
                {
                    logger.error("tyrus 'onError', message processing failed", thr);
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
    public synchronized void send(Object object) throws IOException
    {
        session.getBasicRemote().sendText(assistant.serialize(object));
    }

    @Override
    public void onMessage(String message)
    {
        assistant.handleEvent(message);
    }

    private WebSocketContainer createWebSocketContainer(ProxySettings proxySettings)
    {
        ClientManager clientManager = ClientManager.createClient();

        if (proxySettings != null)
        {
            // TODO: add support for proxies
        }

        return clientManager;
    }
}
