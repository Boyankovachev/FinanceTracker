package com.diplomna.api;


import com.diplomna.api.stock.ParseStock;
import com.mashape.unirest.http.exceptions.UnirestException;

public class TestAPI {
    static public void main(String []args){
        ParseStock stock = new ParseStock();
        try {
            stock.setStockBySymbol("TSLA");
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        System.out.println(stock.getSymbol() + " " +stock.getRawCurrentPrice() + " " + stock.isMarketOpen()
        + " " + stock.getCurrency() + " " + stock.getRecommendationKey() + " " + stock.getExchangeName()
        + " " + stock.getName() + " " + stock.getCurrencySymbol());
        System.out.println(stock.getDescription());

    }
}
