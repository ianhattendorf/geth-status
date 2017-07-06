package com.ianhattendorf.geth.gethstatus;

import org.junit.*;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class ApplicationTest {

    @Rule
    public final ExternalResource servers = new ExternalServersRule();

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
