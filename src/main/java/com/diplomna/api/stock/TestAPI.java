package com.diplomna.api.stock;


public class TestAPI {
    static public void main(String []args){
        ParseStock stock = new ParseStock();
        stock.setStockBySymbol("TSLA");
        System.out.println(stock.getSymbol() + " " +stock.getRawCurrentPrice() + " " + stock.isMarketOpen()
        + " " + stock.getCurrency());
    }
}
