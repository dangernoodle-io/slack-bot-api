package io.dangernoodle.slack.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;


@RunWith(value = JUnitPlatform.class)
public class ProxySettingTest
{
    private String expectedUrl;

    private ProxySettings proxySettings;

    @Test
    public void testBuildHttpNoPort()
    {
        givenAnHttpProxyNoPort();
        thenProxyUrlIsCorrect();
    }

    @Test
    public void testBuildHttpPort()
    {
        givenAnHttpProxy();
        thenProxyUrlIsCorrect();
    }

    @Test
    public void testBuildHttpsNoPort()
    {
        givenAnHttpsProxyNoPort();
        thenProxyUrlIsCorrect();
    }

    @Test
    public void testBuildHttpsPort()
    {
        givenAnHttpsProxy();
        thenProxyUrlIsCorrect();
    }

    private void givenAnHttpProxy()
    {
        expectedUrl = "http://example.com:8080";
        proxySettings = new ProxySettings.Builder("example.com").port(8080).build();
    }

    private void givenAnHttpProxyNoPort()
    {
        expectedUrl = "http://example.com";
        proxySettings = new ProxySettings.Builder("example.com").build();
    }

    private void givenAnHttpsProxy()
    {
        expectedUrl = "https://example.com:8080";
        proxySettings = new ProxySettings.Builder("example.com").port(8080).https(true).build();
    }

    private void givenAnHttpsProxyNoPort()
    {
        expectedUrl = "https://example.com";
        proxySettings = new ProxySettings.Builder("example.com").https(true).build();
    }

    private void thenProxyUrlIsCorrect()
    {
        assertEquals(expectedUrl, proxySettings.toProxyUrl());
    }
}
