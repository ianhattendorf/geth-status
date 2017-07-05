package com.ianhattendorf.geth.gethstatus.service;

import com.ianhattendorf.geth.gethstatus.domain.geoip.transfer.FreeGeoApi;
import com.ianhattendorf.geth.gethstatus.domain.geoip.GeoInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
public class FreeGeoService implements GeoService {

    private final FreeGeoApi freeGeoApi;

    @Autowired
    public FreeGeoService(FreeGeoApi freeGeoApi) {
        this.freeGeoApi = freeGeoApi;
    }

    @Cacheable("freeGeoInfo")
    @Override
    public GeoInfo getInfo(String ip) {
        try {
            log.debug("Fetching geo IP info: {}", ip);
            return freeGeoApi.getInfo(ip).get(5, TimeUnit.SECONDS).toGeoInfo();
        } catch (InterruptedException e) {
            log.error("Thread interrupted while loading geo ip", e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException | TimeoutException e) {
            log.error("Exception loading geo ip", e);
        }
        return new GeoInfo();
    }
}
