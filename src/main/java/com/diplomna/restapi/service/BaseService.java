package com.diplomna.restapi.service;

import com.diplomna.api.alphavantage.AlphaVantageAPI;
import com.diplomna.api.stock.ParseStock;
import com.diplomna.assets.AssetManager;
import com.diplomna.assets.finished.*;
import com.diplomna.assets.sub.Asset;
import com.diplomna.assets.sub.PurchaseInfo;
import com.diplomna.database.insert.InsertIntoDb;
import com.diplomna.database.read.ReadFromDb;
import com.diplomna.date.DatеManager;
import com.diplomna.users.sub.AssetType;
import com.diplomna.users.sub.Notification;
import com.diplomna.users.sub.User;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.jsontype.impl.AsExistingPropertyTypeSerializer;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mysql.cj.CacheAdapter;
import com.sun.source.tree.LabeledStatementTree;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class BaseService {

    private final Logger logger;
    public BaseService(){
        this.logger = LoggerFactory.getLogger(BaseService.class);
    }

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
                stockBase.get(i).setCurrentMarketPrice(parseStock.getRawCurrentPrice());
                stockBase.get(i).setMarketOpen(parseStock.isMarketOpen());
                stockBase.get(i).setRecommendationKey(parseStock.getRecommendationKey());
            } catch (UnirestException e) {
                String errorMessage = "YahooFinanceAPI fail for symbol " + stockBase.get(i).getSymbol();
                logger.error(errorMessage);
                e.printStackTrace();
            }
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

            AlphaVantageAPI alphaVantageAPI = new AlphaVantageAPI();
            try {
                alphaVantageAPI.setIndex(indexBase.get(i).getSymbol());
                indexBase.get(i).setCurrentMarketPrice((Double.parseDouble(alphaVantageAPI.getIndexPrice())));

                //information below not provided by available API
                indexBase.get(i).setMarketOpen(true);
            } catch (UnirestException e) {
                String errorMessage = "AlphaVantageAPI fail for symbol " + indexBase.get(i).getSymbol();
                logger.error(errorMessage);
                e.printStackTrace();
            }
        }

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

            AlphaVantageAPI alphaVantageAPI = new AlphaVantageAPI();
            try {
                alphaVantageAPI.setCrypto(cryptoBase.get(i).getSymbol());
                cryptoBase.get(i).setCurrentMarketPrice(Double.parseDouble(alphaVantageAPI.getCrypto().get("price")));
            } catch (UnirestException e) {
                String errorMessage = "AlphaVantageAPI fail for crypto " + cryptoBase.get(i).getSymbol();
                logger.error(errorMessage);
                e.printStackTrace();
                //LOG ERROR - CRYPTO NOT FOUND BY API
            }
        }
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
        AlphaVantageAPI alphaVantageAPI = new AlphaVantageAPI();
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
                } catch (UnirestException e) {
                    String errorMessage = "AlphaVantageAPI fail for index " + jsonObject.getString("symbol");
                    logger.error(errorMessage);
                    return "Index matchers not found!";
                }
                //information below not provided by available API
                index.setDescription("description from api");
                index.setExchangeName("exchange from api");
                index.setCurrencySymbol("$");
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
                } catch (UnirestException e) {
                    String errorMessage = "AlphaVantageAPI fail for crypto " + jsonObject.getString("symbol");
                    logger.error(errorMessage);
                    e.printStackTrace();
                }
                //information below not provided by available API
                crypto.setDescription("description from api");
                break;
            case "commodity":
                /*
                Get information from API and turn into object
                if not found return to user
                 */
                // Free suitable API not found
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

        for(Notification notification: user.getNotifications()){
            if(newNotification.isNotificationSimilar(notification)){
                return "Notification duplicate!";
            }
        }
        user.addNotification(newNotification);
        InsertIntoDb insert = new InsertIntoDb("test");
        insert.insertNotification(user.getUserId(), newNotification);
        return "success";
    }
}
