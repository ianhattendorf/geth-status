package com.ianhattendorf.geth.gethstatus.service;

import com.ianhattendorf.geth.gethstatus.Application;
import com.ianhattendorf.geth.gethstatus.domain.geoip.GeoInfo;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Before;
import org.junit.Test;

import static com.ianhattendorf.geth.gethstatus.TestHelper.loadResponseBody;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class FreeGeoServiceTest {

    private MockWebServer server;
    private GeoService geoService;

    @Before
    public void setUp() {
        server = new MockWebServer();
        String baseUrl = server.url("/").toString();
        Application application = new Application();
        geoService = new FreeGeoService(application.geoService(application.freeGeoApiRetrofit(baseUrl)));
    }

    @Test
    public void freeGeoServiceReturnsServerInfo() throws InterruptedException {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(loadResponseBody("service/free-geo.json")));

        GeoInfo info = geoService.getInfo(null);

        assertEquals("US", info.getCountryCode());
        assertEquals("United States", info.getCountryName());

        RecordedRequest request = server.takeRequest();
        assertEquals("/json/", request.getPath());
    }

    @Test
    public void freeGeoServiceReturnsSpecifiedIpInfo() throws InterruptedException {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(loadResponseBody("service/free-geo.json")));

        GeoInfo info = geoService.getInfo("1.2.3.4");

        assertEquals("US", info.getCountryCode());
        assertEquals("United States", info.getCountryName());

        RecordedRequest request = server.takeRequest();
        assertEquals("/json/1.2.3.4", request.getPath());
    }

    @Test
    public void freeGeoServiceReturnsEmptyInfoWhenServiceIsDown() throws InterruptedException {
        server.enqueue(new MockResponse()
                .setResponseCode(500));

        GeoInfo info = geoService.getInfo(null);

        assertNull(info.getCountryCode());
        assertNull(info.getCountryName());

        RecordedRequest request = server.takeRequest();
        assertEquals("/json/", request.getPath());
    }
}
