package com.diplomna.singleton;

import com.diplomna.assets.AssetManager;
import com.diplomna.assets.finished.Commodities;
import com.diplomna.assets.finished.Crypto;
import com.diplomna.assets.finished.Index;
import com.diplomna.assets.finished.Stock;

public class CurrentData {
    /*
        Singleton class.
        Holds data for current prices in RAM;
     */
    private AssetManager assetManager;

    private CurrentData(){}

    private static class SingletonHelper{
        private static final CurrentData INSTANCE = new CurrentData();
    }

    public static CurrentData getInstance(){
        return SingletonHelper.INSTANCE;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    //FOR TESTING PURPOSES ONLY!
    public void printAssetManager(){
        for(Stock stock: assetManager.getAllStocks()){
            stock.printStock();
        }
        for(Index index: assetManager.getAllIndex()){
            index.printIndex();
        }
        for(Crypto crypto: assetManager.getCrypto()){
            crypto.printCrypto();
        }
        for(Commodities commodities: assetManager.getCommodities()){
            commodities.printCommodity();
        }
    }
}
