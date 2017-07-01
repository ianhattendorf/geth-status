package com.ianhattendorf.geth.gethstatus.service;

public interface GethService {
    int getProtocolVersion();
    boolean isListening();
    int getPeerCount();
    String getSyncing();
    int getBlockNumber();

}
