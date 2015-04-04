package com.optimaize.webcrawlerverifier.bots;

import com.google.common.collect.ImmutableList;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class GooglebotDataTest {

    @Test
    public void testGetIdentifier() throws Exception {
        assertEquals(GooglebotData.getInstance().getIdentifier(), "GOOGLEBOT");
    }

    @Test
    public void testGetUserAgentChecker() throws Exception {
        assertTrue(GooglebotData.getInstance().getUserAgentChecker().apply("foo Googlebot bar"));
        assertFalse(GooglebotData.getInstance().getUserAgentChecker().apply("foo Google bar"));
    }

    @Test
    public void testGetIps() throws Exception {
        assertTrue(GooglebotData.getInstance().getIps().isEmpty());
    }

    @Test
    public void testGetHostnames() throws Exception {
        assertEquals(GooglebotData.getInstance().getHostnames(), ImmutableList.of("googlebot.com"));
    }
}