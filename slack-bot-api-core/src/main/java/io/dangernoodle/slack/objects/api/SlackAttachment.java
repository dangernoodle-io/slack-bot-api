package io.dangernoodle.slack.objects.api;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * This class is used to represent a <code>message attachement</code>. Use an instance of the {@link Builder} to create
 * one.
 *
 * @since 0.1.0
 * @see <a href="https://api.slack.com/docs/message-attachments">https://api.slack.com/docs/message-attachments</a>
 */
@SuppressWarnings("unused")
public class SlackAttachment
{
    static final String SHORT = "short";
    static final String TITLE = "title";
    static final String VALUE = "value";

    private String authorIcon;

    private String authorLink;

    private String authorName;

    private String color;

    private String fallback;

    private List<Map<String, Object>> fields;

    private String footer;

    private String footerIcon;

    private String imageUrl;

    private String[] mrkdwnIn;

    private String pretext;

    private String text;

    private String thumbUrl;

    private String title;

    private String titleLink;

    private Long ts;

    public static class Builder
    {
        private SlackAttachment attachment = new SlackAttachment();

        public Builder addField(String title, String value, boolean isShort)
        {
            if (attachment.fields == null)
            {
                attachment.fields = new ArrayList<>();
            }

            attachment.fields.add(Collections.unmodifiableMap(Stream.of(
                    new SimpleEntry<>(TITLE, title),
                    new SimpleEntry<>(VALUE, value),
                    new SimpleEntry<>(SHORT, isShort)).collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue()))));

            return this;
        }

        public Builder authorIcon(String authorIcon)
        {
            attachment.authorIcon = authorIcon;
            return this;
        }

        public Builder authorLink(String authorLink)
        {
            attachment.authorLink = authorLink;
            return this;
        }

        public Builder authorName(String authorName)
        {
            attachment.authorName = authorName;
            return this;
        }

        public SlackAttachment build()
        {
            SlackAttachment tmp = attachment;
            attachment = new SlackAttachment();

            return tmp;
        }

        public Builder color(Color color)
        {
            attachment.color = color.toString();
            return this;
        }

        public Builder color(String color) throws IllegalArgumentException
        {
            if (!color.startsWith("#"))
            {
                throw new IllegalArgumentException("color must be hex code");
            }

            attachment.color = color;
            return this;
        }

        public Builder fallback(String fallback)
        {
            attachment.fallback = fallback;
            return this;
        }

        public Builder fallback(String format, Object... args)
        {
            return fallback(String.format(format, args));
        }

        public Builder footer(String footer)
        {
            attachment.footer = footer;
            return this;
        }

        public Builder footerIcon(String footerIcon)
        {
            attachment.footerIcon = footerIcon;
            return this;
        }

        public Builder imageUrl(String imageUrl)
        {
            attachment.imageUrl = imageUrl;
            return this;
        }

        public Builder markdown(Markdown... markdown)
        {
            attachment.mrkdwnIn = Stream.of(markdown)
                                        .map(Markdown::toString)
                                        .toArray(size -> new String[size]);
            return this;
        }

        public Builder pretext(String pretext)
        {
            attachment.pretext = pretext;
            return this;
        }

        public Builder pretext(String format, Object... args)
        {
            return pretext(String.format(format, args));
        }

        public Builder text(String text)
        {
            attachment.text = text;
            return this;
        }

        public Builder text(String format, Object... args)
        {
            return text(String.format(format, args));
        }

        public Builder thumbUrl(String thumbUrl)
        {
            attachment.thumbUrl = thumbUrl;
            return this;
        }

        public Builder timestamp(Long timestamp)
        {
            attachment.ts = timestamp;
            return this;
        }

        public Builder title(String title)
        {
            attachment.title = title;
            return this;
        }

        public Builder title(String format, Object... args)
        {
            return title(String.format(format, args));
        }

        public Builder titleLink(String titleLink)
        {
            attachment.titleLink = titleLink;
            return this;
        }
    }

    public enum Color
    {
        danger,
        good,
        warning;
    }

    public enum Markdown
    {
        fields,
        pretext,
        text,
    }
}
