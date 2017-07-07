package com.ianhattendorf.geth.gethstatus.service;

import com.ianhattendorf.geth.gethstatus.domain.diskusage.DiskStats;

public class MockDistStatsService implements DiskStatsService {
    @Override
    public DiskStats getDiskStats() {
        return new DiskStats("123.45", "543.21");
    }
}
