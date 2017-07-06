package com.ianhattendorf.geth.gethstatus.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ianhattendorf.geth.gethstatus.Application;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class IpifyPublicIpServiceTest {

    private MockWebServer server;
    private PublicIpService publicIpService;

    @Before
    public void setUp() {
        server = new MockWebServer();
        String baseUrl = server.url("/").toString();
        Application application = new Application();
        publicIpService = new IpifyPublicIpService(application.ipifyApi(application.ipifyApiRetrofit(baseUrl)));
    }

    @After
    public void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    public void ipifyPublicIpServiceReturnsInfo() throws InterruptedException {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"ip\":\"1.2.3.4\"}"));

        assertEquals("1.2.3.4", publicIpService.getPublicIp());

        RecordedRequest request = server.takeRequest();
        assertEquals("/?format=json", request.getPath());
    }

    @Test
    public void ipifyPublicIpServiceReturnsUnknownIpWhenServiceIsDown() throws InterruptedException {
        server.enqueue(new MockResponse()
                .setResponseCode(500));

        assertEquals("Unknown", publicIpService.getPublicIp());

        RecordedRequest request = server.takeRequest();
        assertEquals("/?format=json", request.getPath());
    }

    public static class IpifyPublicIpDispatcher extends Dispatcher {
        @Override
        public MockResponse dispatch(RecordedRequest request) {
            if (!request.getMethod().equals("GET") || !request.getPath().equals("/?format=json")) {
                return new MockResponse().setResponseCode(404);
            }

            return new MockResponse()
                    .setResponseCode(200)
                    .setHeader("Content-Type", "application/json")
                    .setBody("{\"ip\":\"1.2.3.4\"}");
        }
    }
}
