package com.ianhattendorf.geth.gethstatus.domain.geth.transfer;

import com.googlecode.jsonrpc4j.JsonRpcMethod;
import com.ianhattendorf.geth.gethstatus.domain.geth.GethPeer;

import java.util.List;

public interface GethRpcApi {

    @JsonRpcMethod("web3_clientVersion")
    String web3ClientVersion();

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

    @JsonRpcMethod("eth_gasPrice")
    String ethGasPrice();

    @JsonRpcMethod("admin_peers")
    List<GethPeer> adminPeers();
}
