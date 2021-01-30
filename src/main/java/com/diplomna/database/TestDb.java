package com.diplomna.database;

import com.diplomna.assets.finished.Crypto;
import com.diplomna.assets.finished.Index;
import com.diplomna.assets.finished.Stock;
import com.diplomna.assets.sub.ActiveAsset;
import com.diplomna.database.create.CreateDatabase;
import com.diplomna.database.create.InsertTestDataIntoDb;
import com.diplomna.database.delete.DeleteFromDb;
import com.diplomna.database.delete.sub.DeleteFromStock;
import com.diplomna.database.insert.InsertIntoDb;
import com.diplomna.database.read.ReadFromDb;
import com.diplomna.users.UserManager;
import com.diplomna.users.sub.User;
import org.springframework.web.servlet.view.RedirectView;

import java.sql.SQLException;
import java.util.List;

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

        //ReadFromDb readFromDb = new ReadFromDb("test");
        //readFromDb.readNotificationsByUserId(5);

        /*
        ReadFromDb readFromDb = new ReadFromDb("test");
        List<String> stocks = readFromDb.readPresentAsset("commodity");
        for(String stock: stocks){
            System.out.println(stock);
        }

         */
        /*
        DeleteFromDb deleteFromDb = new DeleteFromDb("test");
        deleteFromDb.deleteAllStockPurchases(5, "AMZN");

         */
        /*
        ReadFromDb readFromDb = new ReadFromDb("test");
        Index stock = readFromDb.readIndexBySymbol("NESHTOKOETOGONQMA");
        if(stock == null){
            System.out.println("nqmaq gooo");
        }

         */
        /*
        ReadFromDb readFromDb = new ReadFromDb("test");
        UserManager users = readFromDb.readUsers(true);
        for(User user: users.getUsers()){
            System.out.println(user.getUserName());
        }

         */
        ReadFromDb readFromDb = new ReadFromDb("test");
        List<Crypto> stocks = null;
        try {
            stocks = readFromDb.readAllCrypto();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        for(Crypto stock: stocks){
            stock.printCrypto();
        }
    }
}
