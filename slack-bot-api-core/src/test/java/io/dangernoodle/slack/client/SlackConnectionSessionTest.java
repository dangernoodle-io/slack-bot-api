package io.dangernoodle.slack.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import io.dangernoodle.slack.SlackJsonTestFiles;
import io.dangernoodle.slack.objects.SlackIntegration;
import io.dangernoodle.slack.objects.SlackMessageable;
import io.dangernoodle.slack.objects.SlackUser;
import io.dangernoodle.slack.objects.api.SlackStartRtmResponse;


@RunWith(value = JUnitPlatform.class)
public class SlackConnectionSessionTest
{
    private SlackMessageable channel;

    private SlackIntegration integration;

    private SlackIntegration.Id integrationId;

    private SlackJsonTestFiles jsonFile;

    private SlackMessageable.Id messageableId;

    private long pingId;

    private SlackConnectionSession session;

    private SlackUser user;

    private SlackUser.Id userId;

    @BeforeEach
    public void before()
    {
        session = new SlackConnectionSession();
    }

    @Test
    public void testFindChannel()
    {
        givenAChannelId();
        whenFindChannel();
        thenChannelIsFound();
    }

    @Test
    public void testFindIntegration()
    {
        givenAnIntegrationId();
        whenFindIntegration();
        thenIntegrationIsFound();
    }

    @Test
    public void testFindUser()
    {
        givenAUserId();
        whenFindUser();
        thenUserIsFound();
    }

    @Test
    public void testSessionPopulated()
    {
        givenAnRtmResponse();
        whenUpdateSession();
        thenSelfIsNotNull();
        thenTeamIsNotNull();
        thenUsersIsNotEmpty();
        thenChannelsIsNotEmpty();
        thenIntegrationsIsNotEmpty();
    }

    @Test
    public void testUpdateLastPingId()
    {
        givenAPingId();
        whenUpdatePingId();
        thenPingIdIsUpdated();
    }

    private void givenAChannelId()
    {
        messageableId = new SlackMessageable.Id("C1V17EH34");
        givenAnRtmResponse();
        whenUpdateSession();
    }

    private void givenAnIntegrationId()
    {
        integrationId = new SlackIntegration.Id("BOT1");
        givenAnRtmResponse();
        whenUpdateSession();
    }

    private void givenAnRtmResponse()
    {
        jsonFile = SlackJsonTestFiles.rtmStartResp;
        session.updateSession(SlackJsonTestFiles.rtmStartResp.parseIntoObject(SlackStartRtmResponse.class));
    }

    private void givenAPingId()
    {
        pingId = 100;
    }

    private void givenAUserId()
    {
        userId = new SlackUser.Id("USER1");
        givenAnRtmResponse();
        whenUpdateSession();
    }

    private void thenChannelIsFound()
    {
        assertNotNull(channel);
        assertEquals(messageableId, channel.getId());
    }

    private void thenChannelsIsNotEmpty()
    {
        assertFalse(session.channels.isEmpty());
    }

    private void thenIntegrationIsFound()
    {
        assertNotNull(integration);
        assertEquals(integrationId, integration.getId());
    }

    private void thenIntegrationsIsNotEmpty()
    {
        assertFalse(session.integrations.isEmpty());
    }

    private void thenPingIdIsUpdated()
    {
        assertEquals(pingId, session.getLastPingId());
    }

    private void thenSelfIsNotNull()
    {
        assertNotNull(session.getSelf());
    }

    private void thenTeamIsNotNull()
    {
        assertNotNull(session.getTeam());
    }

    private void thenUserIsFound()
    {
        assertNotNull(user);
        assertEquals(userId, user.getId());
    }

    private void thenUsersIsNotEmpty()
    {
        assertFalse(session.users.isEmpty());
    }

    private void whenFindChannel()
    {
        channel = session.findChannel(messageableId);
    }

    private void whenFindIntegration()
    {
        integration = session.findIntegration(integrationId);
    }

    private void whenFindUser()
    {
        user = session.findUser(userId);
    }

    private void whenUpdatePingId()
    {
        session.updateLastPingId(pingId);
    }

    private void whenUpdateSession()
    {
        session.updateSession(jsonFile.parseIntoObject(SlackStartRtmResponse.class));
    }
}
