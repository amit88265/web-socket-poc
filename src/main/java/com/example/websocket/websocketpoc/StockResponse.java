package com.example.websocket.websocketpoc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockResponse {
    private String stock;
    private Double price;

    public static StockResponse from(String stock, Double livePrice) {
        return new StockResponse(stock, livePrice);
    }

    @Override
    public String toString() {
        return "StockResponse{" +
                "stock='" + stock + '\'' +
                ", prices=" + price +
                '}';
    }
}
