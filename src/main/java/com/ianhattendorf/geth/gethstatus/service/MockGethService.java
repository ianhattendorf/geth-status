package com.ianhattendorf.geth.gethstatus.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class MockGethService implements GethService {
    @Override
    public int getPeerCount() {
        return ThreadLocalRandom.current().nextInt(2, 30);
    }
}
