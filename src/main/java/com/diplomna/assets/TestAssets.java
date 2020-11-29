package com.diplomna.assets;

import com.diplomna.assets.finished.*;

public class TestAssets {
    static public void main(String []args){
        Stock tesla = new Stock();
        tesla.setName("Tesla");
        tesla.setSymbol("TSLA");
        tesla.setCurrentMarketPrice(585.65);

        PassiveResource house = new PassiveResource();
        house.setCurrentMarketPrice(100000);
        house.setName("Rezidenciq sekvoqa");

        IndexFund snp500 = new IndexFund();
        snp500.setName("S&P500");
        snp500.setCurrentMarketPrice(3300);

        Crypto bitcoin = new Crypto();
        bitcoin.setName("Bitcoin");
        bitcoin.setCurrentMarketPrice(3300);

        Commodities gold = new Commodities("gold");
        gold.setCurrentMarketPrice(34);

        System.out.println(tesla.getName() + " " + tesla.getSymbol() + " " + tesla.getCurrentMarketPrice());
        System.out.println(house.getName() + " " + house.getCurrentMarketPrice());
        System.out.println(snp500.getName() + " " + snp500.getCurrentMarketPrice());
        System.out.println(bitcoin.getName() + " " + bitcoin.getCurrentMarketPrice());
    }
}
