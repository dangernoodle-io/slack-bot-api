package io.dangernoodle.slack.utils;

import java.io.IOException;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dangernoodle.slack.client.SlackHttpDelegate;
import io.dangernoodle.slack.client.SlackJsonTransformer;


public class GoogleHttpDelegate implements SlackHttpDelegate
{
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    private static final Logger logger = LoggerFactory.getLogger(GoogleHttpDelegate.class);

    private final SlackJsonTransformer jsonTransformer;
    private final HttpRequestFactory requestFactory;

    public GoogleHttpDelegate(SlackJsonTransformer jsonTransformer, ProxySettings proxySettings)
    {
        this.jsonTransformer = jsonTransformer;
        this.requestFactory = createRequestFactory(proxySettings);
    }

    @Override
    public <T> T get(String url, Class<T> clazz) throws IOException
    {
        String result = requestFactory.buildGetRequest(new GenericUrl(url)).execute().parseAsString();

        if (logger.isTraceEnabled())
        {
            logger.trace("request: {}", url);
            logger.trace("response: {}", jsonTransformer.prettyPrint(result));
        }

        return jsonTransformer.deserialize(result, clazz);
    }

    HttpRequestFactory createRequestFactory(ProxySettings proxySettings)
    {
        return HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer()
        {
            @Override
            public void initialize(HttpRequest request)
            {
                // no-op
            }
        });
    }
}
