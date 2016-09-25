package io.dangernoodle.slack;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import io.dangernoodle.slack.utils.GsonTransformer;


public enum SlackJsonTestFiles
{
    channel,
    channelCreated,
    channelDeleted,
    channelJoined,
    channelLeft,
    channelRename,
    group,
    groupJoined,
    groupLeft,
    groupRename,
    hello,
    im,
    message,
    messageChanged,
    messageDeleted,
    pong,
    pongArgs,
    rtmStartResp,
    userChange,
    userTyping;

    private static final List<String> dirs;

    private static final GsonTransformer transformer;

    static
    {
        dirs = Arrays.asList("/", "/events", "/messages", "/objects");
        transformer = new GsonTransformer();
    }

    private final String jsonFile;

    private SlackJsonTestFiles()
    {
        this.jsonFile = this.toString();
    }

    public String loadJson()
    {
        try (Scanner scanner = new Scanner(getInputStream(), "UTF-8"))
        {
            return scanner.useDelimiter("\\Z").next();
        }
    }

    public <T> T parseIntoObject(Class<T> clazz)
    {
        return transformer.deserialize(this.loadJson(), clazz);
    }

    public String toLower()
    {
        return this.name().toLowerCase();
    }

    private InputStream getInputStream()
    {
        return dirs.stream()
                   .map(dir -> String.format("/json%s/%s.json", dir, jsonFile))
                   .map(file -> getClass().getResourceAsStream(file))
                   .filter(stream -> stream != null)
                   .findFirst()
                   .orElseThrow(() -> new RuntimeException("failed to find json file for " + this));
    }
}
