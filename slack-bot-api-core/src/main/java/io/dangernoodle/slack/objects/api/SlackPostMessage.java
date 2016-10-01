package io.dangernoodle.slack.objects.api;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.dangernoodle.slack.objects.SlackMessageable;


@SuppressWarnings("unused")
public class SlackPostMessage
{
    private Boolean asUser = true;

    private List<SlackAttachment> attachments;

    private String channel;

    private String iconEmoji;

    private String iconUrl;

    private Integer linkNames;

    private String parse;

    private String text;

    private String token;

    private Boolean unfurlLinks;

    private Boolean unfurlMedia;

    private String username;

    public static class Builder
    {
        private SlackPostMessage message = new SlackPostMessage();

        public Builder(SlackAttachment.Builder builder)
        {
            attachment(builder);
        }

        public Builder(String text)
        {
            text(text);
        }

        public Builder asUser(boolean asUser)
        {
            message.asUser = asUser;
            return this;
        }

        public Builder attachment(SlackAttachment.Builder builder)
        {
            if (message.attachments == null)
            {
                message.attachments = new ArrayList<>();
            }

            message.attachments.add(builder.build());
            return this;
        }

        public SlackPostMessage build(String authToken, SlackMessageable.Id id)
        {
            message.token = authToken;
            message.channel = id.value();

            return message;
        }

        public Builder iconEmoji(String iconEmoji)
        {
            message.iconEmoji = iconEmoji;
            return this;
        }

        public Builder iconUrl(String iconUrl)
        {
            message.iconUrl = iconUrl;
            return this;
        }

        public Builder linkify(boolean linkify)
        {
            message.linkNames = linkify ? 1 : 0;
            return this;
        }

        public Builder parse(Parse parse)
        {
            message.parse = (parse == Parse.dftl) ? null : parse.toString();
            return this;
        }

        public Builder text(String text)
        {
            message.text = text;
            return this;
        }

        public Builder unfurlLinks(boolean unfurlLinks)
        {
            message.unfurlLinks = unfurlLinks;
            return this;
        }

        public Builder unfurlMedia(boolean unfurlMedia)
        {
            message.unfurlMedia = unfurlMedia;
            return this;
        }

        public Builder username(String username)
        {
            message.username = username;
            message.asUser = false;

            return this;
        }
    }

    public enum Parse
    {
        dftl,
        full,
        none;
    }
}
