package com.example.websocket.websocketpoc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableScheduling
public class LiveStockPricePublisher {

    @Autowired
    private SimpMessagingTemplate simpleMessagingTemplate;
    @Autowired
    private RealTimeStockPriceFetcher realTimeStockPriceFetcher;

    @Scheduled(fixedRate = 1000)
    public void publishLivePrices() {
        List<StockResponse> liveNiftyStockPrices = realTimeStockPriceFetcher.fetchLiveNiftyStockPrices();
        simpleMessagingTemplate.convertAndSend("/topic/live-stock-price", liveNiftyStockPrices);
    }
}
