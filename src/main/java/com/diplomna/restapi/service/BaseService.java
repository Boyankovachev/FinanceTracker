package com.diplomna.restapi.service;

import com.diplomna.api.alphavantage.AlphaVantageAPI;
import com.diplomna.api.stock.YahooFinanceAPI;
import com.diplomna.assets.AssetManager;
import com.diplomna.assets.finished.*;
import com.diplomna.assets.sub.PurchaseInfo;
import com.diplomna.database.DatabaseConnection;
import com.diplomna.date.DatеManager;
import com.diplomna.exceptions.AssetNotFoundException;
import com.diplomna.singleton.CurrentData;
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
    private final CurrentData currentData;
    private final DatabaseConnection dbConnection;

    public BaseService(){
        this.logger = LoggerFactory.getLogger(BaseService.class);
        dbConnection = new DatabaseConnection();
        currentData = CurrentData.getInstance();
    }

    public User setupUser(User user){
        /*
            user - object without asset manager
            initialize asset manager and add to user
            get notifications and add to user
            return user
         */
        //user.printUser();
        AssetManager assetManager = new AssetManager();

        assetManager.addStocks(getStocksByUserId(user.getUserId()));
        // for(Stock stock: assetManager.getAllStocks()){
        //     stock.printStock();
        // }
        assetManager.addPassiveResources(getPassiveResourcesByUserId(user.getUserId()));
        assetManager.addIndexList(getIndexByUserId(user.getUserId()));
        assetManager.addCryptoList(getCryptoByUserId(user.getUserId()));
        assetManager.addCommodities(getCommodityByUserId(user.getUserId()));
        user.setAssets(assetManager);
        //user.printUser();
        user.setNotifications(getNotificationsByUserId(user.getUserId()));
        return user;
    }

    public List<Notification> getNotificationsByUserId(int userId){
        //returns notification of user by id
        if(dbConnection.read().readNotificationsByUserId(userId) == null){
            return new ArrayList<>();
        }
        return dbConnection.read().readNotificationsByUserId(userId);
    }

    public List<Stock> getStocksByUserId(int userId){
        /*
            returns list of fully initialized stock objects,
            by user id with the purchase info list
         */
        //currentData.getAssetManager().print();
        List<Stock> stockPurchases = dbConnection.read().readStockPurchasesByUserId(userId);
        //System.out.println("\n--------------------\nStock purchases:");
        //for(Stock stock: stockPurchases){
        //    stock.printStock();
        //}
        List<Stock> stockBase = new ArrayList<>();
        List<String> ownedStocksSymbols = new ArrayList<>();

        for(Stock stock: stockPurchases){
            // Add all stock symbols the user owns
            // to a collection of strings
            if(!ownedStocksSymbols.contains(stock.getSymbol())){
                ownedStocksSymbols.add(stock.getSymbol());
            }
        }
        //System.out.println("\nStock base:");
        for(String symbol: ownedStocksSymbols){
            // iterate through the collection of strings
            // and add each stock to another collection
            stockBase.add(Stock.newInstance(currentData.getAssetManager().getStockBySymbol(symbol)));
            //currentData.getAssetManager().getStockBySymbol(symbol).printStock();
        }
        //currentData.getAssetManager().print();
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
        //currentData.getAssetManager().print();
        return stockBase;
    }

    public List<PassiveResource> getPassiveResourcesByUserId(int userId){
        //returns passive resources of user by id
        List<PassiveResource> passiveResources = dbConnection.read().readPassiveResourcesByUserId(userId);
        for (PassiveResource passiveResource : passiveResources) {
            passiveResource.calculatePercentChange();
        }
        return passiveResources;
    }

    public List<Index> getIndexByUserId(int userId){
        /*
            returns list of fully initialized index objects,
            by user id with the purchase info list
         */
        List<Index> indexPurchases = dbConnection.read().readIndexPurchasesByUserId(userId);
        List<Index> indexBase = new ArrayList<>();
        List<String> ownedIndexSymbols = new ArrayList<>();

        for(Index index: indexPurchases){
            // Add all index symbols the user owns
            // to a collection of strings
            if(!ownedIndexSymbols.contains(index.getSymbol())){
                ownedIndexSymbols.add(index.getSymbol());
            }
        }
        for(String symbol: ownedIndexSymbols){
            // iterate through the collection of strings
            // and add each index to another collection
            indexBase.add(Index.newInstance(currentData.getAssetManager().getIndexBySymbol(symbol)));
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
        List<Crypto> cryptoPurchases = dbConnection.read().readCryptoPurchaseByUserId(userId);
        List<Crypto> cryptoBase = new ArrayList<>();
        List<String> ownedCryptoSymbols = new ArrayList<>();

        for(Crypto crypto: cryptoPurchases){
            // Add all crypto symbols the user owns
            // to a collection of strings
            if(!ownedCryptoSymbols.contains(crypto.getSymbol())){
                ownedCryptoSymbols.add(crypto.getSymbol());
            }
        }
        for(String symbol: ownedCryptoSymbols){
            // iterate through the collection of strings
            // and add each index to another collection
            cryptoBase.add(Crypto.newInstance(currentData.getAssetManager().getCryptoBySymbol(symbol)));
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
        List<Commodities> commodityPurchases = dbConnection.read().readCommodityPurchaseInfoByUserId(userId);
        List<Commodities> commodityBase = new ArrayList<>();
        List<String> ownedCommodityNames = new ArrayList<>();

        for(Commodities commodity: commodityPurchases){
            // Add all commodity names the user owns
            // to a collection of strings
            if(!ownedCommodityNames.contains(commodity.getName())){
                ownedCommodityNames.add(commodity.getName());
            }
        }
        for(String name: ownedCommodityNames){
            // iterate through the collection of strings
            // and add each commodity to another collection
            commodityBase.add(Commodities.newInstance(currentData.getAssetManager().getCommodityByName(name)));
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
        //check if user owns passive resource with same name
        if(user.getAssets().isAssetInList("passive-resource", jsonObject.getString("name"))){
            return "Passive resource with such a name already exists in your portfolio!";
        }

        //check user input for validity
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
                datеManager.setDateFromString(jsonObject.getString("date"));
                purchaseInfo.setPurchaseDate(datеManager);
            } catch (ParseException e) { //if date format is wrong
                return "Invalid date format. Please use dd.mm.yyyy example - 14.5.2020";
            }
        }

        //create and add object to user object
        PassiveResource newResource = new PassiveResource();
        newResource.setName(jsonObject.getString("name"));
        purchaseInfo.setPrice(jsonObject.getDouble("price"));
        newResource.setPurchaseInfo(purchaseInfo);
        if(!jsonObject.getString("description").equals(""))
            newResource.setDescription(jsonObject.getString("description"));
        if(!jsonObject.getString("currency").equals(""))
            newResource.setCurrency(jsonObject.getString("currency"));
        if(!jsonObject.getString("currencySymbol").equals(""))
            newResource.setCurrencySymbol(jsonObject.getString("currencySymbol"));
        newResource.setCurrentMarketPrice(jsonObject.getDouble("price"));

        user.getAssets().addPassiveResource(newResource);

        //add to database
        dbConnection.add().insertPassiveResource(user.getUserId(), newResource);

        return "Asset added successfully!";
    }

    public String addAsset(JSONObject jsonObject, User user){
        /*
            add active asset to user
            jsonObject contains asset data
            return response (for client)
            if asset is not present:
                add to CurrentData singleton
                and to DB
         */
        //Check if user already has that investment
        if(user.getAssets().isAssetInList(jsonObject.getString("assetType"), jsonObject.getString("symbol"))){
            return "Investment already in portfolio!";
        }

        //Validate user input and
        //create the initial purchase info object
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

        //Check if resource exists in the program
        if(!currentData.getAssetManager().isAssetInList(jsonObject.getString("assetType"), jsonObject.getString("symbol"))){
            //If resource doesn't exist
            //Create object from API
            //Add to DB and RAM
            //Last- add to user
            AlphaVantageAPI alphaVantageAPI = new AlphaVantageAPI();
            switch (jsonObject.getString("assetType")){
                case "stock":
                    try {
                        Stock stock = new Stock();
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
                        stock.setCurrentMarketPrice(yahooFinanceAPI.getRawCurrentPrice());
                        stock.setMarketOpen(yahooFinanceAPI.isMarketOpen());
                        stock.setRecommendationKey(yahooFinanceAPI.getRecommendationKey());

                        dbConnection.add().insertStock(stock); //Add to DB
                        currentData.getAssetManager().addStock(stock); //Add to RAM
                    } catch (UnirestException | JSONException e) {
                        String errorMessage = "YahooFinanceAPI fail for symbol " + jsonObject.getString("symbol");
                        logger.error(errorMessage);
                        return "Such a stock doesn't exist";
                    }
                    break;
                case "index":
                    try {
                        Index index = new Index();
                        index.setSymbol(jsonObject.getString("symbol"));
                        HashMap<String, String> info = alphaVantageAPI.getInitialIndex(jsonObject.getString("symbol"));
                        index.setCurrency(info.get("currency"));
                        index.setName(info.get("name"));
                        index.setCurrentMarketPrice(Double.parseDouble(alphaVantageAPI.getIndexPrice(jsonObject.getString("symbol"))));

                        //information below not provided by available API
                        index.setDescription("description from api");
                        index.setExchangeName("exchange from api");
                        index.setCurrencySymbol("$");
                        index.setMarketOpen(true);

                        dbConnection.add().insertIndex(index); //Add to DB
                        currentData.getAssetManager().addIndex(index); //Add to RAM
                    } catch (UnirestException | JSONException e) {
                        String errorMessage = "AlphaVantageAPI fail for index " + jsonObject.getString("symbol");
                        logger.error(errorMessage);
                        return "Index matchers not found!";
                    }
                    break;
                case "crypto":
                    try {
                        Crypto crypto = new Crypto();
                        crypto.setSymbol(jsonObject.getString("symbol"));
                        HashMap<String, String> info = alphaVantageAPI.getCrypto(jsonObject.getString("symbol"));
                        crypto.setName(info.get("name"));
                        crypto.setCurrency(info.get("currency"));
                        crypto.setCurrencySymbol(info.get("currencySymbol"));
                        crypto.setCurrentMarketPrice(Double.parseDouble(info.get("price")));

                        //information below not provided by available API
                        crypto.setDescription("description from api");

                        dbConnection.add().insertCrypto(crypto); //Add to DB
                        currentData.getAssetManager().addCrypto(crypto); //Add to RAM
                    } catch (UnirestException | JSONException e) {
                        String errorMessage = "AlphaVantageAPI fail for crypto " + jsonObject.getString("symbol");
                        logger.error(errorMessage);
                        e.printStackTrace();
                        return "Crypto currency not found!";
                    }
                    break;
                case "commodity":
                    Commodities commodity = new Commodities();
                    // Free suitable API not found
                    // Simulate data from API
                    commodity.setName(jsonObject.getString("symbol"));
                    commodity.setDescription("description from api");
                    commodity.setCurrency("Dollar");
                    commodity.setCurrencySymbol("$");
                    commodity.setExchangeName("exchange from API");
                    commodity.setCurrentMarketPrice(0);

                    dbConnection.add().insertCommodity(commodity);
                    currentData.getAssetManager().addCommodity(commodity);
                    break;
            }
        }
        addAssetToUser(jsonObject, user, purchaseInfo);
        return "success";
    }
    private void addAssetToUser(JSONObject jsonObject, User user, PurchaseInfo purchaseInfo){
        /*
            Method adds asset to user object
            Called in addAsset method
         */
        switch (jsonObject.getString("assetType")){
            case "stock":
                Stock stock = currentData.getAssetManager().getStockBySymbol(jsonObject.getString("symbol"));
                stock.setAveragePurchasePrice(jsonObject.getDouble("price"));
                stock.setQuantityOwned(jsonObject.getDouble("quantity"));
                stock.calculatePercentChange();
                stock.addPurchase(purchaseInfo);
                user.getAssets().addStock(stock);
                dbConnection.add().insertStockPurchaseInfo(user.getUserId(), purchaseInfo);
                break;
            case "index":
                Index index = currentData.getAssetManager().getIndexBySymbol(jsonObject.getString("symbol"));
                index.setAveragePurchasePrice(jsonObject.getDouble("price"));
                index.setQuantityOwned(jsonObject.getDouble("quantity"));
                index.calculatePercentChange();
                index.addPurchase(purchaseInfo);
                user.getAssets().addIndex(index);
                dbConnection.add().insertIndexPurchaseInfo(user.getUserId(), purchaseInfo);
                break;
            case "crypto":
                Crypto crypto = currentData.getAssetManager().getCryptoBySymbol(jsonObject.getString("symbol"));
                crypto.setAveragePurchasePrice(jsonObject.getDouble("price"));
                crypto.setQuantityOwned(jsonObject.getDouble("quantity"));
                crypto.calculatePercentChange();
                crypto.addPurchase(purchaseInfo);
                user.getAssets().addCrypto(crypto);
                dbConnection.add().insertCryptoPurchaseInfo(user.getUserId(), purchaseInfo);
                break;
            case "commodity":
                Commodities commodity = currentData.getAssetManager().getCommodityByName(jsonObject.getString("symbol"));
                commodity.setAveragePurchasePrice(jsonObject.getDouble("price"));
                commodity.setQuantityOwned(jsonObject.getDouble("quantity"));
                commodity.calculatePercentChange();
                commodity.addPurchase(purchaseInfo);
                user.getAssets().addCommodity(commodity);
                dbConnection.add().insertCommodityPurchaseInfo(user.getUserId(), purchaseInfo);
                break;
        }
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
        dbConnection.add().update2FA(user);
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
        dbConnection.add().updateUsername(user);
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
        dbConnection.add().updateEmail(user);
        return "success";
    }
    public String changePassword(JSONObject jsonObject, User user){
        /*
            Change user password
            jsonObject contains user input
            return response (for client)
         */
        if(!user.checkPassword(jsonObject.getString("currentPassword"))){
            return "Incorrect current password!";
        }
        if(!jsonObject.getString("newPassword").equals(jsonObject.getString("newPasswordRepeat"))){
            return "Passwords don't match";
        }
        if(jsonObject.getString("newPassword").length() < 4){
            return "New password must be at least 4 characters long!";
        }
        HashMap<String, String> newCredentials = user.generateSaltAndHash(jsonObject.getString("newPassword"));
        user.setPassword(newCredentials.get("hash"));
        user.setSalt(newCredentials.get("salt"));
        dbConnection.add().updatePassword(user);
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
        dbConnection.add().insertNotification(user.getUserId(), newNotification);
        return "success";
    }

    public String removeAsset(JSONObject jsonObject, User user){
        /*
            Removes asset from user portfolio
            jsonObject contains asset information
            return response (for client)
         */

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
                dbConnection.delete().deleteAllStockPurchases(user.getUserId(), jsonObject.getString("assetName"));
                break;
            case "index":
                dbConnection.delete().deleteIndexPurchases(user.getUserId(), jsonObject.getString("assetName"));
                break;
            case "crypto":
                dbConnection.delete().deleteCryptoPurchases(user.getUserId(), jsonObject.getString("assetName"));
                break;
            case "commodity":
                dbConnection.delete().deleteCommodityPurchases(user.getUserId(), jsonObject.getString("assetName"));
                break;
            case "passive-resource":
                dbConnection.delete().deletePassiveResource(user.getUserId(), jsonObject.getString("assetName"));
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
        try {
            dbConnection.add().updatePassiveResourceCurrentPrice(user.getUserId(), jsonObject.getDouble("price"), jsonObject.getString("name"));
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

        try {
            dbConnection.delete().deleteNotification(user.getUserId(), notificationName);
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

    public HashMap<String, String> addPurchaseToActiveAsset(JSONObject jsonObject, User user){
        /*
            Adds purchase to any active asset
            returns response for the client
         */
        HashMap<String, String> map = new HashMap<>();

        PurchaseInfo purchaseInfo = new PurchaseInfo(jsonObject.getDouble("price"), jsonObject.getDouble("quantity"));
        if(jsonObject.getString("date") != null && !jsonObject.getString("date").equals("")){
            try {
                DatеManager datеManager = new DatеManager();
                datеManager.setDateFromString(jsonObject.getString("date"));
                purchaseInfo.setPurchaseDate(datеManager);
                map.put("date", datеManager.getDateAsString());
            } catch (ParseException e) {
                map.put("success", "Wrong date format. Please use day.month.year!");
                return map;
            }
        }
        map.put("price", String.valueOf(jsonObject.getDouble("price")));
        map.put("quantity", String.valueOf(jsonObject.getDouble("quantity")));


        if(jsonObject.getString("assetSymbol") != null && !jsonObject.getString("assetSymbol").equals("")) {
            switch (jsonObject.getString("assetType")) {
                case "stock":
                    purchaseInfo.setStockSymbol(jsonObject.getString("assetSymbol"));

                    //add new purchase to DB
                    dbConnection.add().insertStockPurchaseInfo(user.getUserId(), purchaseInfo);
                    //add new purchase to user
                    user.getAssets().addPurchaseToResource(jsonObject.getString("assetType"), jsonObject.getString("assetSymbol"), purchaseInfo);
                    break;
                case "index":
                    purchaseInfo.setStockSymbol(jsonObject.getString("assetSymbol"));

                    //add new purchase to DB
                    dbConnection.add().insertIndexPurchaseInfo(user.getUserId(), purchaseInfo);
                    //add new purchase to user
                    user.getAssets().addPurchaseToResource(jsonObject.getString("assetType"), jsonObject.getString("assetSymbol"), purchaseInfo);
                    break;
                case "crypto":
                    purchaseInfo.setStockSymbol(jsonObject.getString("assetSymbol"));

                    //add new purchase to DB
                    dbConnection.add().insertCryptoPurchaseInfo(user.getUserId(), purchaseInfo);
                    //add new purchase to RAM
                    user.getAssets().addPurchaseToResource(jsonObject.getString("assetType"), jsonObject.getString("assetSymbol"), purchaseInfo);
                case "commodity":
                    //commodities have no symbol and are referenced by name
                    purchaseInfo.setStockSymbol(jsonObject.getString("assetName"));

                    //add new purchase to DB
                    dbConnection.add().insertCommodityPurchaseInfo(user.getUserId(), purchaseInfo);
                    //add new purchase to RAM
                    user.getAssets().addPurchaseToResource(jsonObject.getString("assetType"), jsonObject.getString("assetName"), purchaseInfo);
                    break;
                default:
                    String errorString = "Purchase not registered for user with id " + user.getUserId()
                            + " .Asset Type not found!";
                    logger.error(errorString);
                    map.put("success", "An error has occurred! Purchase not registered.");
                    return map;
            }
            map.put("success", "success");
        } else {
            map.put("success", "An error has occurred! Purchase not registered.");
        }
        return map;
    }

}
