package com.diplomna.assets;

import com.diplomna.assets.finished.*;

import java.util.ArrayList;
import java.util.List;

public class AssetManager {
    private List<Stock> stocks = new ArrayList<>();;
    private List<PassiveResource> passiveResources;
    private List<Index> indexFunds;
    private List<Commodities> commodities;
    private List<Crypto> cryptoCurrencies;

    public AssetManager(){}

    public List<Stock> getAllStocks(){
        return stocks;
    }
    // getStockBy....
    // getStockBy....
    public void addStock(Stock stock){
        stocks.add(stock);
    }
    public void addStocks(List<Stock> newStocks){
        stocks.addAll(newStocks);
    }
}
