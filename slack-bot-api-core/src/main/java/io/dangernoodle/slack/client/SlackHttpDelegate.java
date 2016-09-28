package io.dangernoodle.slack.client;

import java.io.File;
import java.io.IOException;
import java.util.Map;


public interface SlackHttpDelegate
{
    String get(String url) throws IOException;

    String post(String url, Map<String, Object> formData) throws IOException;

    String upload(String url, File file, String filename, Map<String, Object> formData) throws IOException;

    public class SlackHttpException extends IOException
    {
        private static final long serialVersionUID = -8730904544489275037L;

        private final int code;

        public SlackHttpException(int code, String message)
        {
            super(message);
            this.code = code;
        }

        public int getStatusCode()
        {
            return code;
        }
    }
}
