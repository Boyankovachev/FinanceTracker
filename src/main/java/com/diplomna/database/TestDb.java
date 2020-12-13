package com.diplomna.database;

import com.diplomna.assets.finished.Stock;
import com.diplomna.assets.sub.PurchaseInfo;
import com.diplomna.database.create.CreateDatabase;
import com.diplomna.database.create.InsertTestDataIntoDb;
import com.diplomna.database.insert.InsertIntoDb;
import com.diplomna.date.DatеManager;
import com.diplomna.users.sub.User;

public class TestDb {
        public static void main(String []args) {
            //InsertTestDataIntoDb test = new InsertTestDataIntoDb("test");
            //test.insertIntoUsers();
            //test.test_things();

            InsertIntoDb insert = new InsertIntoDb("test");


            //insert.InsertUser(new User("Venci2", "veneee", "soltanavenci2"));
            //User user1 = new User("Bojidar", "bojidarhash","bojidarsol");
            //user1.setEmail("bojidar@gmail.com");
            //user1.setIs2FactorAuthenticationRequired(true);
            //insert.InsertUser(user1);

            /*
            Stock stock = new Stock("TSLA","Tesla","Dollar", "$",
                    "NASDAQ", "bla bla bla");
            Stock stock2 = new Stock();
            stock2.setSymbol("AAPL2");
            stock2.setName("Apple");
            stock2.setCurrencySymbol("$");
            stock2.setCurrency("Dollar");
            insert.InsertStock(stock2);

             */

            PurchaseInfo purchaseInfo = new PurchaseInfo();
            purchaseInfo.setPrice(12.23);
            purchaseInfo.setQuantity(1.75);
            purchaseInfo.setStockSymbol("TSLA");
            purchaseInfo.setPurchaseDate(new DatеManager());
            insert.insertStockPurchaseInfo(1,purchaseInfo);
    }
}