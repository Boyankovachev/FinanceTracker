package com.diplomna.restapi.service;

import com.diplomna.api.alphavantage.AlphaVantageAPI;
import com.diplomna.api.stock.YahooFinanceAPI;
import com.diplomna.assets.AssetManager;
import com.diplomna.assets.finished.*;
import com.diplomna.assets.sub.PurchaseInfo;
import com.diplomna.database.delete.DeleteFromDb;
import com.diplomna.database.insert.InsertIntoDb;
import com.diplomna.database.read.ReadFromDb;
import com.diplomna.date.DatеManager;
import com.diplomna.exceptions.AssetNotFoundException;
import com.diplomna.pojo.GraphInfo;
import com.diplomna.users.sub.AssetType;
import com.diplomna.users.sub.Notification;
import com.diplomna.users.sub.User;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

@Service
public class BaseService {

    private final Logger logger;
    private String databaseName;
    public BaseService(){
        this.logger = LoggerFactory.getLogger(BaseService.class);
        databaseName = "test";
    }

    public User setupUser(User user){
        /*
            user - object without asset manager
            initialize asset manager and add to user
            get notifications and add to user
            return user
         */
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

    public List<Notification> getNotificationsByUserId(int userId){
        //returns notification of user by id
        ReadFromDb readFromDb = new ReadFromDb("test");
        if(readFromDb.readNotificationsByUserId(userId) == null){
            return new ArrayList<>();
        }
        return readFromDb.readNotificationsByUserId(userId);
    }

    public List<Stock> getStocksByUserId(int userId){
        /*
            returns list of fully initialized stock objects,
            by user id with the purchase info list
         */
        ReadFromDb readFromDb = new ReadFromDb(databaseName);
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
            // read stocks from DB
            stockBase.add(readFromDb.readStockBySymbol(symbol));
        }

        int i,j;
        for(i=0; i<stockBase.size(); i++){
            for(j=0; j<stockPurchases.size(); j++){
                if(stockBase.get(i).getSymbol().equals(stockPurchases.get(j).getSymbol())){
                    stockBase.get(i).addPurchase(stockPurchases.get(j).getFirstPurchase());
                }
            }
            stockBase.get(i).calculateQuantityOwned();
            stockBase.get(i).calculateAveragePurchasePrice();
            stockBase.get(i).calculatePercentChange();
        }

        return stockBase;
    }

    public List<PassiveResource> getPassiveResourcesByUserId(int userId){
        //returns passive resources of user by id
        ReadFromDb readFromDb = new ReadFromDb("test");
        List<PassiveResource> passiveResources = readFromDb.readPassiveResourcesByUserId(userId);
        for(int i=0; i<passiveResources.size(); i++){
            passiveResources.get(i).calculatePercentChange();
        }
        return passiveResources;
    }

    public List<Index> getIndexByUserId(int userId){
        /*
            returns list of fully initialized index objects,
            by user id with the purchase info list
         */
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
            indexBase.get(i).calculatePercentChange();
        }

