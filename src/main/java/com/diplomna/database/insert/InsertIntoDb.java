package com.diplomna.database.insert;

import com.diplomna.assets.finished.PassiveResource;
import com.diplomna.assets.finished.Stock;
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
}
