package com.diplomna.database.insert;

import com.diplomna.assets.finished.*;
import com.diplomna.assets.sub.PurchaseInfo;
import com.diplomna.database.delete.DeleteFromDb;
import com.diplomna.users.sub.Notification;
import com.diplomna.users.sub.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class InsertIntoDb {

    private Connection con;
    private final Logger logger;
    private final String databaseName;

    public InsertIntoDb(Connection con, String databaseName){
        this.con = con;
        this.databaseName = databaseName;
        this.logger = LoggerFactory.getLogger(InsertIntoDb.class);
    }

    public void insertUser(User user){
        /*
            inserts the new user into the user table
         */
        try {
            int newUserId = insertNotNullValues(user); //get the Id of the new user
            user.setUserId(newUserId);
            if(user.getEmail() != null && !user.getEmail().isEmpty()){
                updateEmail(user);
            }
            if(user.getIs2FactorAuthenticationRequired()){
                update2FA(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    private int insertNotNullValues(User user) throws SQLException {
        /*
        insert never null values of user and
        returns the ID created by SQL
         */
        String sql =
                "INSERT INTO `" + databaseName + """
                    `.user(username, password_hash, salt)
                    VALUES(?,?,?);""";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, user.getUserName());
        statement.setString(2, user.getPasswordHash());
        statement.setString(3, user.getSalt());
        statement.executeUpdate();


        Statement selectStatement = con.createStatement();
        String sql2 = "SELECT user_id FROM `" + databaseName + "`.user\n" +
                "WHERE username = '" + user.getUserName() + "';";
        ResultSet resultSet = selectStatement.executeQuery(sql2);
        resultSet.last();
        return resultSet.getInt(1);
    }

    public void insertStock(Stock stock){
        try {
            String sql =
                    "INSERT INTO `" + databaseName + """
                    `.stock(symbol, stock_name, currency, currency_symbol, 
                        exchange_name, description, current_market_price,
                        is_market_open, recommendation_key)
                    VALUES(?,?,?,?,?,?,?,?,?);""";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1,stock.getSymbol());
            statement.setString(2,stock.getName());
            statement.setString(3,stock.getCurrency());
            statement.setString(4,stock.getCurrencySymbol());
            statement.setString(5,stock.getExchangeName());
            statement.setString(6,stock.getDescription());
            statement.setDouble(7, stock.getCurrentMarketPrice());
            statement.setBoolean(8, stock.isMarketOpen());
            statement.setString(9, stock.getRecommendationKey());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            String errorMessage = "Insert stock failed!\n"
                    + throwables.getMessage();
            logger.error(errorMessage);
        }
    }

    public void insertStockPurchaseInfo(int userId, PurchaseInfo purchaseInfo){
        try {
            String sql =
                    "INSERT INTO `" + databaseName + """
                    `.stock_purchase_info(user_id, stock_symbol, price, quantity, purchase_date)
                    VALUES(?,?,?,?,?);""";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1,userId);
            statement.setString(2,purchaseInfo.getStockSymbol());
            statement.setDouble(3,purchaseInfo.getPrice());
            statement.setDouble(4,purchaseInfo.getQuantity());
            if(purchaseInfo.getPurchaseDate() != null) {
                statement.setDate(5, new java.sql.Date(purchaseInfo.getPurchaseDate().calendar.getTime().getTime()));
            }
            else {
                statement.setDate(5, null);
            }
            statement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("insertStockPurchaseInfo failed\n" + throwables.getMessage());
            throwables.printStackTrace();
        }
    }

    public void insertPassiveResource(int userId, PassiveResource passiveResource){
        try {
            String sql =
                    "INSERT INTO `" + databaseName + """
                    `.passive_resource(user_id, name, purchase_price, purchase_date, current_price, description, currency, currency_symbol)
                    VALUES(?,?,?,?,?,?,?,?);""";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1,userId);
            statement.setString(2,passiveResource.getName());
            statement.setDouble(3,passiveResource.getPurchaseInfo().getPrice());
            if(passiveResource.getPurchaseInfo().getPurchaseDate() != null) {
                statement.setDate(4, new java.sql.Date(passiveResource.getPurchaseInfo().getPurchaseDate().calendar.getTime().getTime()));
            }
            else {
                statement.setDate(4, null);
            }
            statement.setDouble(5, passiveResource.getCurrentMarketPrice());
            statement.setString(6,passiveResource.getDescription());
            statement.setString(7,passiveResource.getCurrency());
            statement.setString(8,passiveResource.getCurrencySymbol());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("insertPassiveResource failed\n" + throwables.getMessage());
            throwables.printStackTrace();
        }
    }

    public void insertNotification(int userId, Notification notification) {
        try {
            String sql =
                    "INSERT INTO `" + databaseName + """
                    `.notification()
                    VALUES(?,?,?,?,?,?);""";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1,userId);
            statement.setString(2, notification.getAssetType().toString());
            statement.setBoolean(3, notification.getAssetTypeSettings());
            statement.setString(4, notification.getNotificationTarget());
            statement.setDouble(5, notification.getNotificationPrice());
            statement.setString(6, notification.getNotificationName());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("insertNotification failed\n" + throwables.getMessage());
            throwables.printStackTrace();
        }
    }
    public void insertIndex(Index index) {
        try {
            String sql =
                    "INSERT INTO `" + databaseName + """
                    `.index(symbol, index_name, currency, currency_symbol, exchange_name, description, current_market_price, is_market_open)
                    VALUES(?,?,?,?,?,?,?,?);""";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, index.getSymbol());
            statement.setString(2, index.getName());
            statement.setString(3, index.getCurrency());
            statement.setString(4, index.getCurrencySymbol());
            statement.setString(5, index.getExchangeName());
            statement.setString(6, index.getDescription());
            statement.setDouble(7, index.getCurrentMarketPrice());
            statement.setBoolean(8, index.isMarketOpen());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("insertIndex failed\n" + throwables.getMessage());
            throwables.printStackTrace();
        }
    }
    public void insertIndexPurchaseInfo(int userId, PurchaseInfo purchaseInfo){
        try {
            String sql =
                    "INSERT INTO `" + databaseName + """
                    `.index_purchase_info(user_id, index_symbol, price, quantity, purchase_date)
                    VALUES(?,?,?,?,?);""";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setString(2,purchaseInfo.getStockSymbol());
            statement.setDouble(3,purchaseInfo.getPrice());
            statement.setDouble(4,purchaseInfo.getQuantity());
            if(purchaseInfo.getPurchaseDate() != null) {
                statement.setDate(5, new java.sql.Date(purchaseInfo.getPurchaseDate().calendar.getTime().getTime()));
            }
            else {
                statement.setDate(5, null);
            }
            statement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("insertIndexPurchaseInfo failed\n" + throwables.getMessage());
            throwables.printStackTrace();
        }
    }
    public void insertCommodity(Commodities commodity) {
        try {
            String sql =
                    "INSERT INTO `" + databaseName + """
                    `.commodity(commodity_name, currency, currency_symbol, exchange_name, description, current_market_price)
                    VALUES(?,?,?,?,?,?);""";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, commodity.getName());
            statement.setString(2, commodity.getCurrency());
            statement.setString(3, commodity.getCurrencySymbol());
            statement.setString(4, commodity.getExchangeName());
            statement.setString(5, commodity.getDescription());
            statement.setDouble(6, commodity.getCurrentMarketPrice());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("insertCommodity failed\n" + throwables.getMessage());
            throwables.printStackTrace();
        }
    }
    public void insertCommodityPurchaseInfo(int userId, PurchaseInfo purchaseInfo){
        try {
            String sql =
                    "INSERT INTO `" + databaseName + """
                    `.commodity_purchase_info(user_id, commodity_name, price, quantity, purchase_date)
                    VALUES(?,?,?,?,?);""";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setString(2,purchaseInfo.getStockSymbol());
            statement.setDouble(3,purchaseInfo.getPrice());
            statement.setDouble(4,purchaseInfo.getQuantity());
            if(purchaseInfo.getPurchaseDate() != null) {
                statement.setDate(5, new java.sql.Date(purchaseInfo.getPurchaseDate().calendar.getTime().getTime()));
            }
            else {
                statement.setDate(5, null);
            }
            statement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("insertCommodityPurchaseInfo failed\n" + throwables.getMessage());
            throwables.printStackTrace();
        }
    }
    public void insertCrypto(Crypto crypto){
        try {
            String sql =
                    "INSERT INTO `" + databaseName + """
                    `.crypto(symbol, crypto_name, currency, currency_symbol, description, current_market_price)
                    VALUES(?,?,?,?,?,?);""";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1,crypto.getSymbol());
            statement.setString(2,crypto.getName());
            statement.setString(3,crypto.getCurrency());
            statement.setString(4,crypto.getCurrencySymbol());
            statement.setString(5,crypto.getDescription());
            statement.setDouble(6, crypto.getCurrentMarketPrice());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("insertCrypto failed\n" + throwables.getMessage());
            throwables.printStackTrace();
        }
    }
    public void insertCryptoPurchaseInfo(int userId, PurchaseInfo purchaseInfo){
        try {
            String sql =
                    "INSERT INTO `" + databaseName + """
                    `.crypto_purchase_info(user_id, crypto_symbol, price, quantity, purchase_date)
                    VALUES(?,?,?,?,?);""";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setString(2,purchaseInfo.getStockSymbol());
            statement.setDouble(3,purchaseInfo.getPrice());
            statement.setDouble(4,purchaseInfo.getQuantity());
            if(purchaseInfo.getPurchaseDate() != null) {
                statement.setDate(5, new java.sql.Date(purchaseInfo.getPurchaseDate().calendar.getTime().getTime()));
            }
            else {
                statement.setDate(5, null);
            }
            statement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("insertCryptoPurchaseInfo failed\n" + throwables.getMessage());
            throwables.printStackTrace();
        }
    }

    public void update2FA(User user){
        try {
            String sql =
                    "UPDATE `" + databaseName + "`.user\n" + """
                SET is_2fa_required = ?
                WHERE username = ? and user_id = ?;
                """;
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setBoolean(1,user.getIs2FactorAuthenticationRequired());
            statement.setString(2,user.getUserName());
            statement.setInt(3,user.getUserId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("update2FA failed\n" + throwables.getMessage());
            throwables.printStackTrace();
        }
    }
    public void updateEmail(User user){
        try {
            String sql =
                    "UPDATE `" + databaseName + "`.user\n" + """
                SET email = ?
                WHERE username = ? and user_id = ?;
                """;
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1,user.getEmail());
            statement.setString(2,user.getUserName());
            statement.setInt(3,user.getUserId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("updateEmail failed\n" + throwables.getMessage());
            throwables.printStackTrace();
        }
    }
    public void updateUsername(User user){
        try {
            String sql =
                    "UPDATE `" + databaseName + "`.user\n" + """
                SET username = ?
                WHERE user_id = ?;
                """;
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1,user.getUserName());
            statement.setInt(2,user.getUserId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("updateUsername failed\n" + throwables.getMessage());
            throwables.printStackTrace();
        }
    }
    public void updatePassword(User user){
        try {
            String sql =
                    "UPDATE `" + databaseName + "`.user\n" + """
                SET password_hash = ?, salt = ?
                WHERE user_id = ?;
                """;
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1,user.getPasswordHash());
            statement.setString(2,user.getSalt());
            statement.setInt(3,user.getUserId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("updatePassword failed\n" + throwables.getMessage());
            throwables.printStackTrace();
        }
    }
    public void updatePassiveResourceCurrentPrice(int userId, double newPrice, String name) throws SQLException {
        String sql = "UPDATE " + databaseName + ".`passive_resource` " +
                "SET current_price = ? " +
                "WHERE user_id = ? AND name = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setDouble(1, newPrice);
        statement.setInt(2, userId);
        statement.setString(3, name);
        statement.executeUpdate();
    }

    public void updateStockApiData(Stock stock) throws SQLException {
        String sql =
                "UPDATE `" + databaseName + "`.stock\n" + """
                SET current_market_price = ?, is_market_open = ?, recommendation_key = ?
                WHERE symbol = ?;
                """;

        PreparedStatement statement = con.prepareStatement(sql);
        statement.setDouble(1, stock.getCurrentMarketPrice());
        statement.setBoolean(2, stock.isMarketOpen());
        statement.setString(3, stock.getRecommendationKey());
        statement.setString(4, stock.getSymbol());
        statement.executeUpdate();
    }
    public void updateIndexApiData(Index index) throws SQLException {
        String sql =
                "UPDATE `" + databaseName + "`.index\n" + """
                SET current_market_price = ?, is_market_open = ?
                WHERE symbol = ?;
                """;

        PreparedStatement statement = con.prepareStatement(sql);
        statement.setDouble(1, index.getCurrentMarketPrice());
        statement.setBoolean(2, index.isMarketOpen());
        statement.setString(3, index.getSymbol());
        statement.executeUpdate();
    }
    public void updateCryptoApiData(Crypto crypto) throws SQLException {
        String sql =
                "UPDATE `" + databaseName + "`.crypto\n" + """
                SET current_market_price = ?
                WHERE symbol = ?;
                """;

        PreparedStatement statement = con.prepareStatement(sql);
        statement.setDouble(1, crypto.getCurrentMarketPrice());
        statement.setString(2, crypto.getSymbol());
        statement.executeUpdate();
    }
    public void updateCommodityApiData(Commodities commodity) throws SQLException {
        String sql =
                "UPDATE `" + databaseName + "`.commodity\n" + """
                SET current_market_price = ?
                WHERE commodity_name = ?;
                """;

        PreparedStatement statement = con.prepareStatement(sql);
        statement.setDouble(1, commodity.getCurrentMarketPrice());
        statement.setString(2, commodity.getName());
        statement.executeUpdate();
    }
}
