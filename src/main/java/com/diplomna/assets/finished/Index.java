package com.diplomna.assets.finished;

import com.diplomna.assets.sub.PurchaseInfo;
import com.diplomna.assets.sub.StockBasedActiveAsset;

public class Index extends StockBasedActiveAsset {
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
    private boolean isMarketOpen;
    private String exchangeName;

    public Index(){
        super();
    }
    public Index(String name){
        super(name);
    }
    public Index(String name, double currentMarketPrice){
        super(name,currentMarketPrice);
    }
    public Index(String name, double currentMarketPrice, double quantityOwned){
        super(name,currentMarketPrice,quantityOwned);
    }
    public Index(String name, double currentMarketPrice, double quantityOwned, String symbol){
        super(name,currentMarketPrice,quantityOwned,symbol);
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public void setMarketOpen(boolean marketOpen) {
        isMarketOpen = marketOpen;
    }

    public boolean isMarketOpen() {
        return isMarketOpen;
    }

    public String getExchangeName(){
        return exchangeName;
    }

    public void printIndex(){
        /*
        Prints index to console.
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
        System.out.println("isMarketOpen: " + isMarketOpen);
        System.out.println("exchangeName: " + exchangeName);
        System.out.println();
    }

}
