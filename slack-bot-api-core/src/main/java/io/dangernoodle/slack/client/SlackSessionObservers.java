package io.dangernoodle.slack.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dangernoodle.slack.client.rtm.SlackEventObserver;
import io.dangernoodle.slack.events.SlackHelloEvent;
import io.dangernoodle.slack.events.SlackPongEvent;
import io.dangernoodle.slack.events.channel.SlackChannelCreatedEvent;
import io.dangernoodle.slack.events.channel.SlackChannelJoinedEvent;
import io.dangernoodle.slack.events.channel.SlackChannelLeftEvent;
import io.dangernoodle.slack.events.user.SlackUserChangeEvent;
import io.dangernoodle.slack.objects.SlackChannel;
import io.dangernoodle.slack.objects.SlackMessageable;
import io.dangernoodle.slack.objects.SlackUser;


/**
 * Observer implementations added by the client
 *
 * @since 0.1.0
 */
class SlackSessionObservers
{
    private static final Logger logger = LoggerFactory.getLogger(SlackSessionObservers.class);

    /** updates the session with newly created channels */
    static final SlackEventObserver<SlackChannelCreatedEvent> channelCreatedObserver = (event, client) -> {
        SlackChannel channel = event.getChannel();
        logger.debug("channel created event received, adding new channel [{}]", channel.getName());

        client.getSession().updateChannels(channel);
    };

    /** updates the session when a group (private channel) is joined */
    static final SlackEventObserver<SlackChannelJoinedEvent> groupJoinedObserver = (event, client) -> {
        SlackChannel channel = event.getChannel();
        logger.debug("group joined event received, adding private channel [{}]", channel.getName());

        client.getSession().updateChannels(channel);
    };

    /** updates the session when a group (private channel) is left */
    static final SlackEventObserver<SlackChannelLeftEvent> groupLeftObserver = (event, client) -> {
        SlackMessageable.Id channel = event.getChannel();
        logger.debug("group left event received, removing private channel [{}]", channel.value());

        client.getSession().removeChannel(channel);
    };

    /** hello event observer to log session establishment */
    static final SlackEventObserver<SlackHelloEvent> helloObserver = (event, client) -> {
        SlackConnectionSession session = client.getSession();

        logger.info("slack session established!");
        logger.info("team: {} ({})", session.getTeam().getName(), session.getTeam().getId().value());
        logger.info("self: {} ({})", session.getSelf().getName(), session.getSelf().getId().value());
    };

    /** pong event observer for the heartbeat thread */
    static final SlackEventObserver<SlackPongEvent> pongObserver = (event, client) -> {
        client.getSession().updateLastPingId(event.getId());
        logger.debug("pong event received in [{}]ms", System.currentTimeMillis() - event.getTime());
    };

    /** updates the sesion when a user has changed */
    static final SlackEventObserver<SlackUserChangeEvent> userChangedObserver = (event, client) -> {
        SlackUser user = event.getUser();
        logger.debug("user changed event received, updating user [{}]", user.getName());

        client.getSession().updateUsers(user);
    };

    // TODO: add unknown event listeners that log the events, add option to disable them

    private SlackSessionObservers()
    {
        // private
    }
}
