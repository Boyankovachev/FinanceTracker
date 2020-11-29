package com.diplomna.assets.finished;

import com.diplomna.assets.sub.StockBasedActiveAsset;

public class Stock extends StockBasedActiveAsset {
    /*
    protected String name;
    protected String description;
    protected double currentMarketPrice;
    protected String currency;
    protected List<PurchaseInfo>purchaseInfo = new ArrayList<>();
    protected double quantityOwned;
    protected String symbol;
     */
    public String da_ima_neshto_unikalno_tova_e_akciq = "TESLA";

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
}
