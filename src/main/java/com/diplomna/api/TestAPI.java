package com.diplomna.api;


import com.diplomna.api.stock.ParseStock;

public class TestAPI {
    static public void main(String []args){
        ParseStock stock = new ParseStock();
        stock.setStockBySymbol("TSLA");

        System.out.println(stock.getSymbol() + " " +stock.getRawCurrentPrice() + " " + stock.isMarketOpen()
        + " " + stock.getCurrency() + " " + stock.getRecommendationKey() + " " + stock.getExchangeName()
        + " " + stock.getName() + " " + stock.getCurrencySymbol());
        System.out.println(stock.getDescription());

    }
}
