package com.diplomna.restapi.service;

import com.diplomna.api.stock.ParseStock;
import com.diplomna.assets.finished.Stock;
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
}
