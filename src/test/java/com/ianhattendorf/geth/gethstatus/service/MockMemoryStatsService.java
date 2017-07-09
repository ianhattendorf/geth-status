package com.ianhattendorf.geth.gethstatus.service;

import com.ianhattendorf.geth.gethstatus.domain.memoryusage.MemoryStats;

public class MockMemoryStatsService implements MemoryStatsService {
    @Override
    public MemoryStats getMemoryStats() {
        return new MemoryStats("1.23", "3.21");
    }
}
