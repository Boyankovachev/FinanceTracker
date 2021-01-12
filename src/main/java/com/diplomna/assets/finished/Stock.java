package com.diplomna.assets.finished;

import com.diplomna.assets.sub.PurchaseInfo;
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
    protected String currencySymbol;
    protected double averagePurchasePrice;
     */
    private boolean isMarketOpen;
    private String exchangeName;
    private String recommendationKey;

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

    public Stock(String symbol, String name, String currency, String currencySymbol, String exchangeName, String description){
        this.symbol = symbol;
        this.name = name;
        this.currency = currency;
        this.currencySymbol = currencySymbol;
        this.exchangeName = exchangeName;
        this.description = description;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public void setMarketOpen(boolean marketOpen) {
        isMarketOpen = marketOpen;
    }

    public void setRecommendationKey(String recommendationKey) {
        this.recommendationKey = recommendationKey;
    }

    public boolean isMarketOpen() {
        return isMarketOpen;
    }
    public String getExchangeName(){
        return exchangeName;
    }
    public String getRecommendationKey(){
        return recommendationKey;
    }

    public void printStock(){
        /*
        Prints stock to console.
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
        System.out.println("recommendationKey: " + recommendationKey);
    }

}
