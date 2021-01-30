package com.diplomna.database.read;

import com.diplomna.assets.finished.*;
import com.diplomna.assets.sub.ActiveAsset;
import com.diplomna.database.read.sub.*;
import com.diplomna.users.UserManager;
import com.diplomna.users.sub.Notification;
import com.diplomna.users.sub.User;
import org.w3c.dom.ls.LSException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class ReadFromDb {
    private String connString;
    private Connection con;
    private String user;
    private String password;
    public String databaseName;
    public ReadFromDb(String databaseName){
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

    public UserManager readUsers(boolean onlyWith2Fa){
        ReadUsers readUsers = new ReadUsers(con, databaseName);
        try {
            List<User> users;
            if(!onlyWith2Fa){
                users = readUsers.readUsers();
            }
            else {
                users = readUsers.readUsersWith2FA();
            }
            /*
            for(int i=0; i<users.size(); i++){
                System.out.print(users.get(i).getUserId() + " ");
                System.out.print(users.get(i).getUserName() + " ");
                System.out.print(users.get(i).getPasswordHash() + " ");
                System.out.print(users.get(i).getSalt() + " ");
                System.out.print(users.get(i).getEmail() + " ");
                System.out.println(users.get(i).getIs2FactorAuthenticationRequired() + " ");
            }
            */
            UserManager userManager = new UserManager(users);
            return userManager;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public List<Stock> readStockPurchasesByUserId(int userId){
        ReadStockPurchases readStockPurchases = new ReadStockPurchases(con, databaseName);
        try {
            return readStockPurchases.readStockPurchasesByUserId(userId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public Stock readStockBySymbol(String symbol){
        ReadStock readStock = new ReadStock(con, databaseName);
        try {
            return readStock.readStockBySymbol(symbol);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public List<Stock> readAllStocks() throws SQLException {
        ReadStock readStock = new ReadStock(con, databaseName);
        return readStock.readAllStocks();
    }

    public List<PassiveResource> readPassiveResourcesByUserId(int userId){
        ReadPassiveResources readPassiveResources = new ReadPassiveResources(con, databaseName);
        try {
            return readPassiveResources.readPassiveResourceByUserId(userId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public Index readIndexBySymbol(String symbol){
        ReadIndex readIndex = new ReadIndex(con, databaseName);
        try {
            return readIndex.readIndexBySymbol(symbol);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public List<Index> readAllIndex() throws SQLException {
        ReadIndex readIndex = new ReadIndex(con, databaseName);
        return readIndex.readAllIndex();
    }

    public List<Index> readIndexPurchasesByUserId(int userId){
        ReadIndexPurchases readIndexPurchases = new ReadIndexPurchases(con, databaseName);
        try {
            return readIndexPurchases.readIndexPurchasesByUserId(userId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public Crypto readCryptoBySymbol(String symbol){
        ReadCrypto readCrypto = new ReadCrypto(con, databaseName);
        try {
            return readCrypto.readCryptoBySymbol(symbol);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public List<Crypto> readAllCrypto() throws SQLException {
        ReadCrypto readCrypto = new ReadCrypto(con, databaseName);
        return readCrypto.readAllCrypto();
    }

    public List<Crypto> readCryptoPurchaseByUserId(int userId){
        ReadCryptoPurchases readCryptoPurchases = new ReadCryptoPurchases(con, databaseName);
        try {
            return readCryptoPurchases.readCryptoPurchasesByUserId(userId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public Commodities readCommodityByCommodityName(String commodityName){
        ReadCommodity readCommodity = new ReadCommodity(con, databaseName);
        try {
            return readCommodity.readCommodityByName(commodityName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public List<Commodities> readAllCommodities() throws SQLException {
        ReadCommodity readCommodity = new ReadCommodity(con, databaseName);
        return readCommodity.readAllCommodities();
    }

    public List<Commodities> readCommodityPurchaseInfoByUserId(int userId){
        ReadCommodityPurchases readCommodityPurchases = new ReadCommodityPurchases(con, databaseName);
        try {
            return readCommodityPurchases.readCommodityPurchasesByUserId(userId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public List<Notification> readNotificationsByUserId(int userId){
        ReadNotifications readNotifications = new ReadNotifications(con, databaseName);
        try {
            return readNotifications.readNotificationsByUserId(userId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public List<String> readPresentAsset(String assetType){
        try {
            switch (assetType) {
                case "stock":
                    ReadStock readStock = new ReadStock(con, databaseName);
                    return readStock.readAllStocksSymbols();
                case "index":
                    ReadIndex readIndex = new ReadIndex(con, databaseName);
                    return readIndex.readAllIndexSymbols();
                case "crypto":
                    ReadCrypto readCrypto = new ReadCrypto(con, databaseName);
                    return readCrypto.readAllCryptoSymbols();
                case "commodity":
                    ReadCommodity readCommodity = new ReadCommodity(con, databaseName);
                    return readCommodity.readAllCommodityNames();
            }
        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return null;
    }

}
