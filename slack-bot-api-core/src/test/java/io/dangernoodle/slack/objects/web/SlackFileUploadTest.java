package io.dangernoodle.slack.objects.web;

import java.io.File;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import io.dangernoodle.slack.SlackJsonTestFiles;
import io.dangernoodle.slack.objects.SlackMessageable;
import io.dangernoodle.slack.objects.api.SlackFileUpload.Builder;


@RunWith(value = JUnitPlatform.class)
public class SlackFileUploadTest extends AbstractSerializationTest
{
    @Override
    protected void givenAJsonTestFile()
    {
        jsonFile = SlackJsonTestFiles.fileUpload;
    }

    @Override
    protected void givenAnObjectToSerialize()
    {
        Builder builder = new Builder(new File("tmp.txt"));
        toSerialize = builder.filename("filename")
                             .filetype("txt")
                             .initialComment("comment")
                             .title("title")
                             .build("token", new SlackMessageable.Id("C1"), new SlackMessageable.Id("C2"));
    }
}
