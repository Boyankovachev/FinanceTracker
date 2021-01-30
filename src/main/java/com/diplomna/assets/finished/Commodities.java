package com.diplomna.assets.finished;

import com.diplomna.assets.sub.ActiveAsset;
import com.diplomna.assets.sub.PurchaseInfo;

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

    public void printCommodity(){
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
        System.out.println("currencySymbol: " + currencySymbol);
        System.out.println("averagePurchasePrice: " + averagePurchasePrice);
        System.out.println("exchangeName: " + exchangeName);
        System.out.println();
    }
}
