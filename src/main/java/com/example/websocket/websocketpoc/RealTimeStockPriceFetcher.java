package com.example.websocket.websocketpoc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RealTimeStockPriceFetcher {

    @Autowired
    private StockPriceRepo stockPriceRepo;

    public List<StockResponse> fetchLiveNiftyStockPrices() {
        return stockPriceRepo.getStockPrices()
                .entrySet()
                .stream()
                .map(entry -> liveStockPriceMapper.apply(entry))
                .collect(Collectors.toList());
    }

    private static Double fetchLivePrice(List<Double> stockPrices) {
        Random random = new Random();
        return stockPrices.get(random.nextInt(5));
    }

    Function<Map.Entry<String, List<Double>>, StockResponse> liveStockPriceMapper =
            entry -> StockResponse.from(entry.getKey(), fetchLivePrice(entry.getValue()));
}
