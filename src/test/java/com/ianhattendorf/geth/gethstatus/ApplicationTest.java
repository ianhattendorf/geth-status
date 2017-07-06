package com.ianhattendorf.geth.gethstatus;

import com.ianhattendorf.geth.gethstatus.service.FreeGeoServiceTest;
import com.ianhattendorf.geth.gethstatus.service.IpifyPublicIpServiceTest;
import com.ianhattendorf.geth.gethstatus.service.RpcGethServiceTest;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestConfiguration("classpath:test.properties")
public class ApplicationTest {

    private MockWebServer gethServer;
    private MockWebServer ipifyPublicIpServer;
    private MockWebServer freeGeoServer;

    @Before
    public void setUp() throws IOException {
        gethServer = TestHelper.initMockServer(8088, new RpcGethServiceTest.RpcDispatcher(true));
        ipifyPublicIpServer = TestHelper.initMockServer(8089, new IpifyPublicIpServiceTest.IpifyPublicIpDispatcher());
        freeGeoServer = TestHelper.initMockServer(8090, new FreeGeoServiceTest.FreeGeoDispatcher());
    }

    @After
    public void tearDown() throws IOException {
        gethServer.shutdown();
        ipifyPublicIpServer.shutdown();
        freeGeoServer.shutdown();
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
