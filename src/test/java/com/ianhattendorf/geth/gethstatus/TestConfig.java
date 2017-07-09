package com.ianhattendorf.geth.gethstatus;

import com.ianhattendorf.geth.gethstatus.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {
    @Bean
    public DiskStatsService diskStatsService() {
        return new MockDistStatsService();
    }

    @Bean
    public MemoryStatsService memoryStatsService() {
        return new MockMemoryStatsService();
    }

    @Bean
    public GethUptimeService gethUptimeService() {
        return new MockGethUptimeService();
    }
}
