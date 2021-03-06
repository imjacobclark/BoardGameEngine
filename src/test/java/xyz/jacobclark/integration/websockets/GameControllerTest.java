package xyz.jacobclark.integration.websockets;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import xyz.jacobclark.games.impl.Gomoku;
import xyz.jacobclark.models.Player;

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
        ResponseEntity<Player> joinGameResponse = new RestTemplate().postForEntity("http://localhost:8080/games/" + response.getBody().getUuid() + "/players", null, Player.class);

        StompSession session = stompClient
                .connect(WEBSOCKET_URI, new StompSessionHandlerAdapter() {
                })
                .get(1, SECONDS);

        session.subscribe(WEBSOCKET_TOPIC_SUBSCRIPTION, new MockStompFrameHandler());

        String blackMovePayload = "{\"playerUuid\": \"" + response.getBody().getPlayers().get(0).getUuid().toString() + "\", \"column\": 0, \"row\": 0}";
        String whiteMovePayload = "{\"playerUuid\": \"" + joinGameResponse.getBody().getUuid() + "\", \"column\": 0, \"row\": 1}";

        session.send("/app/games/" + response.getBody().getUuid() + "/pieces", blackMovePayload.getBytes());

        Thread.sleep(100);

        session.send("/app/games/" + response.getBody().getUuid() + "/pieces", whiteMovePayload.getBytes());

        Thread.sleep(100);

        blockingQueue.poll();
        String message = blockingQueue.poll();

        Assert.assertTrue(message.contains("{\"pebbleType\":\"BLACK\",\"column\":0,\"row\":0}"));
        Assert.assertTrue(message.contains("{\"pebbleType\":\"WHITE\",\"column\":0,\"row\":1}"));
        Assert.assertTrue(message.contains("\"title\":\"GOMOKU\""));
        Assert.assertTrue(message.contains("\"board\":{\"pieces\":"));
        Assert.assertTrue(message.contains("{\"players\":[{\""));
    }

    @Test
    public void gameWin_PutsMessageOnWinningQueue_WithExpectedBody() throws Exception {
        ResponseEntity<Gomoku> response = new RestTemplate().postForEntity("http://localhost:8080/games", null, Gomoku.class);
        ResponseEntity<Player> joinGameResponse = new RestTemplate().postForEntity("http://localhost:8080/games/" + response.getBody().getUuid() + "/players", null, Player.class);

        StompSession session = stompClient
                .connect(WEBSOCKET_URI, new StompSessionHandlerAdapter() {
                })
                .get(1, SECONDS);

        session.subscribe(WEBSOCKET_TOPIC_SUBSCRIPTION, new MockStompFrameHandler());
        session.subscribe("/topic/games/events/win", new MockStompFrameHandler());

        String blackMovePayload = "{\"playerUuid\": \"" + response.getBody().getPlayers().get(0).getUuid().toString() + "\", \"column\": 0, \"row\": 0}";
        String blackMovePayload1 = "{\"playerUuid\": \"" + response.getBody().getPlayers().get(0).getUuid().toString() + "\", \"column\": 1, \"row\": 0}";
        String blackMovePayload2 = "{\"playerUuid\": \"" + response.getBody().getPlayers().get(0).getUuid().toString() + "\", \"column\": 2, \"row\": 0}";
        String blackMovePayload3 = "{\"playerUuid\": \"" + response.getBody().getPlayers().get(0).getUuid().toString() + "\", \"column\": 3, \"row\": 0}";
        String blackMovePayload4 = "{\"playerUuid\": \"" + response.getBody().getPlayers().get(0).getUuid().toString() + "\", \"column\": 4, \"row\": 0}";

        String whiteMovePayload = "{\"playerUuid\": \"" + joinGameResponse.getBody().getUuid() + "\", \"column\": 0, \"row\": 1}";
        String whiteMovePayload1 = "{\"playerUuid\": \"" + joinGameResponse.getBody().getUuid() + "\", \"column\": 0, \"row\": 2}";
        String whiteMovePayload2 = "{\"playerUuid\": \"" + joinGameResponse.getBody().getUuid() + "\", \"column\": 0, \"row\": 3}";
        String whiteMovePayload3 = "{\"playerUuid\": \"" + joinGameResponse.getBody().getUuid() + "\", \"column\": 8, \"row\": 4}";
        String whiteMovePayload4 = "{\"playerUuid\": \"" + joinGameResponse.getBody().getUuid() + "\", \"column\": 0, \"row\": 9}";

        session.send("/app/games/" + response.getBody().getUuid() + "/pieces", blackMovePayload.getBytes());

        Thread.sleep(100);

        session.send("/app/games/" + response.getBody().getUuid() + "/pieces", whiteMovePayload.getBytes());

        Thread.sleep(100);

        session.send("/app/games/" + response.getBody().getUuid() + "/pieces", blackMovePayload1.getBytes());

        Thread.sleep(100);

        session.send("/app/games/" + response.getBody().getUuid() + "/pieces", whiteMovePayload1.getBytes());

        Thread.sleep(100);

        session.send("/app/games/" + response.getBody().getUuid() + "/pieces", blackMovePayload2.getBytes());

        Thread.sleep(100);

        session.send("/app/games/" + response.getBody().getUuid() + "/pieces", whiteMovePayload2.getBytes());

        Thread.sleep(100);

        session.send("/app/games/" + response.getBody().getUuid() + "/pieces", blackMovePayload3.getBytes());

        Thread.sleep(100);

        session.send("/app/games/" + response.getBody().getUuid() + "/pieces", whiteMovePayload3.getBytes());

        Thread.sleep(100);

        session.send("/app/games/" + response.getBody().getUuid() + "/pieces", blackMovePayload4.getBytes());

        Thread.sleep(500);

        blockingQueue.poll();
        blockingQueue.poll();
        blockingQueue.poll();
        blockingQueue.poll();
        blockingQueue.poll();
        blockingQueue.poll();
        blockingQueue.poll();
        blockingQueue.poll();

        String message = blockingQueue.poll();

        Assert.assertTrue(message.contains("\"title\":\"GOMOKU\""));
        Assert.assertTrue(message.contains("\"board\":{\"pieces\":"));
        Assert.assertTrue(message.contains("{\"players\":[{\""));
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