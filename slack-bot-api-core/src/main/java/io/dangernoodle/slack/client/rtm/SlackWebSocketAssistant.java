package io.dangernoodle.slack.client.rtm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dangernoodle.slack.client.SlackClient;
import io.dangernoodle.slack.client.SlackClientSettings;
import io.dangernoodle.slack.client.SlackJsonTransformer;
import io.dangernoodle.slack.client.SlackJsonTransformer.SlackJsonObject;
import io.dangernoodle.slack.events.SlackEvent;
import io.dangernoodle.slack.events.SlackEventType;
import io.dangernoodle.slack.events.SlackHelloEvent;
import io.dangernoodle.slack.events.SlackMessageEvent;
import io.dangernoodle.slack.events.SlackMessageEventType;
import io.dangernoodle.slack.events.SlackPongEvent;
import io.dangernoodle.slack.events.channel.SlackChannelCreatedEvent;
import io.dangernoodle.slack.events.channel.SlackChannelDeletedEvent;
import io.dangernoodle.slack.events.channel.SlackChannelJoinedEvent;
import io.dangernoodle.slack.events.channel.SlackChannelLeftEvent;
import io.dangernoodle.slack.events.channel.SlackChannelRenameEvent;
import io.dangernoodle.slack.events.user.SlackPresenceChangeEvent;
import io.dangernoodle.slack.events.user.SlackUserChangeEvent;
import io.dangernoodle.slack.events.user.SlackUserTypingEvent;


public class SlackWebSocketAssistant
{
    private static final Logger logger = LoggerFactory.getLogger(SlackWebSocketAssistant.class);

    private final SlackClient client;

    private final SlackClientSettings settings;

    private final SlackJsonTransformer transformer;

    public SlackWebSocketAssistant(SlackClient client, SlackJsonTransformer transformer, SlackClientSettings settings)
    {
        this.client = client;
        this.transformer = transformer;
        this.settings = settings;
    }

    public void handleEvent(String message)
    {
        if (logger.isTraceEnabled())
        {
            logger.trace("incoming event: {}", transformer.prettyPrint(message));
        }

        SlackJsonObject object = transformer.deserialize(message);
        String type = object.getType();

        if (type != null)
        {
            // treat this as a string until we determine if it's a 'message' event
            String subType = object.getSubType();
            SlackEventType eventType = SlackEventType.toEventType(object.getType());

            dispatchEvent(eventType, subType, object);
        }
        else
        {
            // TODO: handle message acknowledgements?
        }
    }

    public String serialize(Object object)
    {
        return transformer.serialize(object);
    }

    private <E, T extends SlackEvent> void dispatch(E eventType, SlackJsonObject object, Class<T> clazz)
    {
        T event = transformer.deserialize(object, clazz);
        logger.debug("dispatching event {}", event);

        client.getObserverRegistry()
              .findObservers(event.getType())
              .forEach(observer -> observer.onEvent(event, client));
    }

    private void dispatch(SlackEventType eventType, String subType, SlackJsonObject object)
    {
        if (filterSelfUserMessages(object))
        {
            if (logger.isTraceEnabled())
            {
                logger.trace("filtered 'self' message {}", transformer.prettyPrint(object.getRawJson()));
            }
        }
        else if (dispatchAsMessageSubtype(eventType, subType))
        {
            SlackMessageEventType messageEventType = SlackMessageEventType.toEventType(subType);
            if (messageEventType == SlackMessageEventType.UNKNOWN)
            {
                logger.warn("unhandled message event occurred: {}", transformer.prettyPrint(object.getRawJson()));
            }
            else
            {
                dispatch(SlackMessageEventType.toEventType(subType), object, SlackMessageEvent.class);
            }
        }
        else
        {
            dispatch(eventType, object, SlackMessageEvent.class);
        }
    }

    private boolean dispatchAsMessageSubtype(SlackEventType eventType, String subType)
    {
        return (eventType == SlackEventType.MESSAGE && subType != null && settings.isDispatchMessageSubtypes());
    }

