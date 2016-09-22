package io.dangernoodle.slack.client.rtm;

import io.dangernoodle.slack.client.SlackClient;
import io.dangernoodle.slack.events.SlackEvent;

@FunctionalInterface
public interface SlackEventObserver<T extends SlackEvent>
{
    public void onEvent(T event, SlackClient session);
}
