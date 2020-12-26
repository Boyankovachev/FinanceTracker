package com.diplomna.assets.finished;

import com.diplomna.assets.sub.ActiveAsset;

public class Commodities extends ActiveAsset {
    /*
    protected String name;
    protected String description;
    protected double currentMarketPrice;
    protected String currency;
    protected String currencySymbol;
    protected List<PurchaseInfo>purchaseInfo = new ArrayList<>();
    protected double quantityOwned;
    protected double averagePurchasePrice;
     */
    private String exchangeName;

    public Commodities(){
        super();
    }
    public Commodities(String name){
        super(name);
    }
    public Commodities(String name, double currentMarketPrice){
        super(name, currentMarketPrice);
    }
    public Commodities(String name, double currentMarketPrice, double quantityOwned){
        super(name, currentMarketPrice, quantityOwned);
    }
    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getExchangeName(){
        return exchangeName;
    }
}