    private void dispatchEvent(SlackEventType eventType, String subType, SlackJsonObject object)
    {
        switch (eventType)
        {
//            case ACCOUNTS_CHANGED:
//                break;
//            case BOT_ADDED:
//                break;
//            case BOT_CHANGED:
//                break;
//            case CHANNEL_ARCHIVE:
//                break;
            case CHANNEL_CREATED:
                dispatch(eventType, object, SlackChannelCreatedEvent.class);
                break;
            case CHANNEL_DELETED:
                dispatch(eventType, object, SlackChannelDeletedEvent.class);
                break;
//            case CHANNEL_HISTORY_CHANGED:
//                break;
            case CHANNEL_JOINED:
                dispatch(eventType, object, SlackChannelJoinedEvent.class);
                break;
            case CHANNEL_LEFT:
                dispatch(eventType, object, SlackChannelLeftEvent.class);
                break;
//            case CHANNEL_MARKED:
//                break;
            case CHANNEL_RENAME:
                dispatch(eventType, object, SlackChannelRenameEvent.class);
                break;
//            case CHANNEL_UNARCHIVE:
//                break;
//            case COMMANDS_CHANGED:
//                break;
//            case DND_UPDATED:
//                break;
//            case DND_UPDATED_USER:
//                break;
//            case EMAIL_DOMAIN_CHANGED:
//                break;
//            case EMOJI_CHANGED:
//                break;
//            case FILE_CHANGE:
//                break;
//            case FILE_COMMENT_ADDED:
//                break;
//            case FILE_COMMENT_DELETED:
//                break;
//            case FILE_COMMENT_EDITED:
//                break;
//            case FILE_CREATED:
//                break;
//            case FILE_DELETED:
//                break;
//            case FILE_PUBLIC:
//                break;
//            case FILE_SHARED:
//                break;
//            case FILE_UNSHARED:
//                break;
//            case GROUP_ARCHIVE:
//                break;
//            case GROUP_CLOSE:
//                break;
//            case GROUP_HISTORY_CHANGED:
//                break;
            case GROUP_JOINED:
                dispatch(eventType, object, SlackChannelJoinedEvent.class);
                break;
            case GROUP_LEFT:
                dispatch(eventType, object, SlackChannelLeftEvent.class);
                break;
//            case GROUP_MARKED:
//                break;
//            case GROUP_OPEN:
//                break;
            case GROUP_RENAME:
                dispatch(eventType, object, SlackChannelRenameEvent.class);
                break;
//            case GROUP_UNARCHIVE:
//                break;
            case HELLO:
                dispatch(eventType, object, SlackHelloEvent.class);
                break;
//            case IM_CLOSE:
//                break;
//            case IM_CREATED:
//                break;
//            case IM_HISTORY_CHANGED:
//                break;
//            case IM_MARKED:
//                break;
//            case IM_OPEN:
//                break;
//            case MANUAL_PRESENCE_CHANGE:
//                break;
            case MESSAGE:
                dispatch(eventType, subType, object);
                break;
//            case PIN_ADDED:
//                break;
//            case PIN_REMOVED:
//                break;
//            case PREF_CHANGE:
//                break;
            case PRESENCE_CHANGE:
                dispatch(eventType, object, SlackPresenceChangeEvent.class);
                break;
            case PONG:
                dispatch(eventType, object, SlackPongEvent.class);
                break;
//            case REACTION_ADDED:
//                break;
//            case REACTION_REMOVED:
//                break;
            case RECONNECT_URL:
                // unsupported, so just ignore outright for now
                break;
//            case STAR_ADDED:
//                break;
//            case STAR_REMOVED:
//                break;
//            case SUBTEAM_CREATED:
//                break;
//            case SUBTEAM_SELF_ADDED:
//                break;
//            case SUBTEAM_SELF_REMOVED:
//                break;
//            case SUBTEAM_UPDATED:
//                break;
//            case TEAM_DOMAIN_CHANGE:
//                break;
//            case TEAM_JOIN:
//                break;
//            case TEAM_MIGRATION_STARTED:
//                break;
//            case TEAM_PLAN_CHANGE:
//                break;
//            case TEAM_PREF_CHANGE:
//                break;
//            case TEAM_PROFILE_CHANGE:
//                break;
//            case TEAM_PROFILE_DELETE:
//                break;
//            case TEAM_PROFILE_REORDER:
//                break;
//            case TEAM_RENAME:
//                break;
            case USER_CHANGE:
                dispatch(eventType, object, SlackUserChangeEvent.class);
                break;
            case USER_TYPING:
                dispatch(eventType, object, SlackUserTypingEvent.class);
                break;
            case UNKNOWN:
            default:
                logUnknownEvent(object);
                break;
        }
    }

    private void logUnknownEvent(SlackJsonObject object)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("unhandled event occurred: {}", transformer.prettyPrint(object.getRawJson()));
        }
    }

    private boolean filterSelfUserMessages(SlackJsonObject object)
    {
        return settings.isFilterSelfMessages() && client.getSession().getSelf().getId().value().equals(object.getUser());
    }
}
