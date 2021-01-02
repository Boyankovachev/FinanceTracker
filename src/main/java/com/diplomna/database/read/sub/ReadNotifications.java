package com.diplomna.database.read.sub;

import com.diplomna.assets.finished.Stock;
import com.diplomna.assets.sub.PurchaseInfo;
import com.diplomna.date.DatеManager;
import com.diplomna.users.sub.AssetType;
import com.diplomna.users.sub.Notification;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReadNotifications {
    private Connection connection = null;
    private String databaseName = null;
    public ReadNotifications(Connection connection, String databaseName){
        this.connection = connection;
        this.databaseName =databaseName;
    }

    public List<Notification> readNotificationsByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM `" + databaseName +"`.notification\n" +
                "WHERE user_id = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,userId);
        ResultSet resultSet = statement.executeQuery();
        List<Notification> notifications = new ArrayList<>();
        while (resultSet.next()){
            Notification notification = new Notification();
            notification.setAssetType(AssetType.valueOf(resultSet.getString(2)));
            notification.setAssetTypeSettings(resultSet.getBoolean(3));
            notification.setNotificationTarget(resultSet.getString(4));
            notification.setNotificationPrice(resultSet.getDouble(5));
            notification.setNotificationName(resultSet.getString(6));
            notifications.add(notification);
        }
        return notifications;
    }
}
