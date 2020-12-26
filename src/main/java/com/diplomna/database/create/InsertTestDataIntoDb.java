package com.diplomna.database.create;

import com.diplomna.assets.finished.*;
import com.diplomna.assets.sub.PurchaseInfo;
import com.diplomna.database.insert.InsertIntoDb;
import com.diplomna.date.DatеManager;
import com.diplomna.users.sub.AssetType;
import com.diplomna.users.sub.Notification;
import com.diplomna.users.sub.User;


public class InsertTestDataIntoDb {
    /*
        tozi klass e samo za testvane na insert
        i za vmukvane na testova infrormaviq vuv bazata danni
        (grozno e)
     */
    public void insertTestData(String databaseName){
        InsertIntoDb insert = new InsertIntoDb(databaseName);


        /////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////
        // USERS
        insert.insertUser(new User("Venci", "vencihas", "soltanavenci"));
        User user = new User("Bojidar", "bojidarhash","bojidarsol");
        user.setEmail("bojidar@gmail.com");
        user.setIs2FactorAuthenticationRequired(true);
        insert.insertUser(user);

        /////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////
        // STOCKS
        Stock stock = new Stock("TSLA","Tesla","Dollar", "$",
                "NASDAQ", "tesla description");
        Stock stock2 = new Stock();
        Stock stock3 = new Stock("AMZN","Amazon","Dollar", "$",
                "NASDAQ", "amazon sells things online");
        stock2.setSymbol("AAPL");
        stock2.setName("Apple");
        stock2.setCurrencySymbol("$");
        stock2.setCurrency("Dollar");
        insert.insertStock(stock);
        insert.insertStock(stock2);
        insert.insertStock(stock3);

        PurchaseInfo purchaseInfoVenciTesla = new PurchaseInfo();
        purchaseInfoVenciTesla.setPrice(425.54);
        purchaseInfoVenciTesla.setQuantity(0.75);
        purchaseInfoVenciTesla.setStockSymbol("TSLA");
        purchaseInfoVenciTesla.setPurchaseDate(new DatеManager());
        /////////////////////////////////////////////////////////////
        PurchaseInfo purchaseInfoAppleBojidar = new PurchaseInfo();
        purchaseInfoAppleBojidar.setPrice(320.23);
        purchaseInfoAppleBojidar.setQuantity(5);
        purchaseInfoAppleBojidar.setStockSymbol("AAPL");
        purchaseInfoAppleBojidar.setPurchaseDate(new DatеManager());
        /////////////////////////////////////////////////////////////
        PurchaseInfo a = new PurchaseInfo();
        a.setPrice(145.21);
        a.setQuantity(15.64);
        a.setStockSymbol("AMZN");
        a.setPurchaseDate(new DatеManager());
        /////////////////////////////////////////////////////////////
        PurchaseInfo b = new PurchaseInfo();
        b.setPrice(1250);
        b.setQuantity(120);
        b.setStockSymbol("AMZN");
        b.setPurchaseDate(new DatеManager());
        PurchaseInfo b2 = new PurchaseInfo();
        b2.setPrice(123);
        b2.setQuantity(124);
        b2.setStockSymbol("TSLA");
        b2.setPurchaseDate(new DatеManager());
        PurchaseInfo b3 = new PurchaseInfo();
        b3.setPrice(64);
        b3.setQuantity(15);
        b3.setStockSymbol("TSLA");
        b3.setPurchaseDate(new DatеManager());
        PurchaseInfo b4 = new PurchaseInfo();
        b4.setPrice(1234);
        b4.setQuantity(164);
        b4.setStockSymbol("AMZN");
        b4.setPurchaseDate(new DatеManager());
        PurchaseInfo b5 = new PurchaseInfo();
        b5.setPrice(32);
        b5.setQuantity(16);
        b5.setStockSymbol("AMZN");
        b5.setPurchaseDate(new DatеManager());
        PurchaseInfo b6 = new PurchaseInfo();
        b6.setPrice(246);
        b6.setQuantity(264);
        b6.setStockSymbol("AMZN");
        b6.setPurchaseDate(new DatеManager());
        /////////////////////////////////////////////////////////////
        insert.insertStockPurchaseInfo(1,purchaseInfoVenciTesla);
        insert.insertStockPurchaseInfo(2,purchaseInfoAppleBojidar);
        insert.insertStockPurchaseInfo(1,a);
        insert.insertStockPurchaseInfo(2,b);
        insert.insertStockPurchaseInfo(1,b2);
        insert.insertStockPurchaseInfo(2,b3);
        insert.insertStockPurchaseInfo(1,b3);
        insert.insertStockPurchaseInfo(1,b4);
        insert.insertStockPurchaseInfo(2,b5);
        insert.insertStockPurchaseInfo(1,b6);


        /////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////
        // PASSIVE RESOURCES
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
        /////////////////////////////////////////////////////////////
        PassiveResource apartament2 = new PassiveResource();
        apartament2.setName("apartament2");
        PurchaseInfo purchaseInfoApartament2 = new PurchaseInfo();
        purchaseInfoApartament2.setPrice(150000);
        apartament2.setPurchaseInfo(purchaseInfoApartament2);
        /////////////////////////////////////////////////////////////
        PassiveResource ap = new PassiveResource();
        ap.setName("Apartament Mladost");
        PurchaseInfo aa = new PurchaseInfo();
        aa.setPurchaseDate(new DatеManager());
        aa.setPrice(75000);
        ap.setPurchaseInfo(aa);
        ap.setCurrentMarketPrice(80000);
        ap.setDescription("Apartament v mladost 1 ul edi koq si tnn..");
        ap.setCurrency("LEV");
        ap.setCurrencySymbol("lev");
        /////////////////////////////////////////////////////////////
        PassiveResource ap4 = new PassiveResource();
        ap4.setName("apartament2");
        PurchaseInfo purchaseInfoApartament4 = new PurchaseInfo();
        purchaseInfoApartament4.setPrice(60000);
        ap4.setPurchaseInfo(purchaseInfoApartament2);
        /////////////////////////////////////////////////////////////
        insert.insertPassiveResource(1, apartament1);
        insert.insertPassiveResource(2, apartament2);
        insert.insertPassiveResource(2, ap);
        insert.insertPassiveResource(1, ap4);

        PurchaseInfo ALLPURCHASEINFO = new PurchaseInfo();
        ALLPURCHASEINFO.setPurchaseDate(new DatеManager());
        ALLPURCHASEINFO.setPrice(540);
        ALLPURCHASEINFO.setQuantity(12);


        /////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////
        // COMMODITIES
        Commodities commodities1 = new Commodities();
        commodities1.setName("Petrol");
        commodities1.setExchangeName("EXCH");
        commodities1.setCurrency("Dollar");
        commodities1.setCurrencySymbol("$");
        /////////////////////////////////////////////////////////////
        Commodities commodities2 = new Commodities();
        commodities2.setName("Wheat");
        commodities2.setExchangeName("EXCH");
        commodities2.setCurrency("Dollar");
        commodities2.setCurrencySymbol("$");
        /////////////////////////////////////////////////////////////
        insert.insertCommodity(commodities1);
        insert.insertCommodity(commodities2);

        ALLPURCHASEINFO.setStockSymbol("Wheat");
        insert.insertCommodityPurchaseInfo(1,ALLPURCHASEINFO);
        ALLPURCHASEINFO.setQuantity(14);
        ALLPURCHASEINFO.setPrice(15.42);
        insert.insertCommodityPurchaseInfo(2,ALLPURCHASEINFO);
        ALLPURCHASEINFO.setStockSymbol("Petrol");
        insert.insertCommodityPurchaseInfo(1,ALLPURCHASEINFO);
        ALLPURCHASEINFO.setStockSymbol("Petrol");
        ALLPURCHASEINFO.setPrice(123);
        ALLPURCHASEINFO.setQuantity(456);
        insert.insertCommodityPurchaseInfo(1, ALLPURCHASEINFO);
        ALLPURCHASEINFO.setPrice(235);
        ALLPURCHASEINFO.setQuantity(135);
        insert.insertCommodityPurchaseInfo(2, ALLPURCHASEINFO);
        ALLPURCHASEINFO.setPrice(153);
        ALLPURCHASEINFO.setQuantity(134);
        insert.insertCommodityPurchaseInfo(2, ALLPURCHASEINFO);
        ALLPURCHASEINFO.setPrice(2341);
        ALLPURCHASEINFO.setQuantity(1324);
        ALLPURCHASEINFO.setStockSymbol("Wheat");
        insert.insertCommodityPurchaseInfo(1, ALLPURCHASEINFO);
        ALLPURCHASEINFO.setPrice(43);
        ALLPURCHASEINFO.setQuantity(12);
        insert.insertCommodityPurchaseInfo(2, ALLPURCHASEINFO);

        /////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////
        // CRYPTO
        Crypto crypto1 = new Crypto();
        crypto1.setName("Bitcoin");
        crypto1.setCurrency("Dollar");
        crypto1.setCurrencySymbol("$");
        crypto1.setSymbol("BTC");
        /////////////////////////////////////////////////////////////
        Crypto crypto2 = new Crypto();
        crypto2.setName("Ethereum");
        crypto2.setCurrency("Dollar");
        crypto2.setCurrencySymbol("$");
        crypto2.setSymbol("ETH");
        /////////////////////////////////////////////////////////////
        insert.insertCrypto(crypto1);
        insert.insertCrypto(crypto2);
        /////////////////////////////////////////////////////////////
        ALLPURCHASEINFO.setStockSymbol("BTC");
        ALLPURCHASEINFO.setQuantity(1);
        ALLPURCHASEINFO.setPrice(2);
        insert.insertCryptoPurchaseInfo(1, ALLPURCHASEINFO);
        ALLPURCHASEINFO.setQuantity(5);
        ALLPURCHASEINFO.setPrice(6);
        insert.insertCryptoPurchaseInfo(2, ALLPURCHASEINFO);
        ALLPURCHASEINFO.setQuantity(7);
        ALLPURCHASEINFO.setPrice(8);
        insert.insertCryptoPurchaseInfo(1, ALLPURCHASEINFO);
        ALLPURCHASEINFO.setQuantity(9);
        ALLPURCHASEINFO.setPrice(10);
        insert.insertCryptoPurchaseInfo(2, ALLPURCHASEINFO);
        ALLPURCHASEINFO.setStockSymbol("ETH");
        ALLPURCHASEINFO.setQuantity(10);
        ALLPURCHASEINFO.setPrice(20);
        insert.insertCryptoPurchaseInfo(1, ALLPURCHASEINFO);
        ALLPURCHASEINFO.setQuantity(50);
        ALLPURCHASEINFO.setPrice(60);
        insert.insertCryptoPurchaseInfo(1, ALLPURCHASEINFO);
        ALLPURCHASEINFO.setQuantity(70);
        ALLPURCHASEINFO.setPrice(80);
        insert.insertCryptoPurchaseInfo(1, ALLPURCHASEINFO);
        ALLPURCHASEINFO.setQuantity(90);
        ALLPURCHASEINFO.setPrice(100);
        insert.insertCryptoPurchaseInfo(2, ALLPURCHASEINFO);

        /////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////
        // INDEX
        Index index1 = new Index();
        index1.setName("Standard & Poor's 500");
        index1.setSymbol("S&P500");
        index1.setExchangeName("NASAQ");
        index1.setCurrency("Dollar");
        index1.setCurrencySymbol("$");
        /////////////////////////////////////////////////////////////
        Index index2 = new Index();
        index2.setName("nekuv ETF");
        index2.setSymbol("ETF");
        index2.setExchangeName("NASAQ");
        index2.setCurrency("Dollar");
        index2.setCurrencySymbol("$");
        /////////////////////////////////////////////////////////////
        insert.insertIndex(index1);
        insert.insertIndex(index2);
        /////////////////////////////////////////////////////////////
        ALLPURCHASEINFO.setStockSymbol("S&P500");
        ALLPURCHASEINFO.setQuantity(5);
        ALLPURCHASEINFO.setPrice(1);
        insert.insertIndexPurchaseInfo(2, ALLPURCHASEINFO);
        ALLPURCHASEINFO.setQuantity(6);
        ALLPURCHASEINFO.setPrice(3);
        insert.insertIndexPurchaseInfo(1, ALLPURCHASEINFO);
        ALLPURCHASEINFO.setQuantity(8);
        ALLPURCHASEINFO.setPrice(1);
        insert.insertIndexPurchaseInfo(2, ALLPURCHASEINFO);
        ALLPURCHASEINFO.setQuantity(24);
        ALLPURCHASEINFO.setPrice(23);
        insert.insertIndexPurchaseInfo(1, ALLPURCHASEINFO);
        ALLPURCHASEINFO.setStockSymbol("ETF");
        insert.insertIndexPurchaseInfo(1, ALLPURCHASEINFO);
        ALLPURCHASEINFO.setQuantity(1);
        ALLPURCHASEINFO.setPrice(2);
        insert.insertIndexPurchaseInfo(2, ALLPURCHASEINFO);
        ALLPURCHASEINFO.setQuantity(24);
        ALLPURCHASEINFO.setPrice(4);
        insert.insertIndexPurchaseInfo(1, ALLPURCHASEINFO);
        ALLPURCHASEINFO.setQuantity(34);
        ALLPURCHASEINFO.setPrice(84);
        insert.insertIndexPurchaseInfo(2, ALLPURCHASEINFO);


        /////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////
        // NOTIFICATIONS
        Notification notification = new Notification(AssetType.stock, 750, "tesla price prediction alert");
        notification.setStockSymbol("TSLA");
        notification.setAssetTypeSettings(true);
        insert.insertNotification(1,notification);
    }
}