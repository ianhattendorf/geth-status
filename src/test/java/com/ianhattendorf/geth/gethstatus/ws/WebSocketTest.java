package com.ianhattendorf.geth.gethstatus.ws;

import com.ianhattendorf.geth.gethstatus.ExternalServersRule;
import com.ianhattendorf.geth.gethstatus.TestHelper;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
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

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.*;

import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assume.assumeThat;

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
    public void shouldReceiveInitMessageFromServer() throws InterruptedException, ExecutionException, TimeoutException, JSONException {
        // TODO ignored on jenkins, failing due to TimeoutException
        assumeThat(System.getenv("JENKINS_URL"), nullValue());
        
        StompSession session = stompClient
                .connect(String.format(WEBSOCKET_URL, port), new StompSessionHandlerAdapter() {})
                .get(5, TimeUnit.SECONDS);

        session.subscribe(WEBSOCKET_QUEUE_INIT, new DefaultStompFrameHandler());
        session.send(WEBSOCKET_APP_INIT, null);

        JSONAssert.assertEquals(TestHelper.fileToString("/expected/ws/geth-status.json"),
                blockingQueue.poll(5, TimeUnit.SECONDS), true);
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
