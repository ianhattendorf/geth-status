package com.ianhattendorf.geth.gethstatus.domain;

import com.ianhattendorf.geth.gethstatus.domain.geoip.transfer.FreeGeoApi;
import com.ianhattendorf.geth.gethstatus.domain.geoip.transfer.FreeGeoInfo;
import lombok.val;

import java.util.concurrent.CompletableFuture;

public class MockFreeGeoApi implements FreeGeoApi {

    private FreeGeoInfo freeGeoInfo;
    private Throwable throwable;

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public FreeGeoInfo getFreeGeoInfo() {
        return freeGeoInfo;
    }

    public void setFreeGeoInfo(FreeGeoInfo freeGeoInfo) {
        this.freeGeoInfo = freeGeoInfo;
    }

    @Override
    public CompletableFuture<FreeGeoInfo> getInfo(String ip) {
        if (throwable != null) {
            val completableFuture = new CompletableFuture<FreeGeoInfo>();
            completableFuture.completeExceptionally(throwable);
            return completableFuture;
        }
        return CompletableFuture.completedFuture(freeGeoInfo);
    }
}
