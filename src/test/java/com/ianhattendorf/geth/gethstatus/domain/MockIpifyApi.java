package com.ianhattendorf.geth.gethstatus.domain;

import com.ianhattendorf.geth.gethstatus.domain.publicip.transfer.IpifyApi;
import com.ianhattendorf.geth.gethstatus.domain.publicip.transfer.IpifyPublicIpInfo;

import java.util.concurrent.CompletableFuture;

public class MockIpifyApi implements IpifyApi {

    private IpifyPublicIpInfo info;
    private Throwable throwable;

    public MockIpifyApi() {
        info = new IpifyPublicIpInfo("1.2.3.4");
    }

    public IpifyPublicIpInfo getInfo() {
        return info;
    }

    public void setInfo(IpifyPublicIpInfo info) {
        this.info = info;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public CompletableFuture<IpifyPublicIpInfo> getIp() {
        if (throwable != null) {
            CompletableFuture<IpifyPublicIpInfo> completableFuture = new CompletableFuture<>();
            completableFuture.completeExceptionally(throwable);
            return completableFuture;
        }
        return CompletableFuture.completedFuture(info);
    }
}
