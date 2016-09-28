package io.dangernoodle.slack.client;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(value = JUnitPlatform.class)
public class SlackClientBuilderTest
{
    @Mock
    private SlackClientSettings mockSettings;

    @Mock
    private SlackProviderFactory mockFactory;

    private SlackClientBuilder builder;

    private SlackClient client;

    @BeforeEach
    public void before()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateClientLoadProvider()
    {
        givenAClientBuilder();
        whenCreateClient();
        thenClientWasCreated();
        thenLoadedFactoryWasUsed();
    }

    @Test
    public void testCreateClientWithProvider()
    {
        givenAClientBuilderWithProvider();
        whenCreateClient();
        thenClientWasCreated();
        thenPassedFactoryWasUsed();
    }

    private void thenPassedFactoryWasUsed()
    {
        verify(mockFactory).createHttpDelegate();
        verify(mockFactory).createJsonTransformer();
        verify(mockFactory).createClient(any());
    }

    private void thenLoadedFactoryWasUsed()
    {
        assertNotNull(SlackTestProviderFactory.httpDelegate);
        assertNotNull(SlackTestProviderFactory.jsonTransformer);
        assertNotNull(SlackTestProviderFactory.websocketClient);
    }

    private void thenClientWasCreated()
    {
        assertNotNull(client);
    }

    private void whenCreateClient()
    {
        client = builder.build();
    }

    private void givenAClientBuilderWithProvider()
    {
        builder = new SlackClientBuilder(mockSettings).with(mockFactory);
    }

    private void givenAClientBuilder()
    {
        builder = new SlackClientBuilder(mockSettings);
    }
}
