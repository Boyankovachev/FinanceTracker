package com.diplomna.assets.finished;

import com.diplomna.assets.sub.Asset;
import com.diplomna.assets.sub.PurchaseInfo;

public class PassiveResource extends Asset {
    /*
    protected String name;
    protected String description;
    protected double currentMarketPrice;
    protected String currency;
    protected String currencySymbol;
     */
    private PurchaseInfo purchaseInfo;

    public PassiveResource(){}
    public PassiveResource(String name){
        super(name);
    }
    public PassiveResource(String name, double currentMarketPrice){
        super(name, currentMarketPrice);
    }

    public void setPurchaseInfo(PurchaseInfo purchaseInfo) {
        this.purchaseInfo = purchaseInfo;
    }

    public PurchaseInfo getPurchaseInfo() {
        return purchaseInfo;
    }
}
