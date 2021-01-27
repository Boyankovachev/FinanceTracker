package com.diplomna.database.delete.sub;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteFromIndex {
    private Connection connection = null;
    private String databaseName = null;
    public DeleteFromIndex(Connection connection, String databaseName){
        this.connection = connection;
        this.databaseName =databaseName;
    }

    public void deleteAllIndexPurchases(int userId, String symbol) throws SQLException {
        String sql = "DELETE FROM `" + databaseName + "`.index_purchase_info " +
                "WHERE user_id = ? and index_symbol = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,userId);
        statement.setString(2,symbol);
        statement.executeUpdate();
    }
}
