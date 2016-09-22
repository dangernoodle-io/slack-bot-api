package io.dangernoodle.slack.utils;

/**
 * Simple value class used to represent proxy settings
 *
 * @since 0.1.0
 */
public class ProxySettings
{
    // turn this back into a builder to accept 'http/https/', etc etc

    private final String host;

    private final String password;

    private final int port;

    private ObjectSidekick<ProxySettings> sidekick;

    private final String username;

    public ProxySettings(String host, int port)
    {
        this(host, port, "", "");
    }

    public ProxySettings(String host, int port, String username, String password)
    {
        this.host = host;
        this.port = port;

        this.username = username;
        this.password = password;

        this.sidekick = createSidekick();
    }

    @Override
    public boolean equals(Object obj)
    {
        return sidekick.equals(obj);
    }

    public String getHost()
    {
        return host;
    }

    public String getPassword()
    {
        return password;
    }

    public int getPort()
    {
        return port;
    }

    public String getUsername()
    {
        return username;
    }

    @Override
    public int hashCode()
    {
        return sidekick.hashCode();
    }

    @Override
    public String toString()
    {
        return sidekick.toString();
    }

    private ObjectSidekick<ProxySettings> createSidekick()
    {
        return new ObjectSidekick<>(this).with("host", ProxySettings::getHost)
                                         .with("port", ProxySettings::getPort)
                                         .with("username", ProxySettings::getUsername)
                                         .with("password", ProxySettings::getPassword);
    }
}
