package com.diplomna.assets.sub;

public class PurchaseInfo {
    private double price;
    private String purchaseDate;
    private double quantity;

    public PurchaseInfo(){}
    public PurchaseInfo(double price, double quantity){
        this.price = price;
        this.quantity = quantity;
    }
    public PurchaseInfo(double price, String purchaseDate, double quantity){
        this.price = price;
        this.purchaseDate = purchaseDate;
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
