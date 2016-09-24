package io.dangernoodle.slack.utils;

/**
 * Simple value class used to represent proxy settings
 *
 * @since 0.1.0
 */
public class ProxySettings
{
    private String host;

    private boolean https;

    private String password;

    private int port;

    private String username;

    private ProxySettings()
    {
        // builder
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

    public boolean isHttps()
    {
        return https;
    }

    public String toProxyUrl()
    {
        StringBuilder builder = new StringBuilder("http");

        if (https)
        {
            builder.append("s");
        }

        builder.append("://")
               .append(host);

        if (port != 0)
        {
            builder.append(":").append(port);
        }

        return builder.toString();
    }

    public static class Builder
    {
        private final ProxySettings settings = new ProxySettings();

        public Builder(String host)
        {
            settings.host = host;
        }

        public ProxySettings build()
        {
            return settings;
        }

        public Builder https(boolean https)
        {
            settings.https = https;
            return this;
        }

        public Builder password(String password)
        {
            settings.password = password;
            return this;
        }

        public Builder port(int port)
        {
            settings.port = port;
            return this;
        }

        public Builder username(String username)
        {
            settings.username = username;
            return this;
        }
    }
}
