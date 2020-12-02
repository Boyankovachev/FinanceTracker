package com.diplomna.assets;

import com.diplomna.assets.finished.Stock;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StockTest {

    private Stock testStock = new Stock();
    @Test
    public void testGettersAndSetters(){
        String name = "Tesla";
        String description = "Some description";
        double current_price = 4456.5;
        String currency = "USD";
        double quantityOwned = 5.435;
        String symbol = "TSLA";
        String currencySymbol = "$";
        String exchangeName = "NASDAQ";
        boolean isMarketOpen = false;
        String recommendationKey = "BUY";
        testStock.setName(name);
        testStock.setDescription(description);
        testStock.setCurrentMarketPrice(current_price);
        testStock.setCurrency(currency);
        testStock.setQuantityOwned(quantityOwned);
        testStock.setSymbol(symbol);
        testStock.setCurrencySymbol(currencySymbol);
        testStock.setMarketOpen(isMarketOpen);
        testStock.setExchangeName(exchangeName);
        testStock.setRecommendationKey(recommendationKey);
        assertEquals(name, testStock.getName());
        assertEquals(description, testStock.getDescription());
        assertEquals(current_price, testStock.getCurrentMarketPrice());
        assertEquals(currency, testStock.getCurrency());
        assertEquals(quantityOwned, testStock.getQuantityOwned());
        assertEquals(symbol, testStock.getSymbol());
        assertEquals(currencySymbol, testStock.getCurrencySymbol());
        assertEquals(isMarketOpen, testStock.isMarketOpen());
        assertEquals(exchangeName, testStock.getExchangeName());
        assertEquals(recommendationKey, testStock.getRecommendationKey());
    }
}
