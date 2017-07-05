package com.ianhattendorf.geth.gethstatus.service;

import com.ianhattendorf.geth.gethstatus.domain.geoip.transfer.FreeGeoInfo;
import com.ianhattendorf.geth.gethstatus.domain.MockFreeGeoApi;
import lombok.val;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
        val info = new FreeGeoInfo();
        info.setCountryCode("US");
        info.setCountryName("United States");
        mockFreeGeoApi.setFreeGeoInfo(info);
        assertEquals("US", geoService.getInfo("1.2.3.4").getCountryCode());
        assertEquals("United States", geoService.getInfo("1.2.3.4").getCountryName());
    }

    @Test
    public void geoServiceReturnsUnknownIpWhenServiceIsDown() {
        mockFreeGeoApi.setThrowable(new Throwable());
        assertNull(geoService.getInfo(null).getCountryCode());
        assertNull(geoService.getInfo(null).getCountryName());
    }
}
