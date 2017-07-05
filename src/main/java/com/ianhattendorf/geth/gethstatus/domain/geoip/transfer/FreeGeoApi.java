package com.ianhattendorf.geth.gethstatus.domain.geoip.transfer;

import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.concurrent.CompletableFuture;

public interface FreeGeoApi {
    @GET("/json/{ip}")
    CompletableFuture<FreeGeoInfo> getInfo(@Path("ip") String ip);
}
