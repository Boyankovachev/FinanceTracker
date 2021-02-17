package com.diplomna.database.insert;

import com.diplomna.assets.finished.*;
import com.diplomna.assets.sub.PurchaseInfo;
import com.diplomna.database.delete.DeleteFromDb;
import com.diplomna.database.insert.sub.*;
import com.diplomna.users.sub.Notification;
import com.diplomna.users.sub.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class InsertIntoDb {
    private String connString;
    private Connection con;
    private String user;
    private String password;
    public String databaseName;
    private final Logger logger;
    public InsertIntoDb(String databaseName){
        user = "root";
        password = "1234";
        this.connString = "jdbc:mysql://localhost:3306/?user=" + user + "&password=" + password;
        this.databaseName = databaseName;
        this.logger = LoggerFactory.getLogger(InsertIntoDb.class);
        try {
            this.con = DriverManager.getConnection(connString);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void insertUser(User user){
        InsertIntoUsers insert = new InsertIntoUsers(this.con, this.databaseName);
        try {
            insert.insertUser(user);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void insertStock(Stock stock){
        InsertIntoStock insertStock =
                new InsertIntoStock(this.con, this.databaseName);
        try {
            insertStock.insertStock(stock);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            String errorMessage = "Insert stock failed!\n" +
                    "Message: \n"
                    + throwables.getMessage();
            logger.error(errorMessage);
        }
    }

    public void insertStockPurchaseInfo(int userId, PurchaseInfo purchaseInfo){
        InsertIntoStockPurchaseInfo insertIntoStockPurchaseInfo = new InsertIntoStockPurchaseInfo(this.con, this.databaseName);
        try {
            insertIntoStockPurchaseInfo.insertStockPurchaseInfo(userId,purchaseInfo);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void insertPassiveResource(int userId, PassiveResource passiveResource){
       InsertIntoPassiveResource insertIntoPassiveResource = new InsertIntoPassiveResource(this.con, this.databaseName);
        try {
            insertIntoPassiveResource.insertPassiveResource(userId,passiveResource);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void insertNotification(int userId, Notification notification) {
        InsertIntoNotification insertIntoNotification = new InsertIntoNotification(this.con, this.databaseName);
        try {
            insertIntoNotification.insertNotification(userId,notification);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void insertIndex(Index index) {
        InsertIntoIndex insertIntoIndex = new InsertIntoIndex(this.con, this.databaseName);
        try {
            insertIntoIndex.insertIndex(index);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void insertIndexPurchaseInfo(int userId, PurchaseInfo purchaseInfo){
        InsertIntoIndexPurchaseInfo insertIntoIndexPurchaseInfo = new InsertIntoIndexPurchaseInfo(this.con, this.databaseName);
        try {
            insertIntoIndexPurchaseInfo.insertIndexPurchaseInfo(userId, purchaseInfo);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void insertCommodity(Commodities commodities) {
        InsertIntoCommodity insertIntoCommodity = new InsertIntoCommodity(this.con, this.databaseName);
        try {
            insertIntoCommodity.insertCommodity(commodities);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void insertCommodityPurchaseInfo(int userId, PurchaseInfo purchaseInfo){
        InsertIntoCommodityPurchaseInfo insertIntoCommodityPurchaseInfo = new InsertIntoCommodityPurchaseInfo(this.con, this.databaseName);
        try {
            insertIntoCommodityPurchaseInfo.insertCommodityPurchaseInfo(userId, purchaseInfo);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void insertCrypto(Crypto crypto){
        InsertIntoCrypto insertIntoCrypto = new InsertIntoCrypto(this.con, this.databaseName);
        try {
            insertIntoCrypto.insertCrypto(crypto);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void insertCryptoPurchaseInfo(int userId, PurchaseInfo purchaseInfo){
        InsertIntoCryptoPurchaseInfo insertIntoCryptoPurchaseInfo = new InsertIntoCryptoPurchaseInfo(this.con, this.databaseName);
        try {
            insertIntoCryptoPurchaseInfo.insertCryptoPurchaseInfo(userId, purchaseInfo);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update2FA(User user){
        InsertIntoUsers insert = new InsertIntoUsers(this.con, this.databaseName);
        try {
            insert.updateIs2faRequired(user);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void updateEmail(User user){
        InsertIntoUsers insert = new InsertIntoUsers(this.con, this.databaseName);
        try {
            insert.updateEmail(user);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void updateUsername(User user){
        InsertIntoUsers insert = new InsertIntoUsers(this.con, this.databaseName);
        try {
            insert.updateUsername(user);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void updatePassword(User user){
        InsertIntoUsers insert = new InsertIntoUsers(this.con, this.databaseName);
        try {
            insert.updatePassword(user);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void updatePassiveResourceCurrentPrice(int userId, double newPrice, String name) throws SQLException {
        InsertIntoPassiveResource insertIntoPassiveResource = new InsertIntoPassiveResource(this.con, this.databaseName);
        insertIntoPassiveResource.updatePassiveResourceCurrentPrice(userId, newPrice, name);
    }

    public void updateStockApiData(Stock stock) throws SQLException {
        InsertIntoStock insertIntoStock = new InsertIntoStock(this.con, this.databaseName);
        insertIntoStock.updateStockApiData(stock);
    }
    public void updateIndexApiData(Index index) throws SQLException {
        InsertIntoIndex insertIntoIndex = new InsertIntoIndex(this.con, this.databaseName);
        insertIntoIndex.updateIndexApiData(index);
    }
    public void updateCryptoApiData(Crypto crypto) throws SQLException {
        InsertIntoCrypto insertIntoCrypto = new InsertIntoCrypto(this.con, this.databaseName);
        insertIntoCrypto.updateCryptoApiData(crypto);
    }
    public void updateCommodityApiData(Commodities commodity) throws SQLException {
        InsertIntoCommodity insertIntoCommodity = new InsertIntoCommodity(this.con, this.databaseName);
        insertIntoCommodity.updateCommodityApiData(commodity);
    }
}
