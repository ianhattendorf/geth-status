package com.ianhattendorf.geth.gethstatus.domain;

public final class MockGethRpcApi implements GethRpcApi {
    @Override
    public String ethProtocolVersion() {
        return "0x2711";
    }

    @Override
    public boolean netListening() {
        return true;
    }

    @Override
    public String netPeerCount() {
        return "0x10";
    }

    @Override
    public Object ethSyncing() {
        return Boolean.FALSE;
    }

    @Override
    public String ethBlockNumber() {
        return "0x123";
    }
}
