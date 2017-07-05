package com.ianhattendorf.geth.gethstatus.domain.publicip.transfer;

import retrofit2.http.GET;

import java.util.concurrent.CompletableFuture;

public interface IpifyApi {
    @GET("?format=json")
    CompletableFuture<IpifyPublicIpInfo> getIp();
}
