package com.ianhattendorf.geth.gethstatus.ws;

import com.ianhattendorf.geth.gethstatus.ExternalServersRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class WebSocketTest {

    private static final String WEBSOCKET_URL = "ws://localhost:%d/ws";
    private static final String WEBSOCKET_QUEUE_INIT = "/user/queue/init";
    private static final String WEBSOCKET_APP_INIT = "/app/init";

    private WebSocketStompClient stompClient;
    private BlockingQueue<String> blockingQueue;

    @LocalServerPort
    private int port;

    @Rule
    public final ExternalResource servers = new ExternalServersRule();

    @Before
    public void setUp() throws IOException {
        stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        blockingQueue = new LinkedBlockingDeque<>();
    }

    @Test
    public void shouldReceiveInitMessageFromServer() throws InterruptedException, ExecutionException, TimeoutException {
        StompSession session = stompClient
                .connect(String.format(WEBSOCKET_URL, port), new StompSessionHandlerAdapter() {})
                .get(1, TimeUnit.SECONDS);

        session.subscribe(WEBSOCKET_QUEUE_INIT, new DefaultStompFrameHandler());
        session.send(WEBSOCKET_APP_INIT, null);

        assertEquals("{\"publicIp\":\"1.2.3.4\",\"clientVersion\":\"Geth/v1.6.6-stable-10a45cb5/linux-amd64/go1.8.3\",\"protocolVersion\":10001,\"listening\":true,\"peerCount\":16,\"syncing\":\"false\",\"blockNumber\":3984201,\"gasPrice\":21000000000,\"diskStats\":{\"usedGB\":\"18.86\",\"totalGB\":\"26.45\"},\"peers\":[{\"name\":\"Geth/v1.6.5-stable-cf87713d/linux-amd64/go1.7\",\"network\":{\"remoteAddress\":\"191.235.84.50\",\"remoteGeoInfo\":{\"countryCode\":\"US\",\"countryName\":\"United States\"}}},{\"name\":\"Geth/v1.6.5-stable-cf87713d/linux-amd64/go1.7\",\"network\":{\"remoteAddress\":\"52.16.188.185\",\"remoteGeoInfo\":{\"countryCode\":\"US\",\"countryName\":\"United States\"}}}]}"
                , blockingQueue.poll(1, TimeUnit.SECONDS));
    }

    private class DefaultStompFrameHandler implements StompFrameHandler {

        @Override
        public Type getPayloadType(StompHeaders headers) {
            return byte[].class;
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            blockingQueue.offer(new String((byte[]) payload));
        }
    }
}
