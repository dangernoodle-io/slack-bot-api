package io.dangernoodle.slack.client.web;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.dangernoodle.slack.client.SlackClientSettings;
import io.dangernoodle.slack.client.SlackHttpDelegate;
import io.dangernoodle.slack.client.SlackJsonTransformer;
import io.dangernoodle.slack.objects.SlackMessageable;
import io.dangernoodle.slack.objects.api.SlackFileUpload;
import io.dangernoodle.slack.objects.api.SlackStartRtmResponse;
import io.dangernoodle.slack.objects.api.SlackWebResponse;


@RunWith(value = JUnitPlatform.class)
public class SlackWebClientTest
{
    private static final SlackMessageable.Id ID = new SlackMessageable.Id("1");

    private SlackWebClient client;

    @Mock
    private SlackHttpDelegate mockHttpDelegate;

    @Mock
    private SlackStartRtmResponse mockRtmResponse;

    @Mock
    private SlackClientSettings mockSettings;

    @Mock
    private SlackJsonTransformer mockTransformer;

    @Mock
    private SlackFileUpload mockUpload;

    @Mock
    private SlackFileUpload.Builder mockUploadBuilder;

    @Mock
    private SlackWebResponse mockWebResponse;

    private SlackStartRtmResponse rtmResponse;

    private SlackWebResponse webResponse;

    @BeforeEach
    public void before()
    {
        MockitoAnnotations.initMocks(this);

        client = new SlackWebClient(mockSettings, mockHttpDelegate, mockTransformer);
    }

    @Test
    public void testInitiateRtmConnection() throws Exception
    {
        givenAnRtmInitiationRequest();
        whenInitiateConnection();
        thenConnectionInitiated();
    }

    @Test
    public void testUpload() throws Exception
    {
        givenAnUploadBuilder();
        givenASerializedWebResponse();
        whenUploadFile();
        thenFileWasUploaded();
        thenResponseWasReturned();
    }

    private void givenAnRtmInitiationRequest() throws IOException
    {
       when(mockHttpDelegate.get(any())).thenReturn("");
       when(mockTransformer.deserialize(anyString(), any())).thenReturn(mockRtmResponse);
    }

    private void givenAnUploadBuilder()
    {
        when(mockUploadBuilder.build(any(), any())).thenReturn(mockUpload);
    }

    private void givenASerializedWebResponse() throws IOException
    {
        when(mockHttpDelegate.upload(any(), any(), any(), any())).thenReturn("");
        when(mockTransformer.deserialize(anyString(), any())).thenReturn(mockWebResponse);
    }

    private void thenConnectionInitiated() throws IOException
    {
        assertNotNull(rtmResponse);
        verify(mockHttpDelegate).get(any());
    }

    private void thenFileWasUploaded() throws IOException
    {
        verify(mockHttpDelegate).upload(any(), any(), any(), any());
    }

    private void thenResponseWasReturned()
    {
        assertNotNull(webResponse);
        verify(mockTransformer).deserialize(anyString(), any());
    }

    private void whenInitiateConnection() throws IOException
    {
        rtmResponse = client.initiateRtmConnection();
    }

    private void whenUploadFile() throws IOException
    {
        webResponse = client.upload(mockUploadBuilder, ID);
    }
}
