package com.ianhattendorf.geth.gethstatus.service;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;
import com.ianhattendorf.geth.gethstatus.domain.geoip.GeoInfo;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource("classpath:test.properties")
public class FreeGeoServiceIT {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8090);

    @Autowired
    private GeoService geoService;

    @Test
    public void freeGeoServiceReturnsServerInfo() {
        stubFor(get(urlEqualTo("/json/"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("service/free-geo.json")));
        GeoInfo info = geoService.getInfo(null);
        assertEquals("US", info.getCountryCode());
        assertEquals("United States", info.getCountryName());
    }

    @Test
    public void freeGeoServiceReturnsSpecifiedIpInfo() {
        stubFor(get(urlEqualTo("/json/1.2.3.4"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("service/free-geo.json")));
        GeoInfo info = geoService.getInfo("1.2.3.4");
        assertEquals("US", info.getCountryCode());
        assertEquals("United States", info.getCountryName());
    }

    @Test
    public void freeGeoServiceReturnsEmptyInfoWhenServiceIsDown() {
        stubFor(get(urlEqualTo("/json/"))
                .willReturn(aResponse()
                        .withStatus(403)));
        GeoInfo info = geoService.getInfo(null);
        assertNull(info.getCountryCode());
        assertNull(info.getCountryName());
    }
}
