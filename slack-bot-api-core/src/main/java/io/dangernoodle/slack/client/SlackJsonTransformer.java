package io.dangernoodle.slack.client;

public interface SlackJsonTransformer
{
    static final String ID = "id";

    static final String SUBTYPE = "subtype";

    static final String OK = "ok";

    static final String PING = "ping";

    static final String REPLY_TO = "reply_to";

    static final String TIME = "time";

    static final String TYPE = "type";

    static final String USER = "user";

    <T> T deserialize(SlackJsonObject object, Class<T> clazz);

    SlackJsonObject deserialize(String json);

    <T> T deserialize(String json, Class<T> clazz);

    String prettyPrint(String json);

    String serialize(Object object);

    public static interface SlackJsonObject
    {
        String getRawJson();

        String getSubType();

        String getType();

        String getUser();

        boolean isReplyTo();
    }
}
