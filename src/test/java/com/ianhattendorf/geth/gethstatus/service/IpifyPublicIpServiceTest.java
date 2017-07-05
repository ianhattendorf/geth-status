package com.ianhattendorf.geth.gethstatus.service;

import com.ianhattendorf.geth.gethstatus.domain.publicip.transfer.IpifyPublicIpInfo;
import com.ianhattendorf.geth.gethstatus.domain.MockIpifyApi;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IpifyPublicIpServiceTest {

    private MockIpifyApi mockIpifyApi;
    private PublicIpService publicIpService;

    @Before
    public void setUp() {
        mockIpifyApi = new MockIpifyApi();
        publicIpService = new IpifyPublicIpService(mockIpifyApi);
    }

    @Test
    public void ipifyPublicIpServiceReturnsInfo() {
        IpifyPublicIpInfo info = new IpifyPublicIpInfo("1.2.3.4");
        mockIpifyApi.setInfo(info);
        assertEquals("1.2.3.4", publicIpService.getPublicIp());
    }

    @Test
    public void ipifyPublicIpServiceReturnsUnknownIpWhenServiceIsDown() {
        mockIpifyApi.setThrowable(new Throwable());
        assertEquals("Unknown", publicIpService.getPublicIp());
    }
}
