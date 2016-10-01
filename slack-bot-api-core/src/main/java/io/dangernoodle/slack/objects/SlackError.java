package io.dangernoodle.slack.objects;

/**
 * This class is used to represent an <code>RTM</code> error.
 *
 * @since 0.1.0
 */
public class SlackError
{
    private int code;

    private String msg;

    public int getCode()
    {
        return code;
    }

    public String getMessage()
    {
        return msg;
    }
}
