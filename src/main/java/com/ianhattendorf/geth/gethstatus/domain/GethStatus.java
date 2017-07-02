package com.ianhattendorf.geth.gethstatus.domain;

import com.ianhattendorf.geth.gethstatus.service.GethService;

import java.util.List;

public class GethStatus {
    private String clientVersion;
    private int protocolVersion;
    private boolean listening;
    private int peerCount;
    private String syncing;
    private int blockNumber;
    private long gasPrice;
    private List<GethPeer> peers;

    public GethStatus(GethService gethService) {
        this.clientVersion = gethService.getClientVersion();
        this.protocolVersion = gethService.getProtocolVersion();
        this.listening = gethService.isListening();
        this.peerCount = gethService.getPeerCount();
        this.syncing = gethService.getSyncing();
        this.blockNumber = gethService.getBlockNumber();
        this.gasPrice = gethService.getGasPrice();
        this.peers = gethService.getPeers();
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public boolean isListening() {
        return listening;
    }

    public int getPeerCount() {
        return peerCount;
    }

    public String getSyncing() {
        return syncing;
    }

    public int getBlockNumber() {
        return blockNumber;
    }

    public long getGasPrice() {
        return gasPrice;
    }

    public List<GethPeer> getPeers() {
        return peers;
    }
}
