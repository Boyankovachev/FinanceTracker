package com.diplomna.assets.finished;

import com.diplomna.assets.sub.PurchaseInfo;
import com.diplomna.assets.sub.StockBasedActiveAsset;

public class Crypto extends StockBasedActiveAsset {

    public Crypto(){
        super();
    }
    public Crypto(String name){
        super(name);
    }
    public Crypto(String name, double currentMarketPrice){
        super(name,currentMarketPrice);
    }
    public Crypto(String name, double currentMarketPrice, double quantityOwned){
        super(name,currentMarketPrice,quantityOwned);
    }
    public Crypto(String name, double currentMarketPrice, double quantityOwned, String symbol){
        super(name,currentMarketPrice,quantityOwned,symbol);
    }

}
