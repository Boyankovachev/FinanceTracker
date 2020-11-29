package com.diplomna.assets.sub;

public class Asset {
    protected String name;
    protected String description;
    protected double currentMarketPrice;
    protected String currency;

    public Asset(){}
    public Asset(String name){
        this.name = name;
    }
    public Asset(String name, double currentMarketPrice){
        this.name = name;
        this.currentMarketPrice = currentMarketPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCurrentMarketPrice() {
        return currentMarketPrice;
    }

    public void setCurrentMarketPrice(double currentMarketPrice) {
        this.currentMarketPrice = currentMarketPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}