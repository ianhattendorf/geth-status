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
    public String getClientVersion() {
        return gethRpcApi.web3ClientVersion();
    }

    @Override
    public int getProtocolVersion() {
        return hexStringToLong(gethRpcApi.ethProtocolVersion()).intValue();
    }

    @Override
    public boolean isListening() {
        return gethRpcApi.netListening();
    }

    @Override
    public int getPeerCount() {
        return hexStringToLong(gethRpcApi.netPeerCount()).intValue();
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
        return hexStringToLong(gethRpcApi.ethBlockNumber()).intValue();
    }

    @Override
    public long getGasPrice() {
        return hexStringToLong(gethRpcApi.ethGasPrice());
    }

    private Long hexStringToLong(String hexString) {
        // 0x3
        if (!"0x".equals(hexString.substring(0, 2))) {
            throw new RuntimeException("Invalid hex string: " + hexString); // TODO more specific exception
        }
        return Long.valueOf(hexString.substring(2), 16);
    }
}
