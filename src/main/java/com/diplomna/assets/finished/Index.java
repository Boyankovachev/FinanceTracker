package com.diplomna.assets.finished;

import com.diplomna.assets.sub.StockBasedActiveAsset;

public class Index extends StockBasedActiveAsset {
    /*
    protected String name;
    protected String description;
    protected double currentMarketPrice;
    protected String currency;
    protected List<PurchaseInfo>purchaseInfo = new ArrayList<>();
    protected double quantityOwned;
    protected String symbol;
    protected String currencySymbol;
    protected double averagePurchasePrice;
    */
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

}
