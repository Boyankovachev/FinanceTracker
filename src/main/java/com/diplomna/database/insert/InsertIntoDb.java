package com.diplomna.database.insert;

import com.diplomna.assets.finished.*;
import com.diplomna.assets.sub.PurchaseInfo;
import com.diplomna.database.insert.sub.*;
import com.diplomna.users.sub.Notification;
import com.diplomna.users.sub.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class InsertIntoDb {
    private String connString;
    private Connection con;
    private String user;
    private String password;
    public String databaseName;
    public InsertIntoDb(String databaseName){
        user = "root";
        password = "1234";
        this.connString = "jdbc:mysql://localhost:3306/?user=" + user + "&password=" + password;
        this.databaseName = databaseName;
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
        InsertIntoStock insertStock = new InsertIntoStock(this.con, this.databaseName);
        try {
            insertStock.insertStock(stock);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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

}
