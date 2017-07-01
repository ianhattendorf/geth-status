package com.ianhattendorf.geth.gethstatus.domain;

import com.googlecode.jsonrpc4j.JsonRpcMethod;

public interface GethRpcApi {

    @JsonRpcMethod("net_peerCount")
    String netPeerCount();
}
