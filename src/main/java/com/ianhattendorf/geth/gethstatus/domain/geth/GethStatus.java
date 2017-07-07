package com.ianhattendorf.geth.gethstatus.domain.geth;

import com.ianhattendorf.geth.gethstatus.domain.diskusage.DiskStats;
import com.ianhattendorf.geth.gethstatus.domain.geoip.GeoInfo;
import com.ianhattendorf.geth.gethstatus.service.GethService;

import java.util.List;
import java.util.Objects;

public class GethStatus {
    private final String clientVersion;
    private final String publicIp;
    private final GeoInfo geoInfo;
    private final int protocolVersion;
    private final boolean listening;
    private final int peerCount;
    private final String syncing;
    private final int blockNumber;
    private final long gasPrice;
    private final DiskStats diskStats;
    private final List<GethPeer> peers;

    public GethStatus(GethService gethService, DiskStats diskStats, GeoInfo geoInfo) {
        this.clientVersion = gethService.getClientVersion();
        this.publicIp = geoInfo.getIp();
        this.geoInfo = geoInfo;
        this.protocolVersion = gethService.getProtocolVersion();
        this.listening = gethService.isListening();
        this.peerCount = gethService.getPeerCount();
        this.syncing = gethService.getSyncing();
        this.blockNumber = gethService.getBlockNumber();
        this.gasPrice = gethService.getGasPrice();
        this.peers = gethService.getPeers();
        this.diskStats = diskStats;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public String getPublicIp() {
        return publicIp;
    }

    public GeoInfo getGeoInfo() {
        return geoInfo;
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

    public DiskStats getDiskStats() {
        return diskStats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GethStatus that = (GethStatus) o;
        return protocolVersion == that.protocolVersion &&
                listening == that.listening &&
                peerCount == that.peerCount &&
                blockNumber == that.blockNumber &&
                gasPrice == that.gasPrice &&
                Objects.equals(clientVersion, that.clientVersion) &&
                Objects.equals(publicIp, that.publicIp) &&
                Objects.equals(geoInfo, that.geoInfo) &&
                Objects.equals(syncing, that.syncing) &&
                Objects.equals(diskStats, that.diskStats) &&
                Objects.equals(peers, that.peers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientVersion, publicIp, geoInfo, protocolVersion, listening, peerCount, syncing, blockNumber, gasPrice, diskStats, peers);
    }
}
