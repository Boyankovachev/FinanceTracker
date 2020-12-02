package com.diplomna.assets.finished;

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
}
