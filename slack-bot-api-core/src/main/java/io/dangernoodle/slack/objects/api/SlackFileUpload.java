package io.dangernoodle.slack.objects.api;

import java.beans.Transient;
import java.io.File;
import java.util.stream.Stream;

import io.dangernoodle.slack.objects.SlackMessageable;
import io.dangernoodle.slack.objects.api.SlackAttachment.Builder;


/**
 * This class is used to represent a <code>file upload</code>. Use an instance of the {@link Builder} to create
 * one.
 *
 * @since 0.1.0
 * @see <a href="https://api.slack.com/methods/files.upload">https://api.slack.com/methods/files.upload</a>
 */
@SuppressWarnings("unused")
public class SlackFileUpload
{
    private String channels;

    private transient File file;

    private transient String filename;

    private String filetype;

    private String initialComment;

    private String title;

    private String token;

    public File getFile()
    {
        return file;
    }

    public String getFilename()
    {
        return (filename == null) ? file.getName() : filename;
    }

    public static class Builder
    {
        private SlackFileUpload upload = new SlackFileUpload();

        public Builder(File file)
        {
            upload.file = file;
        }

        public SlackFileUpload build(String authToken, SlackMessageable.Id... ids)
        {
            SlackFileUpload tmp = upload;
            upload = new SlackFileUpload();

            tmp.token = authToken;
            tmp.channels = String.join(",", Stream.of(ids).map(SlackMessageable.Id::value).toArray(size -> new String[size]));

            return tmp;
        }

        public Builder filename(String filename)
        {
            upload.filename = filename;
            return this;
        }

        public Builder filetype(String filetype)
        {
            upload.filetype = filetype;
            return this;
        }

        public Builder initialComment(String initialComment)
        {
            upload.initialComment = initialComment;
            return this;
        }

        public Builder title(String title)
        {
            upload.title = title;
            return this;
        }
    }
}
