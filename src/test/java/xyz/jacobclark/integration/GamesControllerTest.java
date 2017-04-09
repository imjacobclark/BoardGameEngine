package xyz.jacobclark.integration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.SECONDS;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class GamesControllerTest {
    static final String WEBSOCKET_URI = "ws://localhost:8080/ws";
    static final String WEBSOCKET_TOPIC_SUBSCRIPTION = "/topic/games";
    static final String WEBSOCKET_DESTINATION_ENDPOINT = "/app/games";

    BlockingQueue<String> blockingQueue;
    WebSocketStompClient stompClient;

    @Before
    public void setup() {
        blockingQueue = new LinkedBlockingDeque<>();

        SockJsClient webSocketClient = new SockJsClient(asList(new WebSocketTransport(new StandardWebSocketClient())));
        stompClient = new WebSocketStompClient(webSocketClient);
    }

    @Test
    public void successfulMoveCallToWebsocketEndpointReturnsAllCurrentPiecesOnBoard() throws Exception {
        StompSession session = stompClient
                .connect(WEBSOCKET_URI, new StompSessionHandlerAdapter() {
                })
                .get(1, SECONDS);

        session.subscribe(WEBSOCKET_TOPIC_SUBSCRIPTION, new MockStompFrameHandler());

        session.send(WEBSOCKET_DESTINATION_ENDPOINT, "{\"player\": \"BLACK\", \"column\": 0, \"row\": 0}".getBytes());
        session.send(WEBSOCKET_DESTINATION_ENDPOINT, "{\"player\": \"WHITE\", \"column\": 0, \"row\": 1}".getBytes());

        Assert.assertEquals("[{\"player\":\"WHITE\",\"column\":0,\"row\":1},{\"player\":\"BLACK\",\"column\":0,\"row\":0}]", blockingQueue.poll(1, SECONDS));
    }

    class MockStompFrameHandler implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return byte[].class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            blockingQueue.offer(new String((byte[]) o));
        }
    }
}