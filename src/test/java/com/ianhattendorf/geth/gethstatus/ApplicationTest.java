package com.ianhattendorf.geth.gethstatus;

import com.ianhattendorf.geth.gethstatus.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTest {

    @Configuration
    @Import(Application.class)
    public static class TestConfig {
        @Bean
        public GethRpcApi gethRpcApi() {
            return new MockGethRpcApi();
        }

        @Bean
        public FreeGeoApi freeGeoApi() {
            return new MockFreeGeoApi();
        }

        @Bean
        public IpifyApi ipifyApi() {
            return new MockIpifyApi();
        }
    }

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() throws Exception {
    }

    @Test
    public void indexShouldRenderWithTitle() throws Exception {
        assertThat(restTemplate.getForObject(getIndexURL(), String.class))
                .contains("<title>Geth Node Status</title>");
    }

    private String getIndexURL() {
        return "http://localhost:" + port + "/";
    }
}
