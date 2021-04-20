package com.diplomna.singleton;

import com.diplomna.assets.AssetManager;
import com.diplomna.assets.finished.Commodities;
import com.diplomna.assets.finished.Crypto;
import com.diplomna.assets.finished.Index;
import com.diplomna.assets.finished.Stock;
import com.diplomna.graph.GraphInfo;
import com.diplomna.graph.GraphInfoManager;

public class CurrentData {
    /*
        Singleton class.
        Holds data in RAM;
     */
    private AssetManager assetManager;

    private GraphInfoManager graphInfoManager = new GraphInfoManager();

    private CurrentData(){}

    private static class SingletonHelper{
        private static final CurrentData INSTANCE = new CurrentData();
    }

    public static CurrentData getInstance(){
        return SingletonHelper.INSTANCE;
    }

    //Getter and Setters
    public AssetManager getAssetManager() {
        return assetManager;
    }
    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public GraphInfoManager getGraphInfoManager() {
        return graphInfoManager;
    }
    public void setGraphInfoManager(GraphInfoManager graphInfoManager) {
        this.graphInfoManager = graphInfoManager;
    }

}
