package com.ianhattendorf.geth.gethstatus.service;

import com.ianhattendorf.geth.gethstatus.domain.memoryusage.MemoryStats;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;

import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LinuxShellMemoryStatsServiceTest {

    private ShellService shellService;
    private MemoryStatsService memoryStatsService;

    @Before
    public void setUp() {
        shellService = mock(ShellService.class);
        memoryStatsService = new LinuxShellMemoryStatsService(shellService);
    }

    @Test
    public void testGetMemoryStats() throws IOException {
        when(shellService.execute(eq("awk"), contains("MemFree"), eq("/proc/meminfo")))
                .thenReturn("324840");
        when(shellService.execute(eq("awk"), contains("MemTotal"), eq("/proc/meminfo")))
                .thenReturn("6017628");

        MemoryStats result = memoryStatsService.getMemoryStats();

        assertEquals("5.43", result.getUsedGB());
        assertEquals("5.74", result.getTotalGB());
    }

    @Test
    public void testGetMemoryStatsReturnsNullOnError() throws IOException {
        when(shellService.execute(any(String.class)))
                .thenThrow(new IOException("Some IO Exception"));

        MemoryStats result = memoryStatsService.getMemoryStats();

        assertNull(result);
    }
}
