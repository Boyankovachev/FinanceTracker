package com.diplomna.database.insert.sub;

import com.diplomna.assets.finished.Stock;
import com.diplomna.users.sub.Notification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertIntoNotification {
    private Connection connection = null;
    private String databaseName = null;
    public InsertIntoNotification(Connection connection, String databaseName){
        this.connection = connection;
        this.databaseName =databaseName;
    }

    public void insertNotification(int userId, Notification notification) throws SQLException {
        String sql =
                "INSERT INTO `" + databaseName + """
                    `.notification()
                    VALUES(?,?,?,?,?,?,?);""";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,userId);
        statement.setString(2, notification.getAssetType().toString());
        statement.setBoolean(3, notification.getAssetTypeSettings());
        statement.setDouble(4, notification.getNotificationPrice());
        statement.setString(5, notification.getNotificationName());
        statement.setString(6, notification.getStockSymbol());
        statement.setString(7,notification.getPassiveAssetName());
        statement.executeUpdate();
    }
}

/*
            String sql = "CREATE TABLE IF NOT EXISTS `"+databaseName+"`.`notification`(\n" +
                    "    `user_id` INT NOT NULL,\n" +
                    "    `asset_type` ENUM(\"stock\", \"passive_resource\", \"global\") NOT NULL,\n" +
                    "    `asset_type_settings` BOOL NOT NULL,\n" +
                    "    `notification_price` DOUBLE NOT NULL,\n" +
                    "    `notification_name` VARCHAR(32) NOT NULL,\n" +
                    "    `stock_symbol` VARCHAR(12),\n" +
                    "    `passive_asset_name` VARCHAR(12),\n" +
                    "    FOREIGN KEY (`user_id`) REFERENCES `" + databaseName + "`.user(`user_id`));\n";
 */
