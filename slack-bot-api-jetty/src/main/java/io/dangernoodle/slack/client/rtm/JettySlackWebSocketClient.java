package io.dangernoodle.slack.client.rtm;

import java.io.IOException;
import java.net.URI;

import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * WebSocket client implementation using
 * <a href="https://www.eclipse.org/jetty/documentation/9.3.x/jetty-websocket-client-api.html">Jetty</a>
 *
 * @since 0.1.0
 */
public class JettySlackWebSocketClient implements SlackWebSocketClient, WebSocketListener
{
    private static final Logger logger = LoggerFactory.getLogger(JettySlackWebSocketClient.class);

    private final SlackWebSocketAssistant assistant;
    private Session session;

    private final WebSocketClient socketClient;

    public JettySlackWebSocketClient(SlackWebSocketAssistant assistant)
    {
        this.assistant = assistant;
        this.socketClient = createWebSocketClient();
    }

    WebSocketClient createWebSocketClient()
    {
        return new WebSocketClient(new SslContextFactory());
    }

    @Override
    public synchronized void connect(String url) throws IOException
    {
        try
        {
            startClient();
            session = socketClient.connect(this, URI.create(url)).get();
        }
        // oh jetty, how i love and hate thee...
        catch (Exception e)
        {
            if (e instanceof InterruptedException)
            {
                Thread.currentThread().interrupt();
            }

            logger.error("failed to connect to [{}]", url, e);
            throw new IOException(e);
        }
    }

    // visible for testing
    void startClient() throws Exception
    {
        if (!socketClient.isRunning())
        {
            // this is a final method and can't be mocked...
            socketClient.start();
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
        return (session != null && session.isOpen());
    }

    @Override
    public synchronized boolean isSecure()
    {
        return (session != null && session.isSecure());
    }

    @Override
    public void onWebSocketBinary(byte[] payload, int offset, int len)
    {
        // no-op
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason)
    {
        // no-op
    }

    @Override
    public void onWebSocketConnect(Session session)
    {
        // no-op
    }

    @Override
    public void onWebSocketError(Throwable cause)
    {
        logger.error("jetty 'onError', message processing failed", cause);
    }

    @Override
    public void onWebSocketText(String message)
    {
        assistant.handleEvent(message);
    }

    @Override
    public synchronized void send(Object object) throws IOException
    {
        session.getRemote().sendString(assistant.serialize(object));
    }

    @Override
    protected void finalize() throws Throwable
    {
        super.finalize();
        socketClient.stop();
    }
}
