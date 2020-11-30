package com.diplomna.assets;

import com.diplomna.assets.finished.*;
import com.diplomna.assets.sub.PurchaseInfo;
import com.diplomna.date.DatеManager;

public class TestAssets {
    static public void main(String []args){
        DatеManager date = new DatеManager();
        PurchaseInfo pokupka1 = new PurchaseInfo(144.6,date,15.53);
        PurchaseInfo pokupka2 = new PurchaseInfo(14,date,12.1);
        PurchaseInfo pokupka3 = new PurchaseInfo(251,date,13);
        PurchaseInfo pokupka4 = new PurchaseInfo(2.5,date,2.6);
        Stock tesla = new Stock();
        tesla.addPurchase(pokupka1);
        tesla.addPurchase(pokupka2);
        tesla.addPurchase(pokupka3);
        tesla.addPurchase(pokupka4);
        //System.out.println(tesla.getPurchaseByDate(new DatеManager()).getPrice());
        //System.out.println(tesla.getPurchaseByIndex(3).getPrice());
        //System.out.println(tesla.getPurchaseByPrice(251).getQuantity());
        System.out.println(tesla.getPurchaseByQuantity(2.6).getPrice());
    }
}
