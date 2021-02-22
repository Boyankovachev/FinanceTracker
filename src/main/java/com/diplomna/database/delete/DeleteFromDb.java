package com.diplomna.database.delete;

import com.diplomna.restapi.service.BaseService;
import com.diplomna.users.sub.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteFromDb {

    private Connection con;
    private final Logger logger;
    private final String databaseName;

    public DeleteFromDb(Connection con, String databaseName){
        this.con = con;
        this.databaseName = databaseName;
        this.logger = LoggerFactory.getLogger(DeleteFromDb.class);
    }

    public void deleteAllStockPurchases(int userId, String stockSymbol){
        try {
            String sql = "DELETE FROM `" + databaseName + "`.stock_purchase_info " +
                    "WHERE user_id = ? and stock_symbol = ?;";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setString(2,stockSymbol);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("deleteAllStockPurchases failed\n" + throwables.getMessage());
            throwables.printStackTrace();
        }
    }

    public void deletePassiveResource(int userId, String name){
        try {
            String sql = "DELETE FROM `" + databaseName + "`.passive_resource " +
                    "WHERE user_id = ? and name = ?;";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1,userId);
            statement.setString(2,name);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("deletePassiveResource failed\n" + throwables.getMessage());
            throwables.printStackTrace();
        }
    }

    public void deleteIndexPurchases(int userId, String indexSymbol){
        try {
            String sql = "DELETE FROM `" + databaseName + "`.index_purchase_info " +
                    "WHERE user_id = ? and index_symbol = ?;";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1,userId);
            statement.setString(2,indexSymbol);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("deleteIndexPurchases failed\n" + throwables.getMessage());
            throwables.printStackTrace();
        }
    }

    public void deleteCryptoPurchases(int userId, String cryptoSymbol){
        try {
            String sql = "DELETE FROM `" + databaseName + "`.crypto_purchase_info " +
                    "WHERE user_id = ? and crypto_symbol = ?;";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1,userId);
            statement.setString(2,cryptoSymbol);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("deleteCryptoPurchases failed\n" + throwables.getMessage());
            throwables.printStackTrace();
        }
    }

    public void deleteCommodityPurchases(int userId, String name){
        try {
            String sql = "DELETE FROM `" + databaseName + "`.commodity_purchase_info " +
                    "WHERE user_id = ? and commodity_name = ?;";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1,userId);
            statement.setString(2,name);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("deleteCommodityPurchases failed\n" + throwables.getMessage());
            throwables.printStackTrace();
        }
    }

    public void deleteNotification(int userId, String notificationName) throws SQLException{
        String sql = "DELETE FROM `" + databaseName + "`.notification " +
                "WHERE user_id = ? and notification_name = ?;";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1,userId);
        statement.setString(2,notificationName);
        statement.executeUpdate();
    }

}
