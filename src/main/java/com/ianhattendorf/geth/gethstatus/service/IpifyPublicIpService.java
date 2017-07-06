package com.ianhattendorf.geth.gethstatus.service;

import com.ianhattendorf.geth.gethstatus.domain.publicip.transfer.IpifyApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class IpifyPublicIpService implements PublicIpService {

    private static final Logger logger = LoggerFactory.getLogger(IpifyPublicIpService.class);

    private final IpifyApi ipifyApi;

    public IpifyPublicIpService(IpifyApi ipifyApi) {
        this.ipifyApi = ipifyApi;
    }

    @Cacheable("ipifyPublicIp")
    @Override
    public String getPublicIp() {
        try {
            logger.debug("Fetching public ip");
            return ipifyApi.getIp().get(30, TimeUnit.SECONDS).getIp();
        } catch (InterruptedException e) {
            logger.error("Thread interrupted while fetching public ip", e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException | TimeoutException e) {
            logger.error("Exception fetching public ip", e);
        }
        return "Unknown";
    }
}
