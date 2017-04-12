package xyz.jacobclark.integration.websockets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import xyz.jacobclark.controllers.GameController;
import xyz.jacobclark.games.impl.Gomoku;

import java.lang.reflect.Type;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class GameControllerTest {
    static final String WEBSOCKET_URI = "ws://localhost:8080/ws";
    static final String WEBSOCKET_TOPIC_SUBSCRIPTION = "/topic/games/pieces";

    private BlockingQueue<String> blockingQueue;
    private WebSocketStompClient stompClient;

    @Before
    public void setup() {
        blockingQueue = new LinkedBlockingDeque<>();

        SockJsClient webSocketClient = new SockJsClient(asList(new WebSocketTransport(new StandardWebSocketClient())));
        stompClient = new WebSocketStompClient(webSocketClient);
    }

    @Test
    public void successfulPlacePieceCallToWebsocketPiecesEndpointReturnsAllCurrentPiecesOnBoard() throws Exception {
        ResponseEntity<Gomoku> response = new RestTemplate().postForEntity("http://localhost:8080/games", null, Gomoku.class);

        StompSession session = stompClient
                .connect(WEBSOCKET_URI, new StompSessionHandlerAdapter() {
                })
                .get(1, SECONDS);

        session.subscribe(WEBSOCKET_TOPIC_SUBSCRIPTION, new MockStompFrameHandler());

        session.send("/app/games/" + response.getBody().getId() + "/pieces", "{\"pebbleType\": \"BLACK\", \"column\": 0, \"row\": 0}".getBytes());
        session.send("/app/games/" + response.getBody().getId() + "/pieces", "{\"pebbleType\": \"WHITE\", \"column\": 0, \"row\": 1}".getBytes());

        String message = blockingQueue.poll(1, SECONDS);

        Assert.assertTrue(message.contains("{\"pebbleType\":\"WHITE\",\"column\":0,\"row\":1}"));
        Assert.assertTrue(message.contains("{\"pebbleType\":\"BLACK\",\"column\":0,\"row\":0}"));
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