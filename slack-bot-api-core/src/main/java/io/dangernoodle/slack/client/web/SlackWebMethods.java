package io.dangernoodle.slack.client.web;

enum SlackWebMethods
{
    chatPostMessage("chat.postMessage"),
    filesUpload("files.upload"),
    rtmStart("rtm.start");

    private final String command;

    private SlackWebMethods(String command)
    {
        this.command = command;
    }

    public String toUrl()
    {
        return "https://slack.com/api/" + command;
    }
}
