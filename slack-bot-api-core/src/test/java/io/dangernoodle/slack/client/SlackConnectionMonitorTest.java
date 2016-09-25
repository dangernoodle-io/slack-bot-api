package io.dangernoodle.slack.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.ConnectException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


@RunWith(value = JUnitPlatform.class)
public class SlackConnectionMonitorTest
{
    @Mock
    private SlackClient mockClient;

    @Mock
    private ScheduledExecutorService mockExecutorService;

    @Mock
    @SuppressWarnings("rawtypes")
    private ScheduledFuture mockFuture;

    @Mock
    private SlackConnectionSession mockSession;

    private SlackConnectionMonitor monitor;

    @BeforeEach
    public void before()
    {
        MockitoAnnotations.initMocks(this);

        setupExecutorService();
        when(mockClient.getSession()).thenReturn(mockSession);
    }

    @Test
    public void testExecutorShutdown() throws Throwable
    {
        givenAMonitor();
        whenFinalize();
        thenExecutorIsShutdown();
    }

    @Test
    public void testPingWillBeSent()
    {
        givenAReconnectingMonitor();
        givenAConnectedClient();
        givenLastPingIdsMatch();
        whenRunHeartbeat();
        thenAnotherPingIsSent();
    }

    @Test
    public void testRunIOExceptionThrown() throws Exception
    {
        givenAMonitor();
        givenAnIOExceptionWillOccur();
        whenRunHeartbeat();
        thenPingIdIsNotUpdated();
    }

    @Test
    public void testRunWithNoReconnect() throws Exception
    {
        givenAMonitor();
        givenAConnectedClient();
        givenLastPingIdsDoNotMatch();
        whenRunHeartbeat();
        thenReconnectIsNotCalled();
    }

    @Test
    public void testRunWithReconnectNotConnected() throws Exception
    {
        givenAReconnectingMonitor();
        whenRunHeartbeat();
        thenReconnectIsCalled();
        thenPingIdWasNotChecked();
    }

    @Test
    public void testRunWithReconnectPingIdsDoNotMatch() throws Exception
    {
        givenAReconnectingMonitor();
        givenAConnectedClient();
        givenLastPingIdsDoNotMatch();
        whenRunHeartbeat();
        thenReconnectIsCalled();
    }

    @Test
    public void testStart()
    {
        givenAMonitor();
        whenStart();
        thenStartIsCaled();
    }

    @Test
    public void testStop()
    {
        givenAMonitor();
        whenStart();
        whenStop();
        thenStopIsCalled();

        whenStop();
        thenStopIsNotCalled();
    }

    private SlackConnectionMonitor createSlackConnectionMonitor(boolean reconnect)
    {
        return new SlackConnectionMonitor(mockClient, 1, reconnect)
        {
            @Override
            ScheduledExecutorService createExecutorService()
            {
                return mockExecutorService;
            }
        };
    }

    private void givenAConnectedClient()
    {
        when(mockClient.isConnected()).thenReturn(true);
    }

    private void givenAMonitor()
    {
        monitor = createSlackConnectionMonitor(false);
    }

    private void givenAnIOExceptionWillOccur() throws ConnectException, IOException
    {
        doThrow(IOException.class).when(mockClient).reconnect();
    }

    private void givenAReconnectingMonitor()
    {
        monitor = createSlackConnectionMonitor(true);
    }

    private void givenLastPingIdsDoNotMatch()
    {
        when(mockSession.getLastPingId()).thenReturn(1L);
        when(mockClient.sendPing()).thenReturn(2L);
    }

    private void givenLastPingIdsMatch()
    {
        when(mockSession.getLastPingId()).thenReturn(0L);
        when(mockClient.sendPing()).thenReturn(2L);
    }

    @SuppressWarnings("unchecked")
    private void setupExecutorService()
    {
        when(mockExecutorService.scheduleAtFixedRate(any(), anyLong(), anyLong(), any())).thenReturn(mockFuture);
    }

    private void thenAnotherPingIsSent()
    {
        verify(mockClient).sendPing();
        assertEquals(2L, monitor.getLastPingId());
    }

    private void thenExecutorIsShutdown()
    {
        verify(mockExecutorService).shutdownNow();
    }

    private void thenPingIdIsNotUpdated()
    {
        assertEquals(0L, monitor.getLastPingId());
    }

    private void thenPingIdWasNotChecked()
    {
        verify(mockSession, never()).getLastPingId();
    }

    private void thenReconnectIsCalled() throws ConnectException, IOException
    {
        verify(mockClient).reconnect();
    }

    private void thenReconnectIsNotCalled() throws ConnectException, IOException
    {
        verify(mockClient, never()).reconnect();
    }

    private void thenStartIsCaled()
    {
        verify(mockExecutorService).scheduleAtFixedRate(any(), anyLong(), anyLong(), any());
    }

    private void thenStopIsCalled()
    {
        assertNotNull(mockFuture);
        verify(mockFuture).cancel(true);
    }

    private void thenStopIsNotCalled()
    {
        verifyZeroInteractions(mockFuture);
    }

    private void whenFinalize() throws Throwable
    {
        monitor.finalize();
    }

    private void whenRunHeartbeat()
    {
        monitor.run();
    }

    private void whenStart()
    {
        monitor.start();
    }

    private void whenStop()
    {
        monitor.stop();
    }
}
