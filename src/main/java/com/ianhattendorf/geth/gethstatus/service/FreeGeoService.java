package com.ianhattendorf.geth.gethstatus.service;

import com.ianhattendorf.geth.gethstatus.domain.geoip.transfer.FreeGeoApi;
import com.ianhattendorf.geth.gethstatus.domain.geoip.GeoInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class FreeGeoService implements GeoService {

    private static final Logger logger = LoggerFactory.getLogger(FreeGeoService.class);

    private final FreeGeoApi freeGeoApi;

    @Autowired
    public FreeGeoService(FreeGeoApi freeGeoApi) {
        this.freeGeoApi = freeGeoApi;
    }

    @Cacheable("geoInfo")
    @Override
    public GeoInfo getInfo() {
        return getInfo(null);
    }

    @Cacheable("geoInfo")
    @Override
    public GeoInfo getInfo(String ip) {
        if (ip == null) {
            ip = "";
        }
        try {
            logger.debug("Fetching geo IP info: {}", ip);
            return freeGeoApi.getInfo(ip).get(30, TimeUnit.SECONDS).toGeoInfo();
        } catch (InterruptedException e) {
            logger.error("Thread interrupted while loading geo ip", e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException | TimeoutException e) {
            logger.error("Exception loading geo ip", e);
        }
        return new GeoInfo();
    }
}
