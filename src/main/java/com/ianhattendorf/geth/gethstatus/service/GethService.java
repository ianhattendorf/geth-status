package com.ianhattendorf.geth.gethstatus.service;

import com.ianhattendorf.geth.gethstatus.domain.GethPeer;

import java.util.List;

public interface GethService {
    String getClientVersion();
    int getProtocolVersion();
    boolean isListening();
    int getPeerCount();
    String getSyncing();
    int getBlockNumber();
    long getGasPrice();
    List<GethPeer> getPeers();
}
