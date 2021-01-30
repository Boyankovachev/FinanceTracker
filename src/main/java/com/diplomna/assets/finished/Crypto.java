package com.diplomna.assets.finished;

import com.diplomna.assets.sub.PurchaseInfo;
import com.diplomna.assets.sub.StockBasedActiveAsset;

public class Crypto extends StockBasedActiveAsset {
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

    public void printCrypto(){
        /*
        Prints crypto to console.
        Testing purposes!
         */
        System.out.println("name:" + name);
        System.out.println("description:" + description);
        System.out.println("currentMarketPrice:" + currentMarketPrice);
        for(PurchaseInfo purchaseInfo: purchaseInfo){
            System.out.println("Price: " + purchaseInfo.getPrice() + " Quantity: " + purchaseInfo.getQuantity() + " Date: " + purchaseInfo.getPurchaseDate().getDateAsString());
        }
        System.out.println("quantityOwned: " + quantityOwned);
        System.out.println("symbol: " + symbol);
        System.out.println("currencySymbol: " + currencySymbol);
        System.out.println("averagePurchasePrice: " + averagePurchasePrice);
        System.out.println();
    }

}
