package com.ianhattendorf.geth.gethstatus.service;

public interface GethService {
    String getClientVersion();
    int getProtocolVersion();
    boolean isListening();
    int getPeerCount();
    String getSyncing();
    int getBlockNumber();
    long getGasPrice();
}
