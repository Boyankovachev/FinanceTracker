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

    //Copy constructor
    public Commodities(Commodities commodity){
        this.name = commodity.getName();
        this.currency = commodity.getCurrency();
        this.currencySymbol = commodity.getCurrencySymbol();
        this.description = commodity.getDescription();
        this.currentMarketPrice = commodity.getCurrentMarketPrice();
        this.exchangeName = commodity.getExchangeName();
    }

    //Copy factory
    public static Commodities newInstance(Commodities commodity){
        return new Commodities(commodity);
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getExchangeName(){
        return exchangeName;
    }

    public void printCommodity(){
        //Debugging purposes
        System.out.println(name);
        for(PurchaseInfo purchaseInfo: purchaseInfo){
            System.out.println("Purchase: price: " + purchaseInfo.getPrice() + "  quantity: " + purchaseInfo.getQuantity() );
        }
    }
}
