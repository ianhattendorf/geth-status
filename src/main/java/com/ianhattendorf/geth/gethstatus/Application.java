package com.ianhattendorf.geth.gethstatus;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.googlecode.jsonrpc4j.ProxyUtil;
import com.ianhattendorf.geth.gethstatus.domain.GethRpcApi;
import com.ianhattendorf.geth.gethstatus.service.GethService;
import com.ianhattendorf.geth.gethstatus.service.RpcGethService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableScheduling
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
}
