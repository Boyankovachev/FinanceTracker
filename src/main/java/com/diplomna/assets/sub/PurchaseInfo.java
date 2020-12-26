package com.diplomna.assets.sub;

import com.diplomna.date.DatеManager;

public class PurchaseInfo {
    private double price;
    private DatеManager purchaseDate;
    private double quantity;

    // if a purchase has a symbol
    private String stockSymbol = null;

    public PurchaseInfo(){}
    public PurchaseInfo(double price, double quantity){
        this.price = price;
        this.quantity = quantity;
    }
    public PurchaseInfo(double price, DatеManager purchaseDate, double quantity){
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

    public DatеManager getPurchaseDate() {
        return purchaseDate;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPurchaseDate(DatеManager purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }
}
