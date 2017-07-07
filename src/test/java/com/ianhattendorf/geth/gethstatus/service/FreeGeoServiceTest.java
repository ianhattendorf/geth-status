package com.ianhattendorf.geth.gethstatus.service;

import com.ianhattendorf.geth.gethstatus.Application;
import com.ianhattendorf.geth.gethstatus.TestHelper;
import com.ianhattendorf.geth.gethstatus.domain.geoip.GeoInfo;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @After
    public void tearDown() throws IOException {
        server.shutdown();
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

    public static class FreeGeoDispatcher extends Dispatcher {

        private static final Pattern ipPattern = Pattern.compile("\\/json\\/(\\d+\\.\\d+\\.\\d+\\.\\d+)?");

        @Override
        public MockResponse dispatch(RecordedRequest request) {
            if (!request.getMethod().equals("GET")) {
                return new MockResponse().setResponseCode(405);
            }

            Matcher matcher = ipPattern.matcher(request.getPath());
            if (!matcher.matches()) {
                return new MockResponse().setResponseCode(404);
            }
            String freeGeoBody = TestHelper.loadResponseBody("service/free-geo.json");
            String ip = matcher.group(1);

            // no ip specified
            if (ip == null) {
                return new MockResponse()
                        .setResponseCode(200)
                        .setHeader("Content-Type", "application/json")
                        .setBody(freeGeoBody);
            }

            // ip specified
            return new MockResponse()
                    .setResponseCode(200)
                    .setHeader("Content-Type", "application/json")
                    .setBody(freeGeoBody.replace("1.2.3.4", ip));
        }
    }
}
