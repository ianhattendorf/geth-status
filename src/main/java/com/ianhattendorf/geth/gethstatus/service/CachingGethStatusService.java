package com.ianhattendorf.geth.gethstatus.service;

import com.ianhattendorf.geth.gethstatus.domain.geth.GethStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class CachingGethStatusService implements GethStatusService {

    private final GethService gethService;
    private final DiskStatsService diskStatsService;
    private final GeoService geoService;
    private final Duration gethStatusCacheDuration;
    private final Object updateLock = new Object();

    private GethStatus gethStatus;
    private Instant lastUpdated = Instant.MIN;

    @Autowired
    public CachingGethStatusService(GethService gethService, DiskStatsService diskStatsService, GeoService geoService, Duration gethStatusCacheDuration) {
        this.gethService = gethService;
        this.diskStatsService = diskStatsService;
        this.geoService = geoService;
        this.gethStatusCacheDuration = gethStatusCacheDuration;
    }

    @Override
    public GethStatus getGethStatus() {
        Instant now = Instant.now();
        if (lastUpdated.plus(gethStatusCacheDuration).isBefore(now)) {
            synchronized (updateLock) {
                if (lastUpdated.plus(gethStatusCacheDuration).isBefore(now)) {
                    gethStatus = new GethStatus(gethService, diskStatsService.getDiskStats(), geoService.getInfo());
                    lastUpdated = now;
                }
            }
        }
        return gethStatus;
    }
}
