package com.ianhattendorf.geth.gethstatus.service;

import java.time.Instant;

public interface GethUptimeService {
    Instant getUptime();
}
