package com.ianhattendorf.geth.gethstatus.service;

import com.ianhattendorf.geth.gethstatus.domain.memoryusage.MemoryStats;

public interface MemoryStatsService {
    MemoryStats getMemoryStats();
}
