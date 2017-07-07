package com.ianhattendorf.geth.gethstatus;

import com.ianhattendorf.geth.gethstatus.service.DiskStatsService;
import com.ianhattendorf.geth.gethstatus.service.MockDistStatsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {
    @Bean
    public DiskStatsService diskStatsService() {
        return new MockDistStatsService();
    }
}
