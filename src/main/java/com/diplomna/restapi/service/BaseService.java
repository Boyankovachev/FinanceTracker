package com.diplomna.restapi.service;

import com.diplomna.api.stock.ParseStock;
import com.diplomna.assets.AssetManager;
import com.diplomna.assets.finished.*;
import com.diplomna.assets.sub.PurchaseInfo;
import com.diplomna.database.insert.InsertIntoDb;
import com.diplomna.database.read.ReadFromDb;
import com.diplomna.date.DatеManager;
import com.diplomna.users.sub.Notification;
import com.diplomna.users.sub.User;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mysql.cj.CacheAdapter;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BaseService {

    public BaseService(){}

    public User setupUser(User user){
        AssetManager assetManager = new AssetManager();
        assetManager.addStocks(getStocksByUserId(user.getUserId()));
        assetManager.addPassiveResources(getPassiveResourcesByUserId(user.getUserId()));
        assetManager.addIndexList(getIndexByUserId(user.getUserId()));
        assetManager.addCryptoList(getCryptoByUserId(user.getUserId()));
        assetManager.addCommodities(getCommodityByUserId(user.getUserId()));
        user.setAssets(assetManager);
        
        user.setNotifications(getNotificationsByUserId(user.getUserId()));
        return user;
    }

    private List<Notification> getNotificationsByUserId(int userId){
        ReadFromDb readFromDb = new ReadFromDb("test");
        return readFromDb.readNotificationsByUserId(userId);
    }

    private List<Stock> getStocksByUserId(int userId){
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
            try {
                parseStock.setStockBySymbol(stockBase.get(i).getSymbol());  // getting current data from API
            } catch (UnirestException e) {
                //log error
                e.printStackTrace();
            }
            stockBase.get(i).setCurrentMarketPrice(parseStock.getRawCurrentPrice());
            stockBase.get(i).setMarketOpen(parseStock.isMarketOpen());
            stockBase.get(i).setRecommendationKey(parseStock.getRecommendationKey());
        }

        return stockBase;
    }

    private List<PassiveResource> getPassiveResourcesByUserId(int userId){
        ReadFromDb readFromDb = new ReadFromDb("test");
        List<PassiveResource> passiveResources = readFromDb.readPassiveResourcesByUserId(userId);
        return passiveResources;
    }

    private List<Index> getIndexByUserId(int userId){

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

    private List<Crypto> getCryptoByUserId(int userId){
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

    private List<Commodities> getCommodityByUserId(int userId){
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

    public String addPassiveAsset(JSONObject jsonObject, User user){
        /*
        1. proveri dali go ima veche
                -ako go ima returnvash na usera che veche ima takuv
        2. proveri dali stoinostite sa pravilni
                -ako ne sa pravilni vrushtash na usera che e vuvel nevalidni stoinosti
        3. dobavi v ram
                -ako datata ne e pravilno retunvash che e greshna datata
        4. dobavi v database
         */

        //1 - raboti
        if(user.getAssets().isAssetInList("passive-resource", jsonObject.getString("name"))){
            return "Passive resource with such a name already exists in your portfolio!";
        }

        //2 - raboti
        try {
            if (jsonObject.getDouble("price") <= 0) {
                return "Invalid price";
            }
        }catch (JSONException e){ //if double not provided
            return "Invalid price";
        }
        PurchaseInfo purchaseInfo = new PurchaseInfo();
        if(!jsonObject.getString("date").equals("")) {
            try {
                DatеManager datеManager = new DatеManager();
                datеManager.setDateFromString(jsonObject.getString("date"));  //add date to purchase info
                purchaseInfo.setPurchaseDate(datеManager);
            } catch (ParseException e) { //if date format is wrong
                return "Invalid date format. Please use dd.mm.yyyy example - 14.5.2020";
            }
        }

        //3 - raboti
        PassiveResource newResource = new PassiveResource();                           //create new object
        newResource.setName(jsonObject.getString("name"));                         //add name
        purchaseInfo.setPrice(jsonObject.getDouble("price"));                      //add price to purchase info
        newResource.setPurchaseInfo(purchaseInfo);                                      //add purchase info
        if(!jsonObject.getString("description").equals(""))
            newResource.setDescription(jsonObject.getString("description"));        //set description if provided
        if(!jsonObject.getString("currency").equals(""))
            newResource.setCurrency(jsonObject.getString("currency"));              //add currency if provided
        if(!jsonObject.getString("currencySymbol").equals(""))
            newResource.setCurrencySymbol(jsonObject.getString("currencySymbol"));  //add currency symbol if provided
        newResource.setCurrentMarketPrice(jsonObject.getDouble("price"));           //add current market price equal to purchase price

        user.getAssets().addPassiveResource(newResource);                                //add new object to user

        //4 - raboti
        InsertIntoDb insertIntoDb = new InsertIntoDb("test");
        insertIntoDb.insertPassiveResource(user.getUserId(), newResource);

        return "Asset added successfully!";
    }

    public String addAsset(JSONObject jsonObject, User user){
        /*
        v zavisimost ot assettype:
        1. proveri dali tozi user go ima veche
            -ako go ima returnvash na usera che veche ima takuv
        2. proveri dali veche go ima v bazata danni
            -ako da zardi go ot bazata danni
        3. proveri dali APIa za suotvetniq resurs go namira
            -ako ne vurni greshka na useraa
        4. proveri dali stoinostite sa pravilni
                -ako ne sa pravilni vrushtash na usera che e vuvel nevalidni stoinosti
        5. dobavi v ram
        6. dobavi v baza danni
         */

        //1 - raboti
        if(user.getAssets().isAssetInList(jsonObject.getString("assetType"), jsonObject.getString("symbol"))){
            return "Passive resource with such a name already exists in your portfolio!";
        }

        //2 - raboti
        boolean isInDB = false;
        ReadFromDb readFromDb = new ReadFromDb("test");
        List<String> presentSymbols = readFromDb.readPresentAsset(jsonObject.getString("assetType"));
        if(presentSymbols.contains(jsonObject.getString("symbol"))){
            isInDB = true;
        }

        //3
        Stock stock = new Stock();
        Index index = new Index();
        Crypto crypto = new Crypto();
        Commodities commodity = new Commodities();
        switch (jsonObject.getString("assetType")){
            case "stock":
                try {
                    ParseStock parseStock = new ParseStock(jsonObject.getString("symbol"));
                    stock.setName(parseStock.getName());
                    stock.setSymbol(parseStock.getSymbol()); // == stock.setSymbol(jsonObject.getString("symbol");
                    stock.setDescription(parseStock.getDescription());
                    stock.setCurrentMarketPrice(parseStock.getRawCurrentPrice());
                    stock.setCurrency(parseStock.getCurrency());
                    stock.setCurrencySymbol(parseStock.getCurrencySymbol());
                    stock.setMarketOpen(parseStock.isMarketOpen());
                    stock.setExchangeName(parseStock.getExchangeName());
                    stock.setRecommendationKey(parseStock.getRecommendationKey());
                } catch (UnirestException e) {
                    //log
                    return "Such a stock doesn't exist";
                }
                break;
            case "index":
                /*
                Get information from API and turn into object
                if not found return to user
                 */
                index.setSymbol(jsonObject.getString("symbol"));
                index.setName(jsonObject.getString("symbol") + " ime ot api");
                index.setDescription("description from api");
                index.setCurrency("Dollar");
                index.setCurrencySymbol("$");
                break;
            case "crypto":
                /*
                Get information from API and turn into object
                if not found return to user
                 */
                crypto.setSymbol(jsonObject.getString("symbol"));
                crypto.setName(jsonObject.getString("symbol") + " ime ot api");
                crypto.setDescription("description from api");
                crypto.setCurrency("Dollar");
                crypto.setCurrencySymbol("$");
                break;
            case "commodity":
                /*
                Get information from API and turn into object
                if not found return to user
                 */
                commodity.setName(jsonObject.getString("symbol"));
                commodity.setDescription("description from api");
                commodity.setCurrency("Dollar");
                commodity.setCurrencySymbol("$");
                break;
        }

        //4 - raboti
        PurchaseInfo purchaseInfo = new PurchaseInfo();
        try {
            if (jsonObject.getDouble("price") <= 0) {
                return "Invalid price";
            }
            if(jsonObject.getDouble("quantity") <= 0){
                return "Invalid quantity";
            }
        }catch (JSONException e){ //if double not provided
            return "Please provide valid number!";
        }
        purchaseInfo.setPrice(jsonObject.getDouble("price"));
        stock.setAveragePurchasePrice(jsonObject.getDouble("price"));
        purchaseInfo.setQuantity(jsonObject.getDouble("quantity"));
        stock.setQuantityOwned(jsonObject.getDouble("quantity"));
        purchaseInfo.setStockSymbol(jsonObject.getString("symbol"));
        if(!jsonObject.getString("date").equals("")) {
            try {
                DatеManager datеManager = new DatеManager();
                datеManager.setDateFromString(jsonObject.getString("date"));  //add date to purchase info
                purchaseInfo.setPurchaseDate(datеManager);
            } catch (ParseException e) { //if date format is wrong
                return "Invalid date format. Please use dd.mm.yyyy example - 14.5.2020";
            }
        }
        stock.addPurchase(purchaseInfo);


        //5 + 6
        InsertIntoDb insert = new InsertIntoDb("test");
        switch (jsonObject.getString("assetType")){
            case "stock":
                user.getAssets().addStock(stock);
                insert.insertStock(stock);
                insert.insertStockPurchaseInfo(user.getUserId(), purchaseInfo);
                break;
            case "index":
                user.getAssets().addIndex(index);
                insert.insertIndex(index);
                insert.insertIndexPurchaseInfo(user.getUserId(), purchaseInfo);
                break;
            case "crypto":
                user.getAssets().addCrypto(crypto);
                insert.insertCrypto(crypto);
                insert.insertCryptoPurchaseInfo(user.getUserId(), purchaseInfo);
                break;
            case "commodity":
                user.getAssets().addCommodity(commodity);
                insert.insertCommodity(commodity);
                insert.insertCommodityPurchaseInfo(user.getUserId(), purchaseInfo);
                break;
        }

        return "success";
    }

    public String change2FA(String input, User user){
        input = input.replace("=","");

        if(input.equals("on") && user.getIs2FactorAuthenticationRequired() ||
                input.equals("off") && !user.getIs2FactorAuthenticationRequired()){
            return "fail";
        }
        if(input.equals("on")){
            user.setIs2FactorAuthenticationRequired(true);
        }
        else if(input.equals("off")){
            user.setIs2FactorAuthenticationRequired(false);
        }
        else {
            return "fail";
        }
        InsertIntoDb insert = new InsertIntoDb("test");
        insert.update2FA(user);
        return "success";
    }

    public String changeUsername(String newUsername, User user){
        newUsername = newUsername.replace("=","");
        if(newUsername.length() < 4 || newUsername.length() > 32){
                return "Invalid username";
        }
        user.setUserName(newUsername);
        InsertIntoDb insert = new InsertIntoDb("test");
        insert.updateUsername(user);
        return "success";
    }
    public String changeEmail(String newEmail, User user){
        newEmail = newEmail.replace("=","");
        newEmail = newEmail.replace("%40", "@");
        user.setEmail(newEmail);
        InsertIntoDb insert = new InsertIntoDb("test");
        insert.updateEmail(user);
        return "success";
    }
    public String changePassword(JSONObject jsonObject, User user){
        //check if current password is entered correctly
        //check validity of new password
        //check if passwords match
        //generate salt and hash for new password
        //add new salt and hash to db
        //add new password to ram
        //return values
        if(!user.checkPassword(jsonObject.getString("currentPassword"))){
            return "Incorrect current password!";
        }
        if(!jsonObject.getString("newPassword").equals(jsonObject.getString("newPasswordRepeat"))){
            return "Passwords don't match";
        }
        if(jsonObject.getString("newPassword").length() < 4){
            return "New password must be at least 4 characters long!";
        }
        List<String> newCredentials = user.generateSaltAndHash(jsonObject.getString("newPassword"));
        user.setPassword(newCredentials.get(0));
        user.setSalt(newCredentials.get(1));
        InsertIntoDb insert = new InsertIntoDb("test");
        insert.updatePassword(user);
        return "success";
    }

}
