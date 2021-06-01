package com.diplomna.update;

import com.diplomna.api.alphavantage.AlphaVantageAPI;
import com.diplomna.api.stock.YahooFinanceAPI;
import com.diplomna.assets.AssetManager;
import com.diplomna.assets.finished.Commodities;
import com.diplomna.assets.finished.Crypto;
import com.diplomna.assets.finished.Index;
import com.diplomna.assets.finished.Stock;
import com.diplomna.assets.sub.Asset;
import com.diplomna.database.DatabaseConnection;
import com.diplomna.database.delete.DeleteFromDb;
import com.diplomna.database.insert.InsertIntoDb;
import com.diplomna.database.read.ReadFromDb;
import com.diplomna.email.EmailService;
import com.diplomna.graph.GraphInfoHolder;
import com.diplomna.graph.GraphService;
import com.diplomna.restapi.service.BaseService;
import com.diplomna.singleton.CurrentData;
import com.diplomna.users.UserManager;
import com.diplomna.users.sub.AssetType;
import com.diplomna.users.sub.Notification;
import com.diplomna.users.sub.User;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;

@Service
public class UpdateService {

    @Autowired
    private final BaseService baseService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private Environment env;

    @Autowired
    private DatabaseConnection dbConnection;

    private final Logger logger;

    private final CurrentData currentData;

    public UpdateService(){
        this.logger = LoggerFactory.getLogger(BaseService.class);
        this.baseService = new BaseService();
        currentData = CurrentData.getInstance();
    }

    public AssetManager setupInitialAssetManager(){
        /*
            The function runs once on startup.
            Reads all assets and returns AssetManager object
            for the CurrentData singleton.
         */
        AssetManager assetManager = new AssetManager();

        List<Stock> stocks = new ArrayList<>();
        List<Index> indexList = new ArrayList<>();
        List<Crypto> cryptos = new ArrayList<>();
        List<Commodities> commodities = new ArrayList<>();
        try {
            stocks = dbConnection.read().readAllStocks();
            indexList = dbConnection.read().readAllIndex();
            cryptos = dbConnection.read().readAllCrypto();
            commodities = dbConnection.read().readAllCommodities();

            assetManager.addStocks(stocks);
            assetManager.addIndexList(indexList);
            assetManager.addCryptoList(cryptos);
            assetManager.addCommodities(commodities);

            return assetManager;
        } catch (SQLException throwables) {
            String errorMessage = "Read all assets from" +
                    " DB failed in setupInitialAssetManager";
            logger.error(errorMessage);
            throwables.printStackTrace();
        }
        return null;

    }

    public void updateAllAssets(){
        /*
            updates all assets
         */
        updateStocks();
        updateIndex();
        updateCommodity();
        updateCrypto();
    }

    private void updateStocks(){
        /*
            updates all stock data
         */
        List<Stock> stocks = currentData.getAssetManager().getAllStocks();
        YahooFinanceAPI yahooFinanceAPI = new YahooFinanceAPI();
        int i;
        for(i=0; i<stocks.size(); i++){

            if(Objects.equals(env.getProperty("api.update"), "on")) {
                try {
                    yahooFinanceAPI.setStockBySymbol(stocks.get(i).getSymbol());
                    stocks.get(i).setCurrentMarketPrice(yahooFinanceAPI.getRawCurrentPrice());
                    stocks.get(i).setMarketOpen(yahooFinanceAPI.isMarketOpen());
                    stocks.get(i).setRecommendationKey(yahooFinanceAPI.getRecommendationKey());
                } catch (UnirestException e) {
                    String errorMessage = "YahooFinanceAPI fail for symbol "
                            + stocks.get(i).getSymbol();
                    logger.error(errorMessage);
                    e.printStackTrace();
                }
            }
            else if(Objects.equals(env.getProperty("api.update"), "off")){
                stocks.get(i).setCurrentMarketPrice(generateRandom(stocks.get(i).getCurrentMarketPrice()));
            }

        }
        for(Stock stock: stocks){
            try {
                dbConnection.add().updateStockApiData(stock);
            } catch (SQLException throwables) {
                String errorMessage = "DB fail for update stock API data fail for symbol "
                        + stock.getSymbol();
                logger.error(errorMessage);
                throwables.printStackTrace();
            }
        }
    }

