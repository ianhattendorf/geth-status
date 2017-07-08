package com.ianhattendorf.geth.gethstatus.service;

import java.time.Instant;

public class MockGethUptimeService implements GethUptimeService {
    @Override
    public Instant getUptime() {
        return Instant.ofEpochSecond(1499394623);
    }
}
