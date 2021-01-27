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

    public void calculatePercentChange(){
        this.percentChange = ((this.currentMarketPrice - this.purchaseInfo.getPrice()) * 100) / this.purchaseInfo.getPrice();
    }

    public void printPassiveResource(){
        /*
        Prints resource info to console.
        Testing purposes!
         */
        System.out.println("name: " + name);
        System.out.println("purchase price: " + purchaseInfo.getPrice());
        if(purchaseInfo.getPurchaseDate()!=null) {
            System.out.println("purchase date: " + purchaseInfo.getPurchaseDate().getDateAsString());
        }
        else System.out.println("purchase date: null");
        System.out.println("description: " + description);
        System.out.println("current price: " + currentMarketPrice);
        System.out.println("currency: " + currency);
        System.out.println("currencySymbol: " + currencySymbol);
        System.out.println();
    }
}
