package com.ianhattendorf.geth.gethstatus.service;

import com.ianhattendorf.geth.gethstatus.domain.FreeGeoApi;
import com.ianhattendorf.geth.gethstatus.domain.FreeGeoInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class FreeGeoService implements GeoService {

    private static final Logger logger = LoggerFactory.getLogger(FreeGeoService.class);

    private final FreeGeoApi freeGeoApi;

    @Autowired
    public FreeGeoService(FreeGeoApi freeGeoApi) {
        this.freeGeoApi = freeGeoApi;
    }

    @Cacheable("freeGeoInfo")
    @Override
    public FreeGeoInfo getInfo() {
        try {
            logger.debug("Fetching geo IP info");
            return freeGeoApi.getInfo().get();
        } catch (InterruptedException e) {
            logger.error("Thread interrupted while loading geo ip", e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            logger.error("Exception loading geo ip", e);
        }
        FreeGeoInfo info = new FreeGeoInfo();
        info.setIp("Unknown");
        return info;
    }
}
