package com.ianhattendorf.geth.gethstatus.service;

import com.ianhattendorf.geth.gethstatus.domain.FreeGeoInfo;
import com.ianhattendorf.geth.gethstatus.domain.GethPeer;
import com.ianhattendorf.geth.gethstatus.domain.GethRpcApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RpcGethService implements GethService {

    private static final Logger logger = LoggerFactory.getLogger(RpcGethService.class);

    private final GethRpcApi gethRpcApi;
    private final GeoService geoService;

    @Autowired
    public RpcGethService(GethRpcApi gethRpcApi, GeoService geoService) {
        this.gethRpcApi = gethRpcApi;
        this.geoService = geoService;
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

    @Override
    public List<GethPeer> getPeers() {
        List<GethPeer> peers = gethRpcApi.adminPeers();
        peers.parallelStream().forEach(gethPeer -> {
            GethPeer.Network network = gethPeer.getNetwork();
            network.setRemoteAddress(ipTrimPort(network.getRemoteAddress()));

            FreeGeoInfo info = geoService.getInfo(network.getRemoteAddress());
            gethPeer.getNetwork().setRemoteGeoInfo(info);
        });
        return peers;
    }

    private String ipTrimPort(String ip) {
        int lastColonIndex = ip.lastIndexOf(":");
        if (lastColonIndex != -1) {
            return ip.substring(0, lastColonIndex);
        }
        return ip;
    }

    private Long hexStringToLong(String hexString) {
        // 0x3
        if (!"0x".equals(hexString.substring(0, 2))) {
            throw new RuntimeException("Invalid hex string: " + hexString); // TODO more specific exception
        }
        return Long.valueOf(hexString.substring(2), 16);
    }
}
