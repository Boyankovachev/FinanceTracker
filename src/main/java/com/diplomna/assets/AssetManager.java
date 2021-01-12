package com.diplomna.assets;

import com.diplomna.assets.finished.*;
import com.diplomna.assets.sub.PurchaseInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class AssetManager {
    private List<Stock> stocks = new ArrayList<>();;
    private List<PassiveResource> passiveResources = new ArrayList<>();
    private List<Index> indexFunds = new ArrayList<>();
    private List<Commodities> commodities = new ArrayList<>();
    private List<Crypto> cryptoCurrencies = new ArrayList<>();

    public AssetManager(){}

    public List<Stock> getAllStocks(){
        return stocks;
    }
    public void addStock(Stock stock){
        stocks.add(stock);
    }
    public void addStocks(List<Stock> newStocks){
        stocks.addAll(newStocks);
    }

    public List<PassiveResource> getAllPassiveResources(){
        return passiveResources;
    }
    public void addPassiveResource(PassiveResource passiveResource){
        passiveResources.add(passiveResource);
    }
    public void addPassiveResources(List<PassiveResource> newPassiveResources){
        passiveResources.addAll(newPassiveResources);
    }

    public List<Index> getAllIndex(){
        return indexFunds;
    }
    public void addIndex(Index index){
        indexFunds.add(index);
    }
    public void addIndexList(List<Index> newIndexFunds){
        indexFunds.addAll(newIndexFunds);
    }

    public List<Commodities> getCommodities(){
        return commodities;
    }
    public void addCommodity(Commodities commodity){
        commodities.add(commodity);
    }
    public void addCommodities(List<Commodities> newCommodities){
        commodities.addAll(newCommodities);
    }

    public List<Crypto> getCrypto(){
        return cryptoCurrencies;
    }
    public void addCrypto(Crypto crypto){
        cryptoCurrencies.add(crypto);
    }
    public void addCryptoList(List<Crypto> newCrypto){
        cryptoCurrencies.addAll(newCrypto);
    }



    public Stock getStockByName(String stockName){
        for(Stock stock: stocks){
            if(stock.getName().equals(stockName)){
                return stock;
            }
        }
        return null;
    }
    public PassiveResource getPassiveResourceByName(String name){
        for(PassiveResource passiveResource: passiveResources){
            if(passiveResource.getName().equals(name)){
                return passiveResource;
            }
        }
        return null;
    }
    public Index getIndexByName(String name){
        for(Index index: indexFunds){
            if(index.getName().equals(name)){
                return index;
            }
        }
        return null;
    }
    public Crypto getCryptoByName(String name){
        for(Crypto crypto: cryptoCurrencies){
            if(crypto.getName().equals(name)){
                return crypto;
            }
        }
        return null;
    }
    public Commodities getCommodityByName(String name){
        for(Commodities commodity: commodities){
            if(commodity.getName().equals(name)){
                return commodity;
            }
        }
        return null;
    }

    public void addPurchaseToResource(String resourceType, String resourceIdentifier, PurchaseInfo purchaseInfo){
        int i;
        switch (resourceType){
            case "stock":
                for(i=0; i<stocks.size(); i++){ //find the resource
                    if(stocks.get(i).getSymbol().equals(resourceIdentifier)){
                        break;
                    }
                }
                stocks.get(i).addPurchase(purchaseInfo);
                break;
            case "index":
                for(i=0; i<indexFunds.size(); i++){ //find the resource
                    if(indexFunds.get(i).getSymbol().equals(resourceIdentifier)){
                        break;
                    }
                }
                indexFunds.get(i).addPurchase(purchaseInfo);
                break;
            case "crypto":
                for(i=0; i<cryptoCurrencies.size(); i++){ //find the resource
                    if(cryptoCurrencies.get(i).getSymbol().equals(resourceIdentifier)){
                        break;
                    }
                }
                cryptoCurrencies.get(i).addPurchase(purchaseInfo);
                break;
            case "commodity":
                for(i=0; i<commodities.size(); i++){ //find the resource
                    if(commodities.get(i).getName().equals(resourceIdentifier)){
                        break;
                    }
                }
                commodities.get(i).addPurchase(purchaseInfo);
                break;
        }
    }

    public boolean isAssetInList(String assetType, String asset){
        int i;
        boolean isPresent = false;
        switch (assetType){
            case "stock":
                for(i=0; i<stocks.size(); i++){ //find the resource
                    if(stocks.get(i).getSymbol().equals(asset)){
                        isPresent = true;
                        break;
                    }
                }
                break;
            case "index":
                for(i=0; i<indexFunds.size(); i++){ //find the resource
                    if(indexFunds.get(i).getSymbol().equals(asset)){
                        isPresent = true;
                        break;
                    }
                }
                break;
            case "crypto":
                for(i=0; i<cryptoCurrencies.size(); i++){ //find the resource
                    if(cryptoCurrencies.get(i).getSymbol().equals(asset)){
                        isPresent = true;
                        break;
                    }
                }
                break;
            case "commodity":
                for(i=0; i<commodities.size(); i++){ //find the resource
                    if(commodities.get(i).getName().equals(asset)){
                        isPresent = true;
                        break;
                    }
                }
                break;
            case "passive-resource":
                for(i=0; i<passiveResources.size(); i++){ //find the resource
                    if(passiveResources.get(i).getName().equals(asset)){
                        isPresent = true;
                        break;
                    }
                }
                break;
        }
        return isPresent;
    }
}
