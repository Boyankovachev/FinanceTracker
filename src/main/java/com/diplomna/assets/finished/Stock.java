package com.diplomna.assets.finished;

import com.diplomna.assets.sub.PurchaseInfo;
import com.diplomna.assets.sub.StockBasedActiveAsset;

public class Stock extends StockBasedActiveAsset {
    private boolean isMarketOpen;
    private String exchangeName;
    private String recommendationKey;

    public Stock(){
        super();
    }
    public Stock(String name){
        super(name);
    }
    public Stock(String name, double currentMarketPrice){
        super(name,currentMarketPrice);
    }
    public Stock(String name, double currentMarketPrice, double quantityOwned){
        super(name,currentMarketPrice,quantityOwned);
    }
    public Stock(String name, double currentMarketPrice, double quantityOwned, String symbol){
        super(name,currentMarketPrice,quantityOwned,symbol);
    }

    public Stock(String symbol, String name, String currency, String currencySymbol, String exchangeName, String description){
        this.symbol = symbol;
        this.name = name;
        this.currency = currency;
        this.currencySymbol = currencySymbol;
        this.exchangeName = exchangeName;
        this.description = description;
    }

    //Copy constructor
    public Stock(Stock stock){
        this.name = stock.getName();
        this.currency = stock.getCurrency();
        this.currencySymbol = stock.getCurrencySymbol();
        this.description = stock.getDescription();
        this.currentMarketPrice = stock.getCurrentMarketPrice();
        this.symbol = stock.getSymbol();
        this.exchangeName = stock.getExchangeName();
        this.isMarketOpen = stock.isMarketOpen();
        this.recommendationKey = stock.getRecommendationKey();
    }

    //Copy factory
    public static Stock newInstance(Stock stock){
        return new Stock(stock);
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public void setMarketOpen(boolean marketOpen) {
        isMarketOpen = marketOpen;
    }

    public void setRecommendationKey(String recommendationKey) {
        this.recommendationKey = recommendationKey;
    }

    public boolean isMarketOpen() {
        return isMarketOpen;
    }
    public String getExchangeName(){
        return exchangeName;
    }
    public String getRecommendationKey(){
        return recommendationKey;
    }

    public void printStock(){
        //Debugging purposes
        System.out.println(name);
        System.out.println(symbol);
        for(PurchaseInfo purchaseInfo: purchaseInfo){
            System.out.println("Purchase: price: " + purchaseInfo.getPrice() + "  quantity: " + purchaseInfo.getQuantity() );
        }
    }

}
