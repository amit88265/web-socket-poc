package com.example.websocket.websocketpoc;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.websocket.websocketpoc.Constant.HDFC;
import static com.example.websocket.websocketpoc.Constant.INFY;
import static com.example.websocket.websocketpoc.Constant.ITC;
import static com.example.websocket.websocketpoc.Constant.RIL;
import static com.example.websocket.websocketpoc.Constant.TCS;

@Service
public class StockPriceRepo {
    private final Map<String, List<Double>> stockPrices = new HashMap<>();

    @PostConstruct
    public void init() {
        stockPrices.put(RIL, Arrays.asList(1500.50, 1600.00, 1200.00, 1800.76, 2000.00));
        stockPrices.put(ITC, Arrays.asList(150.70, 160.00, 120.90, 180.76, 250.00));
        stockPrices.put(HDFC, Arrays.asList(1000.20, 1100.00, 800.00, 1200.36, 1050.10));
        stockPrices.put(TCS, Arrays.asList(2100.50, 1600.40, 2200.35, 3800.76, 3200.50));
        stockPrices.put(INFY, Arrays.asList(1100.50, 1250.00, 790.90, 1320.24, 1400.00));
    }

    public Map<String, List<Double>> getStockPrices() {
        return this.stockPrices;
    }
}
