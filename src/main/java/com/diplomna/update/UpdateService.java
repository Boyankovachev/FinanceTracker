package com.diplomna.update;

import com.diplomna.api.alphavantage.AlphaVantageAPI;
import com.diplomna.api.stock.YahooFinanceAPI;
import com.diplomna.assets.finished.Commodities;
import com.diplomna.assets.finished.Crypto;
import com.diplomna.assets.finished.Index;
import com.diplomna.assets.finished.Stock;
import com.diplomna.database.delete.DeleteFromDb;
import com.diplomna.database.insert.InsertIntoDb;
import com.diplomna.database.read.ReadFromDb;
import com.diplomna.email.EmailService;
import com.diplomna.restapi.service.BaseService;
import com.diplomna.users.UserManager;
import com.diplomna.users.sub.AssetType;
import com.diplomna.users.sub.Notification;
import com.diplomna.users.sub.User;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;

@Service
public class UpdateService {

    @Autowired
    private final BaseService baseService;

    @Autowired
    private EmailService emailService;

    private final Logger logger;
    private final ReadFromDb readFromDb;
    private final InsertIntoDb insertIntoDb;
    private final DeleteFromDb deleteFromDb;

    public UpdateService(){
        this.logger = LoggerFactory.getLogger(BaseService.class);
        this.baseService = new BaseService();
        readFromDb = new ReadFromDb("test");
        insertIntoDb = new InsertIntoDb("test");
        deleteFromDb = new DeleteFromDb("test");
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
        List<Stock> stocks = new ArrayList<>();
        try {
            stocks = readFromDb.readAllStocks();
        } catch (SQLException throwables) {
            String errorMessage = "Read all stocks from DB failed";
            logger.error(errorMessage);
            throwables.printStackTrace();
        }

        YahooFinanceAPI yahooFinanceAPI = new YahooFinanceAPI();
        int i;
        for(i=0; i<stocks.size(); i++){
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

        for(Stock stock: stocks){
            try {
                insertIntoDb.updateStockApiData(stock);
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
        List<Index> indexList = new ArrayList<>();
        try {
            indexList = readFromDb.readAllIndex();
        } catch (SQLException throwables) {
            String errorMessage = "Read all index from DB failed";
            logger.error(errorMessage);
            throwables.printStackTrace();
        }

        AlphaVantageAPI alphaVantageAPI = new AlphaVantageAPI();
        int i;
        for(i=0; i<indexList.size(); i++){
            try {
                alphaVantageAPI.setIndex(indexList.get(i).getSymbol());

                try {
                    double indexCurrentPrice = Double.parseDouble(alphaVantageAPI.getIndexPrice());
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

        for(Index index: indexList){
            try {
                insertIntoDb.updateIndexApiData(index);
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
        List<Crypto> cryptos = new ArrayList<>();
        try {
            cryptos = readFromDb.readAllCrypto();
        } catch (SQLException throwables) {
            String errorMessage = "Read all crypto from DB failed";
            logger.error(errorMessage);
            throwables.printStackTrace();
        }

        int i;
        for(i=0; i<cryptos.size(); i++){
            try {
                AlphaVantageAPI alphaVantageAPI = new AlphaVantageAPI();
                alphaVantageAPI.setCrypto(cryptos.get(i).getSymbol());
                try {
                    double cryptoCurrentPrice = Double.parseDouble(alphaVantageAPI.getCrypto().get("price"));
                    System.out.println("crypto symbol: " + cryptos.get(i).getSymbol() + "  price: " + cryptoCurrentPrice);
                    cryptos.get(i).setCurrentMarketPrice(cryptoCurrentPrice);
                }catch (JSONException e){
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

        for(Crypto crypto: cryptos){
            try {
                insertIntoDb.updateCryptoApiData(crypto);
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
        List<Commodities> commodities = new ArrayList<>();
        try {
            commodities = readFromDb.readAllCommodities();
        } catch (SQLException throwables) {
            String errorMessage = "Read all commodities from DB failed";
            logger.error(errorMessage);
            throwables.printStackTrace();
        }

        int i;
        for(i=0; i<commodities.size(); i++){
            //Public API for commodities (aka Petrol, Wheat etc) not found

            //simulate some values
            double currentPrice = commodities.get(i).getCurrentMarketPrice();
            commodities.get(i).setCurrentMarketPrice(Math.random() * ((currentPrice+10) -
                    (currentPrice-10) + 1) + (currentPrice - 10));
        }

        for(Commodities commodity: commodities){
            try {
                insertIntoDb.updateCommodityApiData(commodity);
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
        UserManager userManager = readFromDb.readUsers(false);
        for(User user: userManager.getUsers()){

            //check if user has any notifications set up
            if(readFromDb.readNotificationsByUserId(user.getUserId()) == null){
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
                        deleteFromDb.deleteNotification(user.getUserId(), notification.getNotificationName());
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

}