    private void updateIndex(){
        /*
            updates all stock data
         */
        List<Index> indexList = currentData.getAssetManager().getAllIndex();

        AlphaVantageAPI alphaVantageAPI = new AlphaVantageAPI();
        int i;
        for(i=0; i<indexList.size(); i++){

            if(Objects.equals(env.getProperty("api.update"), "on")){
                try {
                    try {
                        double indexCurrentPrice = Double.parseDouble(alphaVantageAPI.getIndexPrice(indexList.get(i).getSymbol()));
                        indexList.get(i).setCurrentMarketPrice(indexCurrentPrice);
                    }catch (JSONException e){
                        indexList.get(i).setCurrentMarketPrice(0);
                        String errorMessage = "AlphaVantageAPI JSON index fail for symbol " + indexList.get(i).getSymbol() +
                                "\nPrice not found";
                        logger.error(errorMessage);
                        e.printStackTrace();
                    }

                    //information below not provided by available API
                    indexList.get(i).setMarketOpen(true);
                } catch (UnirestException e) {
                    String errorMessage = "AlphaVantageAPI Index fail for symbol " + indexList.get(i).getSymbol();
                    logger.error(errorMessage);
                    e.printStackTrace();
                }
            }
            else if(Objects.equals(env.getProperty("api.update"), "off")){
                indexList.get(i).setCurrentMarketPrice(generateRandom(indexList.get(i).getCurrentMarketPrice()));
            }

        }

        for(Index index: indexList){
            try {
                dbConnection.add().updateIndexApiData(index);
            } catch (SQLException throwables) {
                String errorMessage = "DB fail for update index API data fail for symbol " + index.getSymbol();
                logger.error(errorMessage);
                throwables.printStackTrace();
            }
        }
    }

    private void updateCrypto(){
        /*
            updates all stock data
         */
        List<Crypto> cryptos = currentData.getAssetManager().getCrypto();

        int i;
        for(i=0; i<cryptos.size(); i++){

            if(Objects.equals(env.getProperty("api.update"), "on")) {
                try {
                    AlphaVantageAPI alphaVantageAPI = new AlphaVantageAPI();
                    try {
                        double cryptoCurrentPrice = Double.parseDouble(alphaVantageAPI.getCrypto(cryptos.get(i).getSymbol()).get("price"));
                        cryptos.get(i).setCurrentMarketPrice(cryptoCurrentPrice);
                    } catch (JSONException e) {
                        cryptos.get(i).setCurrentMarketPrice(0);
                        String errorMessage = "AlphaVantageAPI JSON crypto fail for symbol " + cryptos.get(i).getSymbol() +
                                "\nPrice not found";
                        logger.error(errorMessage);
                        e.printStackTrace();
                    }
                } catch (UnirestException e) {
                    String errorMessage = "AlphaVantageAPI fail for crypto " + cryptos.get(i).getSymbol();
                    logger.error(errorMessage);
                    e.printStackTrace();
                }
            }
            else if(Objects.equals(env.getProperty("api.update"), "off")){
                cryptos.get(i).setCurrentMarketPrice(generateRandom(cryptos.get(i).getCurrentMarketPrice()));
            }

        }

        for(Crypto crypto: cryptos){
            try {
                dbConnection.add().updateCryptoApiData(crypto);
            } catch (SQLException throwables) {
                String errorMessage = "DB fail for update crypto API data fail for symbol " + crypto.getSymbol();
                logger.error(errorMessage);
                throwables.printStackTrace();
            }
        }

    }

    private void updateCommodity(){
        /*
            updates all stock data
         */
        List<Commodities> commodities = currentData.getAssetManager().getCommodities();

        int i;
        for(i=0; i<commodities.size(); i++){
            //Public API for commodities (aka Petrol, Wheat etc) not found

            commodities.get(i).setCurrentMarketPrice(generateRandom(commodities.get(i).getCurrentMarketPrice()));
        }

        for(Commodities commodity: commodities){
            try {
                dbConnection.add().updateCommodityApiData(commodity);
            } catch (SQLException throwables) {
                String errorMessage = "DB fail for update commodity API data fail for name " + commodity.getName();
                logger.error(errorMessage);
                throwables.printStackTrace();
            }
        }
    }

    public void sendNotifications(){
        /*
            check all users every notification
            if notification has reached its price target
            send and delete notification
         */
        UserManager userManager = dbConnection.read().readUsers();
        for(User user: userManager.getUsers()){

            //check if user has any notifications set up
            if(dbConnection.read().readNotificationsByUserId(user.getUserId()) == null){
                continue;
            }

            baseService.setupUser(user);

            for(Notification notification: user.getNotifications()){
                if(checkNotification(notification, user)){ //-works

                    if(!sendMail(user.getEmail(), notification.getNotificationName(), notification.getNotificationPrice())){
                        String errorMessage = "Sending mail failed for: " + notification.getNotificationName()
                                + " for user with Id: " + user.getUserId();
                        logger.error(errorMessage);
                        continue;
                    }

                    try {
                        dbConnection.delete().deleteNotification(user.getUserId(), notification.getNotificationName());
                    } catch (SQLException throwables) {
                        String errorMessage = "Delete Notification fail for notification: " + notification.getNotificationName()
                                + " for user with Id: " + user.getUserId();
                        logger.error(errorMessage);
                        throwables.printStackTrace();
                    }

                    //log success
                    String message = "Notification send for: " + notification.getNotificationName()
                            + " for user with Id: " + user.getUserId();
                    logger.error(message);
                }
            }
        }
    }

