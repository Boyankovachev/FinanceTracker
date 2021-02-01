package com.diplomna.database.delete;

import com.diplomna.database.delete.sub.*;
import com.diplomna.users.sub.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DeleteFromDb {
    private String connString;
    private Connection con;
    private String user;
    private String password;
    public String databaseName;
    public DeleteFromDb(String databaseName){
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

    public void deleteAllStockPurchases(int userID, String stockSymbol){
        DeleteFromStock deleteFromStock = new DeleteFromStock(this.con, this.databaseName);
        try {
            deleteFromStock.deleteAllStockPurchases(userID, stockSymbol);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deletePassiveResource(int userID, String name){
        DeleteFromPassiveResource deleteFromPassiveResource = new DeleteFromPassiveResource(this.con, this.databaseName);
        try {
            deleteFromPassiveResource.deletePassiveResource(userID, name);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteIndexPurchases(int userID, String indexSymbol){
        DeleteFromIndex deleteFromIndex = new DeleteFromIndex(this.con, this.databaseName);
        try {
            deleteFromIndex.deleteAllIndexPurchases(userID, indexSymbol);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteCryptoPurchases(int userID, String cryptoSymbol){
        DeleteFromCrypto deleteFromCrypto = new DeleteFromCrypto(this.con, this.databaseName);
        try {
            deleteFromCrypto.deleteAllCryptoPurchases(userID, cryptoSymbol);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteCommodityPurchases(int userID, String name){
        DeleteFromCommodities deleteFromCommodities = new DeleteFromCommodities(this.con, this.databaseName);
        try {
            deleteFromCommodities.deleteAllCommodityPurchases(userID, name);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteNotification(int userId, String notificationName) throws SQLException{
        DeleteFromNotification deleteFromNotification = new DeleteFromNotification(this.con, this.databaseName);
        deleteFromNotification.deleteNotification(userId, notificationName);
    }

}
