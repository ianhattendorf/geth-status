package com.ianhattendorf.geth.gethstatus.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ianhattendorf.geth.gethstatus.Application;
import com.ianhattendorf.geth.gethstatus.TestHelper;
import com.ianhattendorf.geth.gethstatus.domain.MockFreeGeoApi;
import com.ianhattendorf.geth.gethstatus.domain.geoip.transfer.FreeGeoInfo;
import com.ianhattendorf.geth.gethstatus.domain.geth.GethPeer;
import com.ianhattendorf.geth.gethstatus.domain.geth.transfer.GethRpcApi;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RpcGethServiceTest {

    private MockWebServer server;
    private MockFreeGeoApi mockFreeGeoApi;
    private GethService rpcGethService;

    @Before
    public void setUp() throws IOException {
        server = TestHelper.initMockServer(0, new RpcDispatcher(false));
        String baseUrl = server.url("/").toString();
        Application application = new Application();
        GethRpcApi gethRpcApi = application.gethRpcApi(application.jsonRpcHttpClient(baseUrl));
        mockFreeGeoApi = new MockFreeGeoApi();
        rpcGethService = new RpcGethService(gethRpcApi, new FreeGeoService(mockFreeGeoApi));
    }

    @After
    public void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    public void testGetClientVersion() {
        assertEquals("Geth/v1.6.6-stable-10a45cb5/linux-amd64/go1.8.3", rpcGethService.getClientVersion());
    }

    @Test
    public void testGetProtocolVersion() {
        assertEquals(10001, rpcGethService.getProtocolVersion());
    }

    @Test
    public void testIsListening() {
        assertEquals(true, rpcGethService.isListening());
    }

    @Test
    public void testGetPeerCount() {
        assertEquals(16, rpcGethService.getPeerCount());
    }

    @Test
    public void testGetSyncingWhenFalse() {
        assertEquals("false", rpcGethService.getSyncing());
    }

    @Test
    public void testGetSyncingWhenTrue() {
        server.setDispatcher(new RpcDispatcher(true));
        assertEquals("{currentBlock=0x2183c, highestBlock=0x3c51c0, knownStates=0x0, pulledStates=0x0, startingBlock=0x0}", rpcGethService.getSyncing());
    }

    @Test
    public void testGetBlockNumber() {
        assertEquals(3984201, rpcGethService.getBlockNumber());
    }

    @Test
    public void testGetGasPrice() {
        assertEquals(21000000000L, rpcGethService.getGasPrice());
    }

    @Test
    public void testGetPeers() {
        mockFreeGeoApi.setFreeGeoInfo(new FreeGeoInfo());
        List<GethPeer> peers = rpcGethService.getPeers();

        assertEquals(2, peers.size());

        assertNull(peers.get(0).getId());
        assertNull(peers.get(0).getCaps());
        assertEquals("Geth/v1.6.5-stable-cf87713d/linux-amd64/go1.7", peers.get(0).getName());
        assertEquals("191.235.84.50", peers.get(0).getNetwork().getRemoteAddress());

        assertNull(peers.get(1).getId());
        assertNull(peers.get(1).getCaps());
        assertEquals("Geth/v1.6.5-stable-cf87713d/linux-amd64/go1.7", peers.get(1).getName());
        assertEquals("52.16.188.185", peers.get(1).getNetwork().getRemoteAddress());
    }

    public static class JsonRPCBody {
        private String id;
        private String jsonrpc;
        private String method;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getJsonrpc() {
            return jsonrpc;
        }

        public void setJsonrpc(String jsonrpc) {
            this.jsonrpc = jsonrpc;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }
    }

    public static class RpcDispatcher extends Dispatcher {

        private final ObjectMapper objectMapper = new ObjectMapper();

        private final boolean syncing;

        public RpcDispatcher(boolean syncing) {
            this.syncing = syncing;
        }

        public boolean isSyncing() {
            return syncing;
        }

        @Override
        public MockResponse dispatch(RecordedRequest request) {
            if (!request.getMethod().equals("POST") || !request.getPath().equals("/")) {
                return new MockResponse().setResponseCode(404);
            }
            JsonRPCBody body;
            try {
                String requestBody = request.getBody().readUtf8();
                body = objectMapper.readValue(requestBody, JsonRPCBody.class);
            } catch (IOException e) {
                throw new RuntimeException("Failed to parse JsonRPCBody");
            }

            String result;
            switch (body.getMethod()) {
                case "web3_clientVersion":
                    result = "\"Geth/v1.6.6-stable-10a45cb5/linux-amd64/go1.8.3\"";
                    break;
                case "eth_protocolVersion":
                    result = "\"0x2711\"";
                    break;
                case "net_listening":
                    result = "true";
                    break;
                case "net_peerCount":
                    result = "\"0x10\"";
                    break;
                case "eth_syncing":
                    result = syncing
                            ? "\"{currentBlock=0x2183c, highestBlock=0x3c51c0, knownStates=0x0, pulledStates=0x0, startingBlock=0x0}\""
                            : "false";
                    break;
                case "eth_blockNumber":
                    result = "\"0x3ccb49\"";
                    break;
                case "eth_gasPrice":
                    result = "\"0x4e3b29200\"";
                    break;
                case "admin_peers":
                    result = "[{\"id\":\"78de8a0916848093c73790ead81d1928bec737d565119932b98c6b100d944b7a95e94f847f689fc723399d2e31129d182f7ef3863f2b4c820abbf3ab2722344d\",\"name\":\"Geth/v1.6.5-stable-cf87713d/linux-amd64/go1.7\",\"caps\":[\"eth/62\",\"eth/63\",\"les/1\"],\"network\":{\"localAddress\":\"10.242.2.2:54012\",\"remoteAddress\":\"191.235.84.50:30303\"},\"protocols\":{\"les\":{\"version\":1,\"difficulty\":452429740452822458354,\"head\":\"7b5de16b474dbc5ada6f05443694513994c084169daa28de20ab76d5d14a5c4d\"}}},{\"id\":\"a979fb575495b8d6db44f750317d0f4622bf4c2aa3365d6af7c284339968eef29b69ad0dce72a4d8db5ebb4968de0e3bec910127f134779fbcb0cb6d3331163c\",\"name\":\"Geth/v1.6.5-stable-cf87713d/linux-amd64/go1.7\",\"caps\":[\"eth/62\",\"eth/63\",\"les/1\"],\"network\":{\"localAddress\":\"10.242.2.2:56708\",\"remoteAddress\":\"52.16.188.185:30303\"},\"protocols\":{\"les\":{\"version\":1,\"difficulty\":452429740452822458354,\"head\":\"7b5de16b474dbc5ada6f05443694513994c084169daa28de20ab76d5d14a5c4d\"}}}]";
                    break;
                default:
                    return new MockResponse().setResponseCode(404);
            }
            String responseBody = String.format("{\"jsonrpc\":\"2.0\",\"id\":\"%s\",\"result\":%s}", body.getId(), result);
            return new MockResponse().setBody(responseBody);
        }
    }
}
