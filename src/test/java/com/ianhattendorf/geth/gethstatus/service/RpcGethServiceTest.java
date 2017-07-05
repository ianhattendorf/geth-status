package com.ianhattendorf.geth.gethstatus.service;

import com.ianhattendorf.geth.gethstatus.domain.geoip.transfer.FreeGeoInfo;
import com.ianhattendorf.geth.gethstatus.domain.geth.GethPeer;
import com.ianhattendorf.geth.gethstatus.domain.MockFreeGeoApi;
import com.ianhattendorf.geth.gethstatus.domain.MockGethRpcApi;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class RpcGethServiceTest {

    private MockGethRpcApi mockGethRpcApi;
    private MockFreeGeoApi mockFreeGeoApi;
    private GeoService geoService;
    private GethService rpcGethService;

    @Before
    public void setUp() {
        mockGethRpcApi = new MockGethRpcApi();
        mockFreeGeoApi = new MockFreeGeoApi();
        geoService = new FreeGeoService(mockFreeGeoApi);
        rpcGethService = new RpcGethService(mockGethRpcApi, geoService);
    }

    @Test
    public void testGetClientVersion() {
        assertEquals("Geth/v1.6.6", rpcGethService.getClientVersion());
    }

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
    public void testGetSyncingWhenFalse() {
        assertEquals("false", rpcGethService.getSyncing());
    }

    @Test
    public void testGetSyncingWhenTrue() {
        mockGethRpcApi.setSyncing(true);
        assertEquals("{currentBlock=0x2183c, highestBlock=0x3c51c0, knownStates=0x0, pulledStates=0x0, startingBlock=0x0}", rpcGethService.getSyncing());
    }

    @Test
    public void testGetBlockNumber() {
        assertEquals(291, rpcGethService.getBlockNumber());
    }

    @Test
    public void testGetGasPrice() {
        assertEquals(78187493520L, rpcGethService.getGasPrice());
    }

    @Test
    public void testGetPeers() {
        mockFreeGeoApi.setFreeGeoInfo(new FreeGeoInfo());
        List<GethPeer> peers = rpcGethService.getPeers();
        assertEquals(1, peers.size());
        assertEquals("Geth1", peers.get(0).getName());
    }
}
