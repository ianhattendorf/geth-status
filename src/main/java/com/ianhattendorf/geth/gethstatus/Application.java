package com.ianhattendorf.geth.gethstatus;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.googlecode.jsonrpc4j.ProxyUtil;
import com.ianhattendorf.geth.gethstatus.domain.GethRpcApi;
import com.ianhattendorf.geth.gethstatus.domain.FreeGeoApi;
import com.ianhattendorf.geth.gethstatus.service.GethService;
import com.ianhattendorf.geth.gethstatus.service.RpcGethService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import retrofit2.Retrofit;
import retrofit2.adapter.java8.Java8CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public JsonRpcHttpClient jsonRpcHttpClient(@Value("${geth.rcp.endpoint:http://localhost:8545}") String gethRpcEndpoint) throws MalformedURLException {
        //You can add authentication headers etc to this map
        Map<String, String> map = new HashMap<>();
        URL url = new URL(gethRpcEndpoint);
        return new JsonRpcHttpClient(url, map);
    }

    @Bean
    public GethRpcApi exampleClientAPI(JsonRpcHttpClient jsonRpcHttpClient) {
        return ProxyUtil.createClientProxy(getClass().getClassLoader(), GethRpcApi.class, jsonRpcHttpClient);
    }

    @Bean
    public GethService gethService(GethRpcApi gethRpcApi) {
        return new RpcGethService(gethRpcApi);
    }

    @Bean
    public Duration gethStatusCacheDuration(@Value("${geth.status.cacheDuration}") int cacheDuration) {
        return Duration.ofMillis(cacheDuration);
    }

    @Bean
    public Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://freegeoip.net")
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(Java8CallAdapterFactory.create())
                .build();
    }

    @Bean
    public FreeGeoApi geoService(Retrofit retrofit) {
        return retrofit.create(FreeGeoApi.class);
    }
}
