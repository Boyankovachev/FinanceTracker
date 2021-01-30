package com.diplomna.assets;

import com.diplomna.assets.finished.*;
import com.diplomna.assets.sub.PurchaseInfo;
import com.diplomna.exceptions.AssetNotFoundException;
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
    public Stock getStockBySymbol(String stockSymbol){
        for(Stock stock: stocks){
            if(stock.getSymbol().equals(stockSymbol)){
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
    public Index getIndexBySymbol(String indexSymbol){
        for(Index index: indexFunds){
            if(index.getSymbol().equals(indexSymbol)){
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
    public Crypto getCryptoBySymbol(String cryptoSymbol){
        for(Crypto crypto: cryptoCurrencies){
            if(crypto.getSymbol().equals(cryptoSymbol)){
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

    public void removeAsset(String assetType, String asset) throws AssetNotFoundException {
        if(!isAssetInList(assetType, asset)){
            throw new AssetNotFoundException(assetType + ": " + asset + " not found");
        }
        switch (assetType){
            case "stock":
                Stock tempStock = getStockBySymbol(asset);
                stocks.remove(tempStock);
                break;
            case "index":
                Index tempIndex = getIndexBySymbol(asset);
                indexFunds.remove(tempIndex);
                break;
            case "crypto":
                Crypto cryptoTemp = getCryptoByName(asset);
                cryptoCurrencies.remove(cryptoTemp);
                break;
            case "commodity":
                Commodities commodityTemp = getCommodityByName(asset);
                commodities.remove(commodityTemp);
                break;
            case "passive-resource":
                PassiveResource passiveResourceTemp = getPassiveResourceByName(asset);
                passiveResources.remove(passiveResourceTemp);
                break;
        }
    }

    public double calculateAllStocksWorth(){
        double total = 0;
        for(Stock stock: stocks){
            total = total + stock.getCurrentMarketPrice() * stock.getQuantityOwned();
        }
        return total;
    }
    public double calculateAllIndexWorth(){
        double total = 0;
        for(Index index: indexFunds){
            total = total + index.getCurrentMarketPrice() * index.getQuantityOwned();
        }
        return total;
    }
    public double calculateAllCryptoWorth(){
        double total = 0;
        for(Crypto crypto: cryptoCurrencies){
            total = total + crypto.getCurrentMarketPrice() * crypto.getQuantityOwned();
        }
        return total;
    }
    public double calculateAllCommodityWorth(){
        double total = 0;
        for(Commodities commodity: commodities){
            total = total + commodity.getCurrentMarketPrice() * commodity.getQuantityOwned();
        }
        return total;
    }
    public double calculateAllPassiveResourceWorth(){
        double total = 0;
        for(PassiveResource passiveResource: passiveResources){
            total = total + passiveResource.getCurrentMarketPrice();
        }
        return total;
    }
    public double calculateWholePortfolio(){
        return calculateAllStocksWorth() + calculateAllIndexWorth() + calculateAllCryptoWorth() +
                calculateAllCommodityWorth() + calculateAllPassiveResourceWorth();
    }

    public void printAssets(){
        /*
        Prints all assets to console.
        Testing purposes!
        */
        for(Stock stock: stocks){
            stock.printStock();
        }
        for(PassiveResource passiveResource: passiveResources){
            passiveResource.printPassiveResource();
        }
        for (Index index: indexFunds){
            index.printIndex();
        }
        for(Commodities commodity: commodities){
            commodity.printCommodity();
        }
        for(Crypto crypto: cryptoCurrencies){
            crypto.printCrypto();
        }
    }
}
