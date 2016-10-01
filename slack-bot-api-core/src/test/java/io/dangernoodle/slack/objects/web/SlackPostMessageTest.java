package io.dangernoodle.slack.objects.web;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import io.dangernoodle.slack.SlackJsonTestFiles;
import io.dangernoodle.slack.objects.SlackMessageable;
import io.dangernoodle.slack.objects.api.SlackAttachment;
import io.dangernoodle.slack.objects.api.SlackPostMessage.Builder;
import io.dangernoodle.slack.objects.api.SlackPostMessage.Parse;


@RunWith(value = JUnitPlatform.class)
public class SlackPostMessageTest extends AbstractSerializationTest
{
    @Override
    protected void givenAJsonTestFile()
    {
        jsonFile = SlackJsonTestFiles.postMessage;
    }

    @Override
    protected void givenAnObjectToSerialize()
    {
        Builder builder = new Builder("text");
        toSerialize = builder.asUser(true)
                             .attachment(new SlackAttachment.Builder().text("text"))
                             .iconEmoji("emoji")
                             .iconUrl("http://example.com/path/to/icon.png")
                             .linkify(true)
                             .parse(Parse.full)
                             .text("text")
                             .unfurlLinks(true)
                             .unfurlMedia(true)
                             .username("username")
                             .build("token", new SlackMessageable.Id("C1"));
    }
}
