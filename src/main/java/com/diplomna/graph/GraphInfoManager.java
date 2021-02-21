package com.diplomna.graph;

import java.util.ArrayList;
import java.util.List;

public class GraphInfoManager {
    private List<GraphInfoHolder> stockInfoList = new ArrayList<>();
    private List<GraphInfoHolder> indexInfoList = new ArrayList<>();
    private List<GraphInfoHolder> cryptoInfoList = new ArrayList<>();


    public GraphInfoManager(){}


    public boolean isStockPresent(String symbol){
        //searches for stock in list
        //returns true if present
        for(GraphInfoHolder holder: stockInfoList){
            if(holder.getAssetSymbol().equals(symbol)){
                return true;
            }
        }
        return false;
    }
    public boolean isIndexPresent(String symbol){
        //searches for index in list
        //returns true if present
        for(GraphInfoHolder holder: indexInfoList){
            if(holder.getAssetSymbol().equals(symbol)){
                return true;
            }
        }
        return false;
    }
    public boolean isCryptoPresent(String symbol){
        //searches for crypto in list
        //returns true if present
        for(GraphInfoHolder holder: cryptoInfoList){
            if(holder.getAssetSymbol().equals(symbol)){
                return true;
            }
        }
        return false;
    }

    public GraphInfoHolder getStockInfo(String symbol){
        //returns GraphInfoHolder for stock
        //return null if missing or empty
        for(GraphInfoHolder holder: stockInfoList){
            if(holder.getAssetSymbol().equals(symbol)){
                return holder;
            }
        }
        return null;
    }

    public GraphInfoHolder getIndexInfo(String symbol){
        //returns GraphInfoHolder for stock
        //return null if empty
        for(GraphInfoHolder holder: indexInfoList){
            if(holder.getAssetSymbol().equals(symbol)){
                return holder;
            }
        }
        return null;
    }

    public GraphInfoHolder getCryptoInfo(String symbol){
        //returns GraphInfoHolder for stock
        //return null if empty
        for(GraphInfoHolder holder: cryptoInfoList){
            if(holder.getAssetSymbol().equals(symbol)){
                return holder;
            }
        }
        return null;
    }


    //Getters and Setters below
    public List<GraphInfoHolder> getCryptoInfoList() {
        if(cryptoInfoList == null){
            return null;
        }
        return cryptoInfoList;
    }

    public List<GraphInfoHolder> getIndexInfoList() {
        if(cryptoInfoList == null){
            return null;
        }
        return indexInfoList;
    }

    public List<GraphInfoHolder> getStockInfoList() {
        if(cryptoInfoList == null){
            return null;
        }
        return stockInfoList;
    }

    public void setCryptoInfoList(List<GraphInfoHolder> cryptoInfoList) {
        this.cryptoInfoList = cryptoInfoList;
    }

    public void setIndexInfoList(List<GraphInfoHolder> indexInfoList) {
        this.indexInfoList = indexInfoList;
    }

    public void setStockInfoList(List<GraphInfoHolder> stockInfoList) {
        this.stockInfoList = stockInfoList;
    }
}
