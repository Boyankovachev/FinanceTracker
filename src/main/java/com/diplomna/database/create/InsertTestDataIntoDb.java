package com.diplomna.database.create;

import com.diplomna.assets.finished.PassiveResource;
import com.diplomna.assets.finished.Stock;
import com.diplomna.assets.sub.PurchaseInfo;
import com.diplomna.database.insert.InsertIntoDb;
import com.diplomna.date.DatеManager;
import com.diplomna.users.sub.AssetType;
import com.diplomna.users.sub.Notification;
import com.diplomna.users.sub.User;


public class InsertTestDataIntoDb {
    public void insertTestData(String databaseName){
        InsertIntoDb insert = new InsertIntoDb(databaseName);

        insert.insertUser(new User("Venci", "vencihas", "soltanavenci"));
        User user = new User("Bojidar", "bojidarhash","bojidarsol");
        user.setEmail("bojidar@gmail.com");
        user.setIs2FactorAuthenticationRequired(true);
        insert.insertUser(user);

        Stock stock = new Stock("TSLA","Tesla","Dollar", "$",
                "NASDAQ", "tesla description");
        Stock stock2 = new Stock();
        stock2.setSymbol("AAPL");
        stock2.setName("Apple");
        stock2.setCurrencySymbol("$");
        stock2.setCurrency("Dollar");
        insert.insertStock(stock);
        insert.insertStock(stock2);

        PurchaseInfo purchaseInfoVenciTesla = new PurchaseInfo();
        purchaseInfoVenciTesla.setPrice(425.54);
        purchaseInfoVenciTesla.setQuantity(0.75);
        purchaseInfoVenciTesla.setStockSymbol("TSLA");
        purchaseInfoVenciTesla.setPurchaseDate(new DatеManager());
        PurchaseInfo purchaseInfoAppleBojidar = new PurchaseInfo();
        purchaseInfoAppleBojidar.setPrice(320.23);
        purchaseInfoAppleBojidar.setQuantity(5);
        purchaseInfoAppleBojidar.setStockSymbol("AAPL");
        purchaseInfoAppleBojidar.setPurchaseDate(new DatеManager());
        insert.insertStockPurchaseInfo(1,purchaseInfoVenciTesla);
        insert.insertStockPurchaseInfo(2,purchaseInfoAppleBojidar);


        PassiveResource apartament1 = new PassiveResource();
        apartament1.setName("Apartament");
        PurchaseInfo purchaseInfoApartament1 = new PurchaseInfo();
        purchaseInfoApartament1.setPurchaseDate(new DatеManager());
        purchaseInfoApartament1.setPrice(100000);
        apartament1.setPurchaseInfo(purchaseInfoApartament1);
        apartament1.setCurrentMarketPrice(120000);
        apartament1.setDescription("Apartament v sofia");
        apartament1.setCurrency("LEV");
        apartament1.setCurrencySymbol("lev");
        insert.insertPassiveResource(1, apartament1);
        PassiveResource apartament2 = new PassiveResource();
        apartament2.setName("apartament2");
        PurchaseInfo purchaseInfoApartament2 = new PurchaseInfo();
        purchaseInfoApartament2.setPrice(150000);
        apartament2.setPurchaseInfo(purchaseInfoApartament2);
        insert.insertPassiveResource(2,apartament2);


        Notification notification = new Notification(AssetType.stock, 750, "tesla price prediction alert");
        notification.setStockSymbol("TSLA");
        insert.insertNotification(1,notification);
    }
}