package com.diplomna.database.delete.sub;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteFromNotification {
    private Connection connection = null;
    private String databaseName = null;
    public DeleteFromNotification(Connection connection, String databaseName){
        this.connection = connection;
        this.databaseName =databaseName;
    }

    public void deleteNotification(int userId, String notificationName) throws SQLException {
        String sql = "DELETE FROM `" + databaseName + "`.notification " +
                "WHERE user_id = ? and notification_name = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,userId);
        statement.setString(2,notificationName);
        statement.executeUpdate();
    }

}