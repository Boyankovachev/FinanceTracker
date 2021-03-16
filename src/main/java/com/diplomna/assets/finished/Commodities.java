package com.diplomna.assets.finished;

import com.diplomna.assets.sub.ActiveAsset;
import com.diplomna.assets.sub.PurchaseInfo;

public class Commodities extends ActiveAsset {
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
