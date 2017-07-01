package com.ianhattendorf.geth.gethstatus.domain;

import com.googlecode.jsonrpc4j.JsonRpcMethod;

public interface GethRpcApi {

    @JsonRpcMethod("eth_protocolVersion")
    String ethProtocolVersion();

    @JsonRpcMethod("net_listening")
    boolean netListening();

    @JsonRpcMethod("net_peerCount")
    String netPeerCount();

    @JsonRpcMethod("eth_syncing")
    Object ethSyncing();

    @JsonRpcMethod("eth_blockNumber")
    String ethBlockNumber();
}