    private boolean checkNotification(Notification notification, User user){
        /*
            Check if notification has reached its price target
            return true if reached
            false if not
         */
        double price = notification.getNotificationPrice();
        if(notification.getAssetType().equals(AssetType.global)){
            if(price >= user.getAssets().calculateWholePortfolio()){
                return true;
            }
        }
        else if(notification.getAssetTypeSettings()){
            switch (notification.getAssetType()){
                case stock -> {
                    if(price >= user.getAssets().calculateAllStocksWorth()){
                        return true;
                    }
                }
                case index -> {
                    if(price >= user.getAssets().calculateAllIndexWorth()){
                        return true;
                    }
                }
                case crypto -> {
                    if(price >= user.getAssets().calculateAllCryptoWorth()){
                        return true;
                    }
                }
                case commodity -> {
                    if(price >= user.getAssets().calculateAllCommodityWorth()){
                        return true;
                    }
                }
            }
        }
        else if(!notification.getAssetTypeSettings()){
            switch (notification.getAssetType()){
                case stock -> {
                    if(price >=
                            user.getAssets().getStockBySymbol(notification.getNotificationTarget()).getQuantityOwned() *
                                user.getAssets().getStockBySymbol(notification.getNotificationTarget()).getCurrentMarketPrice()
                    ){
                        return true;
                    }
                }
                case index -> {
                    if(price >=
                            user.getAssets().getIndexBySymbol(notification.getNotificationTarget()).getQuantityOwned() *
                                    user.getAssets().getIndexBySymbol(notification.getNotificationTarget()).getCurrentMarketPrice()
                    ){
                        return true;
                    }
                }
                case crypto -> {
                    if(price >=
                            user.getAssets().getCryptoBySymbol(notification.getNotificationTarget()).getQuantityOwned() *
                                    user.getAssets().getCryptoBySymbol(notification.getNotificationTarget()).getCurrentMarketPrice()
                    ){
                        return true;
                    }
                }
                case commodity -> {
                    if(price >=
                            user.getAssets().getCommodityByName(notification.getNotificationTarget()).getQuantityOwned() *
                                    user.getAssets().getCommodityByName(notification.getNotificationTarget()).getCurrentMarketPrice()
                    ){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean sendMail(String userEmail, String notificationName, double notificationPrice){ // TO DO
        //catch exceptions and log errors
        emailService.sendNotificationEmail(userEmail, notificationName, notificationPrice);
        return true;
    }

    public void updateHistoricalData(){

        GraphService graphService = new GraphService();

        //Update stock historical data
        for(GraphInfoHolder graphInfoHolder: currentData.getGraphInfoManager().getStockInfoList()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", "Stock");
            jsonObject.put("symbol", graphInfoHolder.getAssetSymbol());
            if(!graphInfoHolder.isDailyEmpty()){
                jsonObject.put("period", "daily");
                graphInfoHolder.updateDaily(graphService.getChartData(jsonObject));
                jsonObject.remove("period");
            }
            if(!graphInfoHolder.isWeeklyEmpty()){
                jsonObject.put("period", "weekly");
                graphInfoHolder.updateWeekly(graphService.getChartData(jsonObject));
                jsonObject.remove("period");
            }
            if(!graphInfoHolder.isMonthlyEmpty()){
                jsonObject.put("period", "monthly");
                graphInfoHolder.updateMonthly(graphService.getChartData(jsonObject));
                jsonObject.remove("period");
            }
        }

        //Update index historical data
        for(GraphInfoHolder graphInfoHolder: currentData.getGraphInfoManager().getIndexInfoList()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", "Index");
            jsonObject.put("symbol", graphInfoHolder.getAssetSymbol());
            if(!graphInfoHolder.isDailyEmpty()){
                jsonObject.put("period", "daily");
                graphInfoHolder.updateDaily(graphService.getChartData(jsonObject));
                jsonObject.remove("period");
            }
            if(!graphInfoHolder.isWeeklyEmpty()){
                jsonObject.put("period", "weekly");
                graphInfoHolder.updateWeekly(graphService.getChartData(jsonObject));
                jsonObject.remove("period");
            }
            if(!graphInfoHolder.isMonthlyEmpty()){
                jsonObject.put("period", "monthly");
                graphInfoHolder.updateMonthly(graphService.getChartData(jsonObject));
                jsonObject.remove("period");
            }
        }

        //Update crypto historical data
        for(GraphInfoHolder graphInfoHolder: currentData.getGraphInfoManager().getCryptoInfoList()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", "Crypto");
            jsonObject.put("symbol", graphInfoHolder.getAssetSymbol());
            if(!graphInfoHolder.isDailyEmpty()){
                jsonObject.put("period", "daily");
                graphInfoHolder.updateDaily(graphService.getChartData(jsonObject));
                jsonObject.remove("period");
            }
            if(!graphInfoHolder.isWeeklyEmpty()){
                jsonObject.put("period", "weekly");
                graphInfoHolder.updateWeekly(graphService.getChartData(jsonObject));
                jsonObject.remove("period");
            }
            if(!graphInfoHolder.isMonthlyEmpty()){
                jsonObject.put("period", "monthly");
                graphInfoHolder.updateMonthly(graphService.getChartData(jsonObject));
                jsonObject.remove("period");
            }
        }

    }

    private double generateRandom(double input){
        /*
            Returns a random number +- 10% of the input number
         */
        return (Math.random() * ((input+10) - (input-10) + 1) + (input - 10));
    }

}

