package io.dangernoodle.slack.objects.web;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import io.dangernoodle.slack.SlackJsonTestFiles;
import io.dangernoodle.slack.objects.api.SlackAttachment.Builder;
import io.dangernoodle.slack.objects.api.SlackAttachment.Markdown;


@RunWith(value = JUnitPlatform.class)
public class SlackAttachmentTest extends AbstractSerializationTest
{
    @Override
    protected void givenAJsonTestFile()
    {
        jsonFile = SlackJsonTestFiles.attachment;
    }

    @Override
    protected void givenAnObjectToSerialize()
    {
        Builder builder = new Builder();
        toSerialize = builder.addField("Priority", "High", false)
                             .authorIcon("http://flickr.com/icons/bobby.jpg")
                             .authorLink("http://flickr.com/bobby/")
                             .authorName("Bobby Tables")
                             .color("#36a64f")
                             .fallback("Required plain-text summary of the attachment.")
                             .footer("Slack API")
                             .footerIcon("https://platform.slack-edge.com/img/default_application_icon.png")
                             .imageUrl("http://my-website.com/path/to/image.jpg")
                             .markdown(Markdown.pretext, Markdown.text, Markdown.fields)
                             .pretext("Optional text that appears above the attachment block")
                             .text("Optional text that appears within the attachment")
                             .thumbUrl("http://example.com/path/to/thumb.png")
                             .timestamp(123456789L)
                             .title("Slack API Documentation")
                             .titleLink("https://api.slack.com/")
                             .build();
    }
}