        return indexBase;
    }

    public List<Crypto> getCryptoByUserId(int userId){
        /*
            returns list of fully initialized crypto objects,
            by user id with the purchase info list
         */
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
            cryptoBase.get(i).calculatePercentChange();
        }
        return cryptoBase;
    }

    public List<Commodities> getCommodityByUserId(int userId){
        /*
            returns list of fully initialized commodity objects,
            by user id with the purchase info list
         */
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
            commodityBase.get(i).calculatePercentChange();
        }
        return commodityBase;
    }

    public String addPassiveAsset(JSONObject jsonObject, User user){
        /*
            add passive asset to user
            jsonObject contains passive asset data
            return response (for client)
         */


        /* MAHNI TOVA POSLE (i oprai)
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
            add active asset to user
            jsonObject contains asset data
            return response (for client)
         */

        /*
        v zavisimost ot assettype:
        1. proveri dali tozi user go ima veche
            -ako go ima returnvash na usera che veche ima takuv
        2. proveri dali veche go ima v bazata danni          -tva go praa posle
            -ako da zardi go ot bazata danni
        3. proveri dali APIa za suotvetniq resurs go namira
            -ako ne vurni greshka na useraa
        4. proveri dali stoinostite sa pravilni
                -ako ne sa pravilni vrushtash na usera che e vuvel nevalidni stoinosti
        5. dobavi v ram
        6. dobavi v baza danni
         */

        //1 - raboti
        //Check if user already owns such a resource
        if(user.getAssets().isAssetInList(jsonObject.getString("assetType"), jsonObject.getString("symbol"))){
            return "Passive resource with such a name already exists in your portfolio!";
        }

        ReadFromDb readFromDb = new ReadFromDb("test");
        InsertIntoDb insert = new InsertIntoDb("test");

        //3
        Stock stock = new Stock();
        Index index = new Index();
        Crypto crypto = new Crypto();
        Commodities commodity = new Commodities();
        AlphaVantageAPI alphaVantageAPI = new AlphaVantageAPI();
        switch (jsonObject.getString("assetType")){
            case "stock":
                //check if in DB
                Stock tempStock = readFromDb.readStockBySymbol(jsonObject.getString("symbol"));
                if(tempStock == null) {
                    System.out.println("load from stock not from DB not from api");
                }
                try {
                    YahooFinanceAPI yahooFinanceAPI = new YahooFinanceAPI(jsonObject.getString("symbol"));
                    stock.setName(yahooFinanceAPI.getName());
                    stock.setSymbol(yahooFinanceAPI.getSymbol()); // == stock.setSymbol(jsonObject.getString("symbol");
                    stock.setDescription(yahooFinanceAPI.getDescription());
                    stock.setCurrentMarketPrice(yahooFinanceAPI.getRawCurrentPrice());
                    stock.setCurrency(yahooFinanceAPI.getCurrency());
                    stock.setCurrencySymbol(yahooFinanceAPI.getCurrencySymbol());
                    stock.setMarketOpen(yahooFinanceAPI.isMarketOpen());
                    stock.setExchangeName(yahooFinanceAPI.getExchangeName());
                    stock.setRecommendationKey(yahooFinanceAPI.getRecommendationKey());
                    stock.setCurrentMarketPrice(yahooFinanceAPI.getRawCurrentPrice()); //latest changes
                    stock.setMarketOpen(yahooFinanceAPI.isMarketOpen());               //latest changes
                    stock.setRecommendationKey(yahooFinanceAPI.getRecommendationKey());//latest changes
                } catch (UnirestException e) {
                    String errorMessage = "YahooFinanceAPI fail for symbol " + jsonObject.getString("symbol");
                    logger.error(errorMessage);
                    return "Such a stock doesn't exist";
                }
                break;
            case "index":
                index.setSymbol(jsonObject.getString("symbol"));
                try {
                    alphaVantageAPI.setInitialIndex(jsonObject.getString("symbol"));
                    HashMap<String, String> info = alphaVantageAPI.getInitialIndex();
                    index.setCurrency(info.get("currency"));
                    index.setName(info.get("name"));
                    alphaVantageAPI.setIndex(jsonObject.getString("symbol"));                    //latest changes
                    index.setCurrentMarketPrice(Double.parseDouble(alphaVantageAPI.getIndexPrice()));//latest changes
                } catch (UnirestException e) {
                    String errorMessage = "AlphaVantageAPI fail for index " + jsonObject.getString("symbol");
                    logger.error(errorMessage);
                    return "Index matchers not found!";
                }
                //information below not provided by available API
                index.setDescription("description from api");
                index.setExchangeName("exchange from api");
                index.setCurrencySymbol("$");
                index.setMarketOpen(true);  //latest changes
                break;
            case "crypto":
                /*
                Get information from API and turn into object
                if not found return to user
                 */
                crypto.setSymbol(jsonObject.getString("symbol"));
                try {
                    alphaVantageAPI.setCrypto(jsonObject.getString("symbol"));
                    HashMap<String, String> info = alphaVantageAPI.getCrypto();
                    crypto.setName(info.get("name"));
                    crypto.setCurrency(info.get("currency"));
                    crypto.setCurrencySymbol(info.get("currencySymbol"));
                    crypto.setCurrentMarketPrice(Double.parseDouble(info.get("price")));  //latest changes
                } catch (UnirestException e) {
                    String errorMessage = "AlphaVantageAPI fail for crypto " + jsonObject.getString("symbol");
                    logger.error(errorMessage);
                    e.printStackTrace();
                }
                //information below not provided by available API
                crypto.setDescription("description from api");
                break;
            case "commodity":
                // Free suitable API not found
                // Simulate data from API
                commodity.setName(jsonObject.getString("symbol"));
                commodity.setDescription("description from api");
                commodity.setCurrency("Dollar");
                commodity.setCurrencySymbol("$");
                commodity.setExchangeName("exchange from API");
                commodity.setCurrentMarketPrice(0);
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
        purchaseInfo.setQuantity(jsonObject.getDouble("quantity"));
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

        //5 + 6
        switch (jsonObject.getString("assetType")){
            case "stock":
                stock.setAveragePurchasePrice(jsonObject.getDouble("price"));
                stock.setQuantityOwned(jsonObject.getDouble("quantity"));
                stock.addPurchase(purchaseInfo);


                user.getAssets().addStock(stock);
                //check if already in DB, if yes - don't add. Do for all asset types!
                Stock tempStock = readFromDb.readStockBySymbol(stock.getSymbol());
                if(tempStock == null) {
                    insert.insertStock(stock);
                }
                insert.insertStockPurchaseInfo(user.getUserId(), purchaseInfo);
                break;
            case "index":
                index.setAveragePurchasePrice(jsonObject.getDouble("price"));
                index.setQuantityOwned(jsonObject.getDouble("quantity"));
                index.addPurchase(purchaseInfo);

                user.getAssets().addIndex(index);
                //check if already in DB, if yes - don't add. Do for all asset types!
                Index tempIndex = readFromDb.readIndexBySymbol(index.getSymbol());
                if (tempIndex == null){
                    insert.insertIndex(index);
                }
                insert.insertIndexPurchaseInfo(user.getUserId(), purchaseInfo);
                break;
            case "crypto":
                crypto.setAveragePurchasePrice(jsonObject.getDouble("price"));
                crypto.setQuantityOwned(jsonObject.getDouble("quantity"));
                crypto.addPurchase(purchaseInfo);

                user.getAssets().addCrypto(crypto);
                //check if already in DB, if yes - don't add. Do for all asset types!
                Crypto tempCrypto = readFromDb.readCryptoBySymbol(crypto.getSymbol());
                if (tempCrypto == null){
                    insert.insertCrypto(crypto);
                }
                insert.insertCryptoPurchaseInfo(user.getUserId(), purchaseInfo);
                break;
            case "commodity":
                commodity.setAveragePurchasePrice(jsonObject.getDouble("price"));
                commodity.setQuantityOwned(jsonObject.getDouble("quantity"));
                commodity.addPurchase(purchaseInfo);

                user.getAssets().addCommodity(commodity);
                //check if already in DB, if yes - don't add. Do for all asset types!
                Commodities tempCommodity = readFromDb.readCommodityByCommodityName(commodity.getName());
                if (tempCommodity == null){
                    insert.insertCommodity(commodity);
                }
                insert.insertCommodityPurchaseInfo(user.getUserId(), purchaseInfo);
                break;
        }

        return "success";
    }

    public String change2FA(String input, User user){
        /*
            Change user 2fa settings
            return response (for client)
         */

        input = input.replace("=","");  // response is with on= or off=   '=' not needed

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
        /*
            Change user username
            return response (for client)
         */

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
        /*
            Change user email
            return response (for client)
         */

        newEmail = newEmail.replace("=","");
        newEmail = newEmail.replace("%40", "@");
        user.setEmail(newEmail);
        InsertIntoDb insert = new InsertIntoDb("test");
        insert.updateEmail(user);
        return "success";
    }
    public String changePassword(JSONObject jsonObject, User user){
        /*
            Change user password
            jsonObject contains user input
            return response (for client)
         */

        //algorituma (proveri go posle i toq dali e adekvaten)
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
        logger.info("user " + user.getUserName() + " changed his password");
        return "success";
    }

    public List<Notification> getGlobalNotifications(User user){
        /*
        Return list of global notification's if user has resources of that asset type
        (method used for drop down notification menu on notifications html page)
         */
        List<Notification> list = new ArrayList<>();
        list.add(new Notification(AssetType.global, "Global notification"));
        if(!user.getAssets().getAllStocks().isEmpty())list.add(new Notification(AssetType.stock, "All stocks"));
        if(!user.getAssets().getAllIndex().isEmpty())list.add(new Notification(AssetType.index, "All index funds"));
        if(!user.getAssets().getCrypto().isEmpty())list.add(new Notification(AssetType.crypto, "All crypto currencies"));
        if(!user.getAssets().getCommodities().isEmpty())list.add(new Notification(AssetType.commodity, "All commodities"));
        return list;
    }

    public String addNotification(JSONObject jsonObject, User user){
        /*
            add notification to user
            jsonObject contains notification information
            return response (for client)
         */
        Notification newNotification = new Notification();

        if(jsonObject.getString("name").equals("") || jsonObject.getString("priceTarget").equals("")){
            return "Invalid notification";
        }

        if(jsonObject.getString("notificationType").equals("global")){
            AssetType assetType = AssetType.valueOf(jsonObject.getString("notificationAsset"));
            newNotification.setAssetType(assetType);
            if(!assetType.toString().equals("global")){
                newNotification.setAssetTypeSettings(true);
            }
        }
        else{
            AssetType assetType = AssetType.valueOf(jsonObject.getString("notificationType"));
            newNotification.setAssetType(assetType);
            newNotification.setAssetTypeSettings(false);
            newNotification.setNotificationTarget(jsonObject.getString("notificationAsset"));
        }
        newNotification.setNotificationName(jsonObject.getString("name"));
        newNotification.setNotificationPrice(jsonObject.getDouble("priceTarget"));

        for (Notification notification : user.getNotifications()) {
            if (newNotification.isNotificationSimilar(notification)) {
                return "Notification duplicate!";
            }
        }

        user.addNotification(newNotification);
        InsertIntoDb insert = new InsertIntoDb("test");
        insert.insertNotification(user.getUserId(), newNotification);
        return "success";
    }


    public String removeAsset(JSONObject jsonObject, User user){
        /*
            Removes asset from user portfolio
            jsonObject contains asset information
            return response (for client)
         */

        DeleteFromDb deleteFromDb = new DeleteFromDb("test");
        try {
            user.getAssets().removeAsset(jsonObject.getString("assetType"), jsonObject.getString("assetName"));
        } catch (AssetNotFoundException e) {
            e.printStackTrace();
            logger.error(jsonObject.getString("assetType") + ": "
                    + jsonObject.getString("assetName") +
                    " not found for user with id" + String.valueOf(user.getUserId()));
            return "Unexpected error occurred! Couldn't remove asset! 1";
        }
        switch (jsonObject.getString("assetType")){
            case "stock":
                deleteFromDb.deleteAllStockPurchases(user.getUserId(), jsonObject.getString("assetName"));
                break;
            case "index":
                deleteFromDb.deleteIndexPurchases(user.getUserId(), jsonObject.getString("assetName"));
                break;
            case "crypto":
                deleteFromDb.deleteCryptoPurchases(user.getUserId(), jsonObject.getString("assetName"));
                break;
            case "commodity":
                deleteFromDb.deleteCommodityPurchases(user.getUserId(), jsonObject.getString("assetName"));
                break;
            case "passive-resource":
                deleteFromDb.deletePassiveResource(user.getUserId(), jsonObject.getString("assetName"));
                break;
            default:
                return "Unexpected error occurred! Couldn't remove asset!";
        }
        return "success";
    }

    public String changePassiveResourcePrice(JSONObject jsonObject, User user) {
        /*
            Change price of a passive resource
            jsonObject contains user input data
            return response (for client)
         */
        if( user.getAssets().getPassiveResourceByName(jsonObject.getString("name")) == null){
            return "Failed to locate passive resource";
        }
        user.getAssets().getPassiveResourceByName(jsonObject.getString("name")).setCurrentMarketPrice(jsonObject.getDouble("price"));
        InsertIntoDb update = new InsertIntoDb("test");
        try {
            update.updatePassiveResourceCurrentPrice(user.getUserId(), jsonObject.getDouble("price"), jsonObject.getString("name"));
        } catch (SQLException throwables) {
            return "Failed to update passive resource!";
        }
        return "success";
    }

    public String removeNotification(String notificationName, User user){
        /*
            Removes notification from user
            jsonObject contains asset information
            return response (for client)
         */

        DeleteFromDb deleteFromDb = new DeleteFromDb("test");
        try {
            deleteFromDb.deleteNotification(user.getUserId(), notificationName);
        } catch (SQLException throwables) {
            String errorMessage = "Delete Notification fail for notification: " + notificationName
                    + " for user with Id: " + user.getUserId();
            logger.error(errorMessage);
            throwables.printStackTrace();
            return "Failed to remove notification!";
        }
        if(user.removeNotificationByName(notificationName)){
            return "success";
        }
        else {
            return "Failed to remove notification!";
        }
    }

    public List<GraphInfo> getStockGraphInfo(String stockSymbol){
        /*
            Return stock historical data list
         */
        List<GraphInfo> graphInfoList = new ArrayList<>();
        AlphaVantageAPI alphaVantageAPI = new AlphaVantageAPI();
        try {
            JSONObject jsonObject = alphaVantageAPI.getStockTimeSeries(stockSymbol);

            Iterator<String> keys = jsonObject.keys();

            while(keys.hasNext()) { // iterating through JSONObject
                String key = keys.next();
                if (jsonObject.get(key) instanceof JSONObject) {
                    String[] temp = key.split("-");
                    GraphInfo graphInfo = new GraphInfo(Integer.parseInt(temp[2]),
                            Integer.parseInt(temp[1]), Integer.parseInt(temp[0]),
                                   Double.parseDouble(jsonObject.getJSONObject(key).get("4. close").toString()));

                    graphInfoList.add(graphInfo);
                }
            }

            //api provides data mixed up
            //sort it
            for(int j=0; j<graphInfoList.size()-1; j++) {
                for (int i = 0; i < graphInfoList.size() - 1; i++) {
                    if (graphInfoList.get(i).getYear() > graphInfoList.get(i + 1).getYear()){
                        swapGraphInfoList(graphInfoList, i);
                    }
                }
            }
            for(int j=0; j<graphInfoList.size()-1; j++) {
                for (int i = 0; i < graphInfoList.size() - 1; i++) {
                    if (graphInfoList.get(i).getMonth() > graphInfoList.get(i + 1).getMonth()){
                        swapGraphInfoList(graphInfoList, i);
                    }
                }
            }
            for(int j=0; j<graphInfoList.size()-1; j++) {
                for (int i = 0; i < graphInfoList.size() - 1; i++) {
                    if (graphInfoList.get(i).getYear() > graphInfoList.get(i + 1).getYear()){
                        swapGraphInfoList(graphInfoList, i);
                    }
                }
            }

            return graphInfoList;
        } catch (UnirestException e) {
            e.printStackTrace();
            String errorMessage = "AlphaVantage api fail monthly data for chart for symbol: " + stockSymbol;
            logger.error(errorMessage);
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            String errorMessage = "AlphaVantage api json fail monthly data for chart for symbol: " + stockSymbol;
            logger.error(errorMessage);
        }
        return null;
    }
    private void swapGraphInfoList(List<GraphInfo> graphInfoList, int i){
        /*
            swap objects in graph info list
            from i and i + 1
            used in getStockGraphInfo
         */
        GraphInfo temp = graphInfoList.get(i);
        graphInfoList.set(i, graphInfoList.get(i+1));
        graphInfoList.set(i+1, temp);
    }

}
