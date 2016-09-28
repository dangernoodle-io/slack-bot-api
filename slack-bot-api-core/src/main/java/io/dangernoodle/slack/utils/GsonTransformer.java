package io.dangernoodle.slack.utils;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import io.dangernoodle.slack.client.SlackJsonTransformer;
import io.dangernoodle.slack.events.SlackEventType;
import io.dangernoodle.slack.events.SlackMessageEvent;
import io.dangernoodle.slack.events.SlackMessageEventType;
import io.dangernoodle.slack.events.SlackPongEvent;
import io.dangernoodle.slack.objects.SlackIntegration;
import io.dangernoodle.slack.objects.SlackMessage;
import io.dangernoodle.slack.objects.SlackMessageable;
import io.dangernoodle.slack.objects.SlackSelf;
import io.dangernoodle.slack.objects.SlackTeam;
import io.dangernoodle.slack.objects.SlackUser;
import io.dangernoodle.slack.objects.api.SlackWebResponse;


public class GsonTransformer implements SlackJsonTransformer
{
    private static final Type PONG_MAP_TYPE_TOKEN = createPongTypeToken();

    private final Gson gson;

    private final JsonParser jsonParser;

    private final Gson prettyPrinter;

    public GsonTransformer()
    {
        GsonBuilder builder = createGsonBuilder();

        this.gson = builder.create();
        this.jsonParser = new JsonParser();
        this.prettyPrinter = builder.setPrettyPrinting().create();
    }

    @Override
    public <T> T deserialize(SlackJsonObject object, Class<T> clazz)
    {
        return gson.fromJson(((GsonJsonObject) object).jsonObject, clazz);
    }

    @Override
    public SlackJsonObject deserialize(String json)
    {
        return new GsonJsonObject(jsonParser.parse(json).getAsJsonObject());
    }

    @Override
    public <T> T deserialize(String json, Class<T> clazz)
    {
        return gson.fromJson(json, clazz);
    }

    @Override
    public String prettyPrint(String json)
    {
        return prettyPrinter.toJson(jsonParser.parse(json).getAsJsonObject());
    }

    @Override
    public String serialize(Object object)
    {
        return gson.toJson(object);
    }

    private GsonBuilder createGsonBuilder()
    {
        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                                .registerTypeAdapter(SlackEventType.class, deserializeEventType())
                                .registerTypeAdapter(SlackMessageEvent.class, deserializeMessageEvent())
                                .registerTypeAdapter(SlackIntegration.Id.class, deserializeIntegrationId())
                                .registerTypeAdapter(SlackMessageable.Id.class, deserializeMessageableId())
                                .registerTypeAdapter(SlackPongEvent.class, deserializePong())
                                .registerTypeAdapter(SlackSelf.Id.class, deserializeSelfId())
                                .registerTypeAdapter(SlackTeam.Id.class, deserializeTeamId())
                                .registerTypeAdapter(SlackUser.Id.class, deserializeUserId())
                                .registerTypeAdapter(SlackWebResponse.class, deserializeWebResponse());
    }

    private JsonDeserializer<SlackEventType> deserializeEventType()
    {
        return (json, typeOfT, context) -> SlackEventType.toEventType(json.getAsString());
    }

    private JsonDeserializer<SlackIntegration.Id> deserializeIntegrationId()
    {
        return (json, typeOfT, context) -> new SlackIntegration.Id(json.getAsString());
    }

    private JsonDeserializer<SlackMessageable.Id> deserializeMessageableId()
    {
        return (json, typeOfT, context) -> new SlackMessageable.Id(json.getAsString());
    }

    private JsonDeserializer<SlackMessageEvent> deserializeMessageEvent()
    {
        return (json, typeOfT, context) -> {
            JsonElement jsonElement = json.getAsJsonObject().get(SUBTYPE);

            SlackMessage message = context.deserialize(json, SlackMessage.class);
            SlackMessageEventType subType = (jsonElement == null) ? null : toSubtype(jsonElement.getAsString());

            return new SlackMessageEvent(message, subType);
        };
    }

    private JsonDeserializer<SlackWebResponse> deserializeWebResponse()
    {
        return (json, typeOfT, context) -> {
            Map<String, Object> response = context.deserialize(json, Map.class);
            return new SlackWebResponse(response);
        };
    }

    private JsonDeserializer<SlackPongEvent> deserializePong()
    {
        return (json, typeOfT, context) -> {
            Map<String, String> map = context.deserialize(json, PONG_MAP_TYPE_TOKEN);
            map.remove(TYPE);

            return new SlackPongEvent(toLong(map.remove(REPLY_TO)), toLong(map.remove(TIME)), map);
        };
    }

    private JsonDeserializer<SlackSelf.Id> deserializeSelfId()
    {
        return (json, typeOfT, context) -> new SlackSelf.Id(json.getAsString());
    }

    private JsonDeserializer<SlackTeam.Id> deserializeTeamId()
    {
        return (json, typeOfT, context) -> new SlackTeam.Id(json.getAsString());
    }

    private JsonDeserializer<SlackUser.Id> deserializeUserId()
    {
        return (json, typeOfT, context) -> new SlackUser.Id(json.getAsString());
    }

    private long toLong(String value)
    {
        return Long.valueOf(value);
    }

    private SlackMessageEventType toSubtype(String subtype)
    {
        return SlackMessageEventType.toEventType(subtype);
    }

    private static Type createPongTypeToken()
    {
        return new TypeToken<Map<String, String>>()
        {
            // constructor not available otherwise
        }.getType();
    }

    public static class GsonJsonObject implements SlackJsonObject
    {
        private final JsonObject jsonObject;

        public GsonJsonObject(JsonObject jsonObject)
        {
            this.jsonObject = jsonObject;
        }

        @Override
        public String getRawJson()
        {
            return jsonObject.toString();
        }

        @Override
        public String getSubType()
        {
            return get(SUBTYPE);
        }

        @Override
        public String getType()
        {
            return get(TYPE);
        }

        @Override
        public String getUser()
        {
            return get(USER);
        }

        private String get(String key)
        {
            return jsonObject.has(key) ? jsonObject.get(key).getAsString() : null;
        }
    }
}
