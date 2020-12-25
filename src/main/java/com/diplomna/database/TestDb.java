package com.diplomna.database;

import com.diplomna.assets.finished.Stock;
import com.diplomna.database.read.ReadFromDb;

public class TestDb {
    public static void main(String []args) {


        //CreateDatabase createDatabase = new CreateDatabase("test");
        //createDatabase.createWholeDb();

        //InsertTestDataIntoDb insertTestDataIntoDb = new InsertTestDataIntoDb();
        //insertTestDataIntoDb.insertTestData("test");


        //ReadFromDb readFromDb = new ReadFromDb("test");
        //readFromDb.readUsers();

        /*
        ReadFromDb readFromDb = new ReadFromDb("test");
        List<Stock> stocks = readFromDb.readStockPurchasesByUserId(1);
        System.out.println(stocks.size());
        for(Stock stock: stocks){
            System.out.print(stock.getSymbol() + " ");
            System.out.print(stock.getFirstPurchase().getPrice() + " ");
            System.out.print(stock.getFirstPurchase().getQuantity() + " ");
            System.out.println(stock.getFirstPurchase().getPurchaseDate().getDateSql() + " ");
        }

         */
        /*
        ReadFromDb readFromDb = new ReadFromDb("test");
        Stock stock = readFromDb.readStockBySymbol("TSLA");
        System.out.println(stock.getSymbol());
        System.out.println(stock.getCurrencySymbol());
        System.out.println(stock.getDescription());
        System.out.println(stock.getName());

         */
    }
}
