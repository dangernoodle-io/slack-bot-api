package io.dangernoodle.slack.client.rtm;

import java.io.IOException;


public interface SlackWebSocketClient
{
    void connect(String url) throws IOException;

    void disconnect() throws IOException;

    boolean isConnected();

    boolean isSecure();

    void send(Object object) throws IOException;
}
