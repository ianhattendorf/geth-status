package com.ianhattendorf.geth.gethstatus.service;

import com.ianhattendorf.geth.gethstatus.domain.GethRpcApi;
import org.springframework.beans.factory.annotation.Autowired;

public class RpcGethService implements GethService {

    private final GethRpcApi gethRpcApi;

    @Autowired
    public RpcGethService(GethRpcApi gethRpcApi) {
        this.gethRpcApi = gethRpcApi;
    }

    @Override
    public int getPeerCount() {
        // 0x3
        String peerCount = gethRpcApi.netPeerCount();
        if (!"0x".equals(peerCount.substring(0, 2))) {
            throw new RuntimeException("Invalid peer count: " + peerCount); // TODO more specific exception
        }
        return Integer.valueOf(peerCount.substring(2), 16);
    }
}
