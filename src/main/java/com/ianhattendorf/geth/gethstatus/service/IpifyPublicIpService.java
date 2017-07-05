package com.ianhattendorf.geth.gethstatus.service;

import com.ianhattendorf.geth.gethstatus.domain.publicip.transfer.IpifyApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
public class IpifyPublicIpService implements PublicIpService {

    private final IpifyApi ipifyApi;

    public IpifyPublicIpService(IpifyApi ipifyApi) {
        this.ipifyApi = ipifyApi;
    }

    @Cacheable("ipifyPublicIp")
    @Override
    public String getPublicIp() {
        try {
            log.debug("Fetching public ip");
            return ipifyApi.getIp().get(5, TimeUnit.SECONDS).getIp();
        } catch (InterruptedException e) {
            log.error("Thread interrupted while fetching public ip", e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException | TimeoutException e) {
            log.error("Exception fetching public ip", e);
        }
        return "Unknown";
    }
}
