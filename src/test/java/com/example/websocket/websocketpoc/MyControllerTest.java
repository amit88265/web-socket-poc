package com.example.websocket.websocketpoc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MyControllerTest {
    @Value("${local.server.port}")
    private int port;
    private String URL;

    private static final String SEND = "/app/live/prices";
    private static final String SUBSCRIBE = "/stock/live-prices";
    private CompletableFuture<StockResponse> completableFuture;

    @Before
    public void setup() {
        URL = "ws://localhost:" + port + "/web-socket";
        completableFuture = new CompletableFuture<>();
    }


    @Test
    public void testCreateGameEndpoint() throws InterruptedException, ExecutionException, TimeoutException {

        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSession stompSession = stompClient.connect(URL, new StompSessionHandlerAdapter() {
        }).get(1, SECONDS);
        stompSession.subscribe(SUBSCRIBE, new Handler());
        stompSession.send(SEND, null);
        StockResponse stockResponse = completableFuture.get(10, SECONDS);
        System.out.println("stockResponse = " + stockResponse.toString());
        while (stompSession.isConnected()) {
            Thread.sleep(1000);
            stompSession.send(SEND, null);
        }
        Assert.assertNotNull(stockResponse);

    }


    private List<Transport> createTransportClient() {
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        return transports;
    }

    private class Handler implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return StockResponse.class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            completableFuture.complete((StockResponse) o);
            System.out.println("stompHeaders *************= " + o.toString());
        }
    }
}
