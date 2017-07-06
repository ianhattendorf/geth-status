package com.ianhattendorf.geth.gethstatus.service;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource("classpath:test.properties")
public class IpifyPublicIpServiceIT {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    @Autowired
    private PublicIpService publicIpService;

    @Test
    public void ipifyPublicIpServiceReturnsInfo() {
        stubFor(get(urlEqualTo("/?format=json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"ip\":\"1.2.3.4\"}")));
        assertEquals("1.2.3.4", publicIpService.getPublicIp());
    }

    @Test
    public void ipifyPublicIpServiceReturnsUnknownIpWhenServiceIsDown() {
        stubFor(get(urlEqualTo("/?format=json"))
                .willReturn(aResponse()
                        .withStatus(403)));
        assertEquals("Unknown", publicIpService.getPublicIp());
    }
}
