package com.ianhattendorf.geth.gethstatus.domain.geth;

import com.ianhattendorf.geth.gethstatus.domain.diskusage.DiskStats;
import com.ianhattendorf.geth.gethstatus.service.GethService;
import lombok.Data;
import lombok.Value;

import java.util.List;
import java.util.Objects;

@Value
public class GethStatus {
    String publicIp;
    String clientVersion;
    int protocolVersion;
    boolean listening;
    int peerCount;
    String syncing;
    int blockNumber;
    long gasPrice;
    DiskStats diskStats;
    List<GethPeer> peers;

    public GethStatus(GethService gethService, DiskStats diskStats, String publicIp) {
        this.publicIp = publicIp;
        this.clientVersion = gethService.getClientVersion();
        this.protocolVersion = gethService.getProtocolVersion();
        this.listening = gethService.isListening();
        this.peerCount = gethService.getPeerCount();
        this.syncing = gethService.getSyncing();
        this.blockNumber = gethService.getBlockNumber();
        this.gasPrice = gethService.getGasPrice();
        this.peers = gethService.getPeers();
        this.diskStats = diskStats;
    }
}
