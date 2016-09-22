package io.dangernoodle.slack.client;

import java.io.IOException;


public interface SlackHttpDelegate
{
    <T> T get(String url, Class<T> clazz) throws IOException;
}
