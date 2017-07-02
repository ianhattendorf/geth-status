package com.ianhattendorf.geth.gethstatus.service;

import com.ianhattendorf.geth.gethstatus.domain.GethPeer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class MockGethService implements GethService {
    @Override
    public String getClientVersion() {
        return "Geth/v1.6.6-stable-10a45cb5/linux-amd64/go1.8.3";
    }

    @Override
    public int getProtocolVersion() {
        return 10001;
    }

    @Override
    public boolean isListening() {
        return true;
    }

    @Override
    public int getPeerCount() {
        return ThreadLocalRandom.current().nextInt(2, 30);
    }

    @Override
    public String getSyncing() {
        return "false";
    }

    @Override
    public int getBlockNumber() {
        return 123;
    }

    @Override
    public long getGasPrice() {
        return 1234567890;
    }

    @Override
    public List<GethPeer> getPeers() {
        List<GethPeer> peers = new ArrayList<>();
        GethPeer peer = new GethPeer();
        peer.setName("Geth1");
        GethPeer.Network network = new GethPeer.Network();
        network.setRemoteAddress("1.2.3.4");
        peer.setNetwork(network);
        peers.add(peer);
        return peers;
    }
}
