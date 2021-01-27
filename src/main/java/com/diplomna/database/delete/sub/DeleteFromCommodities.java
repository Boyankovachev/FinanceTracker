package com.diplomna.database.delete.sub;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteFromCommodities {
    private Connection connection = null;
    private String databaseName = null;
    public DeleteFromCommodities(Connection connection, String databaseName){
        this.connection = connection;
        this.databaseName =databaseName;
    }

    public void deleteAllCommodityPurchases(int userId, String name) throws SQLException {
        String sql = "DELETE FROM `" + databaseName + "`.commodity_purchase_info " +
                "WHERE user_id = ? and commodity_name = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,userId);
        statement.setString(2,name);
        statement.executeUpdate();
    }
}
