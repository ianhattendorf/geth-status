package com.ianhattendorf.geth.gethstatus.domain;

import java.util.LinkedHashMap;
import java.util.Map;

public final class MockGethRpcApi implements GethRpcApi {

    private boolean syncing;

    public boolean isSyncing() {
        return syncing;
    }

    public void setSyncing(boolean syncing) {
        this.syncing = syncing;
    }

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
        if (syncing) {
            Map<String, String> syncingMap = new LinkedHashMap<>(5);
            syncingMap.put("currentBlock", "0x2183c");
            syncingMap.put("highestBlock", "0x3c51c0");
            syncingMap.put("knownStates", "0x0");
            syncingMap.put("pulledStates", "0x0");
            syncingMap.put("startingBlock", "0x0");
            return syncingMap;
        }
        return "false";
    }

    @Override
    public String ethBlockNumber() {
        return "0x123";
    }
}
