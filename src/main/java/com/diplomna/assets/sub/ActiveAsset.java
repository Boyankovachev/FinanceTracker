package com.diplomna.assets.sub;

import java.util.ArrayList;
import java.util.List;
import com.diplomna.date.DatеManager;

public class ActiveAsset extends Asset {
    protected List<PurchaseInfo>purchaseInfo = new ArrayList<>();
    protected double quantityOwned;
    protected double averagePurchasePrice;

    public ActiveAsset(){
        super();
    }
    public ActiveAsset(String name){
        super(name);
    }
    public ActiveAsset(String name, double currentMarketPrice){
        super(name, currentMarketPrice);
    }
    public ActiveAsset(String name, double currentMarketPrice, double quantityOwned){
        super(name, currentMarketPrice);
        this.quantityOwned = quantityOwned;
    }

    public void setQuantityOwned(double quantityOwned) {
        this.quantityOwned = quantityOwned;
    }
    public double getQuantityOwned(){
        return quantityOwned;
    }

    public void calculateQuantityOwned(){
        //calculates quantity owned of specific investment
        if(!areTherePurchases()){
            quantityOwned = 0;
        }
        else {
            quantityOwned = 0;
            for(PurchaseInfo purchaseInfo: purchaseInfo){
                quantityOwned = quantityOwned + purchaseInfo.getQuantity();
            }
        }
    }

    public void calculateAveragePurchasePrice(){
        //calculate average purchase price
        //return 0 if no purchases have been made
        if(!areTherePurchases()){
            averagePurchasePrice = 0;
        }
        else if(purchaseInfo.size() == 1){
            averagePurchasePrice = purchaseInfo.get(0).getPrice();
        }
        else {
            double temp = 0;
            for (PurchaseInfo info : purchaseInfo) {
                temp = temp + (info.getQuantity() * info.getPrice());
            }
            averagePurchasePrice = temp / quantityOwned;

        }
    }

    public List<PurchaseInfo> getAllPurchases(){
        return purchaseInfo;
    }

    public PurchaseInfo getLastPurchase(){
        try {
            return purchaseInfo.get(purchaseInfo.size() - 1);
        }
        catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return null;
    }
    public PurchaseInfo getFirstPurchase(){
        try {
            return purchaseInfo.get(0);
        }
        catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return null;
    }
    public PurchaseInfo getPurchaseByIndex(int index){
        //returns purchase by the specific index of the list
        try {
            return purchaseInfo.get(index);
        }
        catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return null;
    }
    public PurchaseInfo getPurchaseByDate(DatеManager date){
        int i;
        for(i=0; i<purchaseInfo.size(); i++){
            if(purchaseInfo.get(i).getPurchaseDate().getDateAsString().equals(date.getDateAsString())){
                return purchaseInfo.get(i);
            }
        }
        return null;
    }
    public PurchaseInfo getPurchaseByPrice(double price){
        //returns first purchase with specific price
        int i;
        for(i=0; i<purchaseInfo.size(); i++){
            if(purchaseInfo.get(i).getPrice() == price){
                return purchaseInfo.get(i);
            }
        }
        return null;
    }
    public PurchaseInfo getPurchaseByQuantity(double quantity){
        //returns first purchase with specific quantity
        int i;
        for(i=0; i<purchaseInfo.size(); i++){
            if(purchaseInfo.get(i).getQuantity() == quantity){
                return purchaseInfo.get(i);
            }
        }
        return null;
    }

    public void addPurchase(PurchaseInfo newPurchase){
        purchaseInfo.add(newPurchase);
    }
    public void addPurchases(List<PurchaseInfo> newPurchases){
        purchaseInfo.addAll(newPurchases);
    }

    public boolean areTherePurchases(){
        //returns true if purchaseInfo list is empty
        return !purchaseInfo.isEmpty();
    }

    public double getAveragePurchasePrice() {
        return averagePurchasePrice;
    }

    public void setAveragePurchasePrice(double averagePurchasePrice) {
        this.averagePurchasePrice = averagePurchasePrice;
    }

     public void calculatePercentChange(){
        //calculate return on investment
        this.percentChange = ((this.currentMarketPrice - this.averagePurchasePrice) * 100) / this.averagePurchasePrice;
     }

}
