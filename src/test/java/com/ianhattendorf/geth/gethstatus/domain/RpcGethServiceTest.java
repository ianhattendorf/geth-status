package com.ianhattendorf.geth.gethstatus.domain;

import com.ianhattendorf.geth.gethstatus.service.GethService;
import com.ianhattendorf.geth.gethstatus.service.RpcGethService;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class RpcGethServiceTest {

    private final GethService rpcGethService = new RpcGethService(new MockGethRpcApi());

    @Test
    public void testGetProtocolVersion() {
        assertEquals(10001, rpcGethService.getProtocolVersion());
    }

    @Test
    public void testIsListening() {
        assertEquals(true, rpcGethService.isListening());
    }

    @Test
    public void testGetPeerCount() {
        assertEquals(16, rpcGethService.getPeerCount());
    }

    @Test
    public void testGetSyncing() {
        assertEquals(Boolean.FALSE, rpcGethService.getSyncing());
    }

    @Test
    public void testGetBlockNumber() {
        assertEquals(291, rpcGethService.getBlockNumber());
    }
}
