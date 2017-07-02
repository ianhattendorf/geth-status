package com.ianhattendorf.geth.gethstatus.domain;

import com.ianhattendorf.geth.gethstatus.domain.FreeGeoInfo;
import retrofit2.http.GET;

import java.util.concurrent.CompletableFuture;

public interface FreeGeoApi {
    @GET("/json")
    CompletableFuture<FreeGeoInfo> getInfo();
}
