package com.ianhattendorf.geth.gethstatus.service;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LinuxShellGethUptimeServiceTest {

    private ShellService shellService;
    private GethUptimeService gethUptimeService;

    @Before
    public void setUp() {
        shellService = mock(ShellService.class);
        Clock clock = Clock.fixed(Instant.parse("2007-12-03T10:15:30.12Z"), ZoneId.systemDefault());
        gethUptimeService = new LinuxShellGethUptimeService(shellService, clock);
    }

    @Test
    public void testGethUptimeMinutes() throws IOException {
        Instant expected = Instant.parse("2007-12-03T10:12:33.00Z");
        when(shellService.execute(any(String.class))).thenReturn("geth                  02:57");

        Instant result = gethUptimeService.getUptime();

        assertEquals(expected, result);
    }

    @Test
    public void testGethUptimeHours() throws IOException {
        Instant expected = Instant.parse("2007-12-03T07:12:33.00Z");
        when(shellService.execute(any(String.class))).thenReturn("geth                  03:02:57");

        Instant result = gethUptimeService.getUptime();

        assertEquals(expected, result);
    }

    @Test
    public void testGethUptimeDays() throws IOException {
        Instant expected = Instant.parse("2007-12-02T07:12:33.00Z");
        when(shellService.execute(any(String.class))).thenReturn("geth                  1-03:02:57");

        Instant result = gethUptimeService.getUptime();

        assertEquals(expected, result);
    }

    @Test
    public void testGethUptimeInvalidReturnsNull() throws IOException {
        when(shellService.execute(any(String.class))).thenReturn("asdf");

        Instant result = gethUptimeService.getUptime();

        assertNull(result);
    }
}
