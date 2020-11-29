package com.diplomna.assets.sub;

import java.util.ArrayList;
import java.util.List;

public class ActiveAsset extends Asset {
    protected List<PurchaseInfo>purchaseInfo = new ArrayList<>();
    protected double quantityOwned;

    public ActiveAsset(){
        super();
    }
    public ActiveAsset(String name){
        super(name);
    }
    public ActiveAsset(String name, double currentMarketPrice){
        super(name, currentMarketPrice);
    }
    public ActiveAsset(String name, double currentMarketPrice, double quantityOwned){
        super(name, currentMarketPrice);
        this.quantityOwned = quantityOwned;
    }

    public void setQuantityOwned(double quantityOwned) {
        this.quantityOwned = quantityOwned;
    }
    public double getQuantityOwned(){
        return quantityOwned;
    }

    //add methods to handle pruchaseInfo
}
