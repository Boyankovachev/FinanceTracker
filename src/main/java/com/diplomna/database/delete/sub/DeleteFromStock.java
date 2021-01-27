package com.diplomna.database.delete.sub;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteFromStock {
    private Connection connection = null;
    private String databaseName = null;
    public DeleteFromStock(Connection connection, String databaseName){
        this.connection = connection;
        this.databaseName =databaseName;
    }

    public void deleteAllStockPurchases(int userId, String stockSymbol) throws SQLException {
        String sql = "DELETE FROM `" + databaseName + "`.stock_purchase_info " +
                "WHERE user_id = ? and stock_symbol = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,userId);
        statement.setString(2,stockSymbol);
        statement.executeUpdate();
    }

}
