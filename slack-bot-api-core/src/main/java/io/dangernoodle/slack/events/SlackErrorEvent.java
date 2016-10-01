package io.dangernoodle.slack.events;

import io.dangernoodle.slack.objects.SlackError;


/**
 * Represents an <code>error</code> event
 *
 * @since 0.1.0
 */
public class SlackErrorEvent extends SlackEvent
{
    private SlackError error;

    public SlackError getError()
    {
        return error;
    }
}
