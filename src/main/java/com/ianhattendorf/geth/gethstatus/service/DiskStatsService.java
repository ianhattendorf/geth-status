package com.ianhattendorf.geth.gethstatus.service;

import com.ianhattendorf.geth.gethstatus.domain.diskusage.DiskStats;

public interface DiskStatsService {
    DiskStats getDiskStats();
}
