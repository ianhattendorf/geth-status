package com.ianhattendorf.geth.gethstatus.service;

public interface GethService {
    int getProtocolVersion();
    boolean isListening();
    int getPeerCount();
    Object getSyncing();
    int getBlockNumber();

}
