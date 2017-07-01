package com.ianhattendorf.geth.gethstatus.service;

import com.ianhattendorf.geth.gethstatus.domain.GethRpcApi;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class RpcGethService implements GethService {

    private final GethRpcApi gethRpcApi;

    @Autowired
    public RpcGethService(GethRpcApi gethRpcApi) {
        this.gethRpcApi = gethRpcApi;
    }

    @Override
    public int getProtocolVersion() {
        return hexStringToInt(gethRpcApi.ethProtocolVersion());
    }

    @Override
    public boolean isListening() {
        return gethRpcApi.netListening();
    }

    @Override
    public int getPeerCount() {
        return hexStringToInt(gethRpcApi.netPeerCount());
    }

    @Override
    public String getSyncing() {
        Object syncing = gethRpcApi.ethSyncing();
        if (syncing instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, String> syncingMap = (Map<String, String>) syncing;
            return syncingMap.toString();
        }
        return syncing.toString();
    }

    @Override
    public int getBlockNumber() {
        return hexStringToInt(gethRpcApi.ethBlockNumber());
    }

    private int hexStringToInt(String hexString) {
        // 0x3
        if (!"0x".equals(hexString.substring(0, 2))) {
            throw new RuntimeException("Invalid hex string: " + hexString); // TODO more specific exception
        }
        return Integer.valueOf(hexString.substring(2), 16);
    }
}
