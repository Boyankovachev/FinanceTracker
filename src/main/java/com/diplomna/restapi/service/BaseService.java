package com.diplomna.restapi.service;

import com.diplomna.api.stock.ParseStock;
import com.diplomna.assets.finished.*;
import com.diplomna.database.read.ReadFromDb;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BaseService {

    public BaseService(){}

    public List<Stock> getStocksByUserId(int userId){
        ReadFromDb readFromDb = new ReadFromDb("test");
        List<Stock> stockPurchases = readFromDb.readStockPurchasesByUserId(userId);
        List<Stock> stockBase = new ArrayList<>();
        List<String> ownedStocksSymbols = new ArrayList<>();
        for(Stock stock: stockPurchases){
            // Add all stock symbols the user owns
            if(!ownedStocksSymbols.contains(stock.getSymbol())){
                ownedStocksSymbols.add(stock.getSymbol());
            }
        }
        for(String symbol: ownedStocksSymbols){
            // read stocks
            stockBase.add(readFromDb.readStockBySymbol(symbol));
        }
        /*
        for(Stock stock: stockPurchases){
            System.out.print(stock.getSymbol() + " ");
            System.out.print(stock.getFirstPurchase().getPrice() + " ");
            System.out.print(stock.getFirstPurchase().getQuantity() + " ");
            System.out.println(stock.getFirstPurchase().getPurchaseDate().getDateSql() + " ");
        }
        System.out.println();
        for(Stock stock: stockBase){
            System.out.print(stock.getSymbol()  + " ");
            System.out.print(stock.getCurrencySymbol()  + " ");
            System.out.print(stock.getDescription()  + " ");
            System.out.println(stock.getName());
        }

         */
        ParseStock parseStock = new ParseStock();
        int i,j;
        for(i=0; i<stockBase.size(); i++){
            for(j=0; j<stockPurchases.size(); j++){
                if(stockBase.get(i).getSymbol().equals(stockPurchases.get(j).getSymbol())){
                    stockBase.get(i).addPurchase(stockPurchases.get(j).getFirstPurchase());
                }
            }
            stockBase.get(i).calculateQuantityOwned();
            stockBase.get(i).calculateAveragePurchasePrice();
            parseStock.setStockBySymbol(stockBase.get(i).getSymbol());  // getting current data from API
            stockBase.get(i).setCurrentMarketPrice(parseStock.getRawCurrentPrice());
            stockBase.get(i).setMarketOpen(parseStock.isMarketOpen());
            stockBase.get(i).setRecommendationKey(parseStock.getRecommendationKey());
        }
        /*
        //testing if works
        for(Stock stock: stockBase){
            System.out.print(stock.getSymbol() + " ");
            System.out.print(stock.getName() + " ");
            System.out.print(stock.getCurrency() + " ");
            System.out.print(stock.getCurrencySymbol() + " ");
            System.out.print(stock.getExchangeName() + " ");
            System.out.println(stock.getDescription() + " ");
            System.out.println("Total quantity owned: " + stock.getQuantityOwned() + " Avarage Purchase Price: " + stock.getAveragePurchasePrice());
            System.out.println("current price: " + stock.getCurrentMarketPrice() + " | is market open: " + stock.isMarketOpen() + " | recomendation key: " + stock.getRecommendationKey());
            for(i=0; i<stock.getAllPurchases().size(); i++){
                //System.out.print(stock.getFirstPurchase().getPrice() + " ");
                //System.out.print(stock.getFirstPurchase().getQuantity() + " ");
                //System.out.println(stock.getFirstPurchase().getPurchaseDate().getDateSql() + " ");
                System.out.print(stock.getAllPurchases().get(i).getPrice() + " ");
                System.out.print(stock.getAllPurchases().get(i).getQuantity() + " ");
                System.out.println(stock.getAllPurchases().get(i).getPurchaseDate().getDateSql() + " ");
            }
            System.out.println("\n");
        }
         */

        return stockBase;
    }

    public List<PassiveResource> getPassiveResourcesByUserId(int userId){
        ReadFromDb readFromDb = new ReadFromDb("test");
        List<PassiveResource> passiveResources = readFromDb.readPassiveResourcesByUserId(userId);
        for (PassiveResource passiveResource: passiveResources){
            System.out.print(passiveResource.getName() + " ");
            System.out.print(passiveResource.getPurchaseInfo().getPrice() + " ");
            System.out.print(passiveResource.getPurchaseInfo().getPurchaseDate().getDateSql() + " ");
            System.out.print(passiveResource.getCurrentMarketPrice() + " ");
            System.out.print(passiveResource.getDescription() + " ");
            System.out.print(passiveResource.getCurrency() + " ");
            System.out.print(passiveResource.getCurrencySymbol() + " ");
        }
        return passiveResources;
    }

    public List<Index> getIndexByUserId(int userId){

        ReadFromDb readFromDb = new ReadFromDb("test");
        List<Index> indexPurchases = readFromDb.readIndexPurchasesByUserId(userId);
        List<Index> indexBase = new ArrayList<>();
        List<String> ownedIndexSymbols = new ArrayList<>();

        for(Index index: indexPurchases){
            if(!ownedIndexSymbols.contains(index.getSymbol())){
                ownedIndexSymbols.add(index.getSymbol());
            }
        }
        for(String symbol: ownedIndexSymbols){
            indexBase.add(readFromDb.readIndexBySymbol(symbol));
        }

        int i,j;
        for(i=0; i<indexBase.size(); i++){
            for(j=0; j<indexPurchases.size(); j++){
                if(indexBase.get(i).getSymbol().equals(indexPurchases.get(j).getSymbol())){
                    indexBase.get(i).addPurchase(indexPurchases.get(j).getFirstPurchase());
                }
            }
            indexBase.get(i).calculateQuantityOwned();
            indexBase.get(i).calculateAveragePurchasePrice();
            // GET REST OF DATA FROM API !!!!!
        }

        /*  TESTING IF WORKS
                for(Index index: indexList){
            System.out.print(index.getSymbol() + " ");
            System.out.print(index.getName() + " ");
            System.out.print(index.getCurrency() + " ");
            System.out.print(index.getCurrencySymbol() + " ");
            System.out.println(index.getDescription() + " ");
            System.out.println("Total quantity owned: " + index.getQuantityOwned() + " Avarage Purchase Price: " + index.getAveragePurchasePrice());
            for(int i=0; i<index.getAllPurchases().size(); i++){
                //System.out.print(stock.getFirstPurchase().getPrice() + " ");
                //System.out.print(stock.getFirstPurchase().getQuantity() + " ");
                //System.out.println(stock.getFirstPurchase().getPurchaseDate().getDateSql() + " ");
                System.out.print(index.getAllPurchases().get(i).getPrice() + " ");
                System.out.print(index.getAllPurchases().get(i).getQuantity() + " ");
                System.out.println(index.getAllPurchases().get(i).getPurchaseDate().getDateSql() + " ");
            }
            System.out.println("\n");
        }
         */
        return indexBase;
    }

    public List<Crypto> getCryptoByUserId(int userId){
        ReadFromDb readFromDb = new ReadFromDb("test");
        List<Crypto> cryptoPurchases = readFromDb.readCryptoPurchaseByUserId(userId);
        List<Crypto> cryptoBase = new ArrayList<>();
        List<String> ownedCryptoSymbols = new ArrayList<>();
        for(Crypto crypto: cryptoPurchases){
            if(!ownedCryptoSymbols.contains(crypto.getSymbol())){
                ownedCryptoSymbols.add(crypto.getSymbol());
            }
        }
        for(String symbol: ownedCryptoSymbols){
            cryptoBase.add(readFromDb.readCryptoBySymbol(symbol));
        }
        int i,j;
        for(i=0; i<cryptoBase.size(); i++){
            for(j=0; j<cryptoPurchases.size(); j++){
                if(cryptoBase.get(i).getSymbol().equals(cryptoPurchases.get(j).getSymbol())){
                    cryptoBase.get(i).addPurchase(cryptoPurchases.get(j).getFirstPurchase());
                }
            }
            cryptoBase.get(i).calculateQuantityOwned();
            cryptoBase.get(i).calculateAveragePurchasePrice();
        }

        /*
        //Testing if works
        for(Crypto crypto: cryptoBase) {
            System.out.print(crypto.getSymbol() + " ");
            System.out.print(crypto.getName() + " ");
            System.out.print(crypto.getCurrency() + " ");
            System.out.print(crypto.getCurrencySymbol() + " ");
            System.out.println(crypto.getDescription() + " ");
            System.out.println("Total quantity owned: " + crypto.getQuantityOwned() + " Avarage Purchase Price: " + crypto.getAveragePurchasePrice());
            for (i = 0; i < crypto.getAllPurchases().size(); i++) {
                //System.out.print(stock.getFirstPurchase().getPrice() + " ");
                //System.out.print(stock.getFirstPurchase().getQuantity() + " ");
                //System.out.println(stock.getFirstPurchase().getPurchaseDate().getDateSql() + " ");
                System.out.print(crypto.getAllPurchases().get(i).getPrice() + " ");
                System.out.print(crypto.getAllPurchases().get(i).getQuantity() + " ");
                System.out.println(crypto.getAllPurchases().get(i).getPurchaseDate().getDateSql() + " ");
            }
            System.out.println("\n");
        }

         */

        return cryptoBase;
    }

    public List<Commodities> getCommodityByUserId(int userId){
        ReadFromDb readFromDb = new ReadFromDb("test");
        List<Commodities> commodityPurchases = readFromDb.readCommodityPurchaseInfoByUserId(userId);
        List<Commodities> commodityBase = new ArrayList<>();
        List<String> ownedCommodityNames = new ArrayList<>();
        for(Commodities commodity: commodityPurchases){
            if(!ownedCommodityNames.contains(commodity.getName())){
                ownedCommodityNames.add(commodity.getName());
            }
        }
        for(String name: ownedCommodityNames){
            commodityBase.add(readFromDb.readCommodityByCommodityName(name));
        }

        int i,j;
        for(i=0; i<commodityBase.size(); i++){
            for(j=0; j<commodityPurchases.size(); j++){
                if(commodityBase.get(i).getName().equals(commodityPurchases.get(j).getName())){
                    commodityBase.get(i).addPurchase(commodityPurchases.get(j).getFirstPurchase());
                }
            }
            commodityBase.get(i).calculateQuantityOwned();
            commodityBase.get(i).calculateAveragePurchasePrice();
        }

        /*
        //Testing if works
        for(Commodities commodity: commodityBase) {
            System.out.print(commodity.getName() + " ");
            System.out.print(commodity.getCurrency() + " ");
            System.out.print(commodity.getCurrencySymbol() + " ");
            System.out.print(commodity.getExchangeName() + " ");
            System.out.println(commodity.getDescription() + " ");
            System.out.println("Total quantity owned: " + commodity.getQuantityOwned() + " Avarage Purchase Price: " + commodity.getAveragePurchasePrice());
            for (i = 0; i < commodity.getAllPurchases().size(); i++) {
                //System.out.print(stock.getFirstPurchase().getPrice() + " ");
                //System.out.print(stock.getFirstPurchase().getQuantity() + " ");
                //System.out.println(stock.getFirstPurchase().getPurchaseDate().getDateSql() + " ");
                System.out.print(commodity.getAllPurchases().get(i).getPrice() + " ");
                System.out.print(commodity.getAllPurchases().get(i).getQuantity() + " ");
                System.out.println(commodity.getAllPurchases().get(i).getPurchaseDate().getDateSql() + " ");
            }
            System.out.println("\n");
        }
         */


        return commodityBase;
    }
}
