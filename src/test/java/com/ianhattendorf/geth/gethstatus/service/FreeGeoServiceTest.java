package com.ianhattendorf.geth.gethstatus.service;

import com.ianhattendorf.geth.gethstatus.domain.FreeGeoInfo;
import com.ianhattendorf.geth.gethstatus.domain.MockFreeGeoApi;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FreeGeoServiceTest {

    private MockFreeGeoApi mockFreeGeoApi;
    private GeoService geoService;

    @Before
    public void setUp() {
        mockFreeGeoApi = new MockFreeGeoApi();
        geoService = new FreeGeoService(mockFreeGeoApi);
    }

    @Test
    public void geoServiceReturnsInfo() {
        FreeGeoInfo info = new FreeGeoInfo();
        info.setIp("1.2.3.4");
        mockFreeGeoApi.setFreeGeoInfo(info);
        assertEquals("1.2.3.4", geoService.getInfo().getIp());
    }

    @Test
    public void geoServiceReturnsUnknownIpWhenServiceIsDown() {
        mockFreeGeoApi.setThrowable(new Throwable());
        assertEquals("Unknown", geoService.getInfo().getIp());
    }
}
