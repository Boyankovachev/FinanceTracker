package com.diplomna.assets.finished;

import com.diplomna.assets.sub.PurchaseInfo;
import com.diplomna.assets.sub.StockBasedActiveAsset;

public class Crypto extends StockBasedActiveAsset {

    public Crypto(){
        super();
    }
    public Crypto(String name){
        super(name);
    }
    public Crypto(String name, double currentMarketPrice){
        super(name,currentMarketPrice);
    }
    public Crypto(String name, double currentMarketPrice, double quantityOwned){
        super(name,currentMarketPrice,quantityOwned);
    }
    public Crypto(String name, double currentMarketPrice, double quantityOwned, String symbol){
        super(name,currentMarketPrice,quantityOwned,symbol);
    }

    //Copy constructor
    public Crypto(Crypto crypto){
        this.name = crypto.getName();
        this.currency = crypto.getCurrency();
        this.currencySymbol = crypto.getCurrencySymbol();
        this.description = crypto.getDescription();
        this.currentMarketPrice = crypto.getCurrentMarketPrice();
        this.symbol = crypto.getSymbol();
    }

    //Copy factory
    public static Crypto newInstance(Crypto crypto){
        return new Crypto(crypto);
    }

    public void printCrypto(){
        //Debugging purposes
        System.out.println(name);
        System.out.println(symbol);
        for(PurchaseInfo purchaseInfo: purchaseInfo){
            System.out.println("Purchase: price: " + purchaseInfo.getPrice() + "  quantity: " + purchaseInfo.getQuantity() );
        }
    }

}
