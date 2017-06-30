package com.ianhattendorf.geth.gethstatus;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() throws Exception {
    }

    @Test
    public void indexShouldRender() throws Exception {
        assertThat(restTemplate.getForObject("http://localhost:" + port + "/", String.class))
                .contains("Geth Node Status");
    }
    @Test
    public void indexShouldDisplayPeerCount() throws Exception {
        // TODO verify peercount is displayed, not that thymeleaf replaced placeholder
        assertThat(restTemplate.getForObject("http://localhost:" + port + "/", String.class))
                .doesNotContain("<td>-1</td>");
    }
}
