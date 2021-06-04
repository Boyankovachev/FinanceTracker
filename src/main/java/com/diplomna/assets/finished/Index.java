package com.diplomna.assets.finished;

import com.diplomna.assets.sub.PurchaseInfo;
import com.diplomna.assets.sub.StockBasedActiveAsset;

public class Index extends StockBasedActiveAsset {
    private boolean isMarketOpen;
    private String exchangeName;

    public Index(){
        super();
    }
    public Index(String name){
        super(name);
    }
    public Index(String name, double currentMarketPrice){
        super(name,currentMarketPrice);
    }
    public Index(String name, double currentMarketPrice, double quantityOwned){
        super(name,currentMarketPrice,quantityOwned);
    }
    public Index(String name, double currentMarketPrice, double quantityOwned, String symbol){
        super(name,currentMarketPrice,quantityOwned,symbol);
    }

    //Copy constructor
    public Index(Index index){
        this.name = index.getName();
        this.currency = index.getCurrency();
        this.currencySymbol = index.getCurrencySymbol();
        this.description = index.getDescription();
        this.currentMarketPrice = index.getCurrentMarketPrice();
        this.symbol = index.getSymbol();
        this.exchangeName = index.getExchangeName();
        this.isMarketOpen = index.isMarketOpen();
    }

    //Copy factory
    public static Index newInstance(Index index){
        return new Index(index);
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public void setMarketOpen(boolean marketOpen) {
        isMarketOpen = marketOpen;
    }

    public boolean isMarketOpen() {
        return isMarketOpen;
    }

    public String getExchangeName(){
        return exchangeName;
    }

    public void printIndex(){
        //Debugging purposes
        System.out.println(name);
        System.out.println(symbol);
        for(PurchaseInfo purchaseInfo: purchaseInfo){
            System.out.println("Purchase: price: " + purchaseInfo.getPrice() + "  quantity: " + purchaseInfo.getQuantity() );
        }
    }

}
