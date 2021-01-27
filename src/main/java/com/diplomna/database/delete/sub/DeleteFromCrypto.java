package com.diplomna.database.delete.sub;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteFromCrypto {
    private Connection connection = null;
    private String databaseName = null;
    public DeleteFromCrypto(Connection connection, String databaseName){
        this.connection = connection;
        this.databaseName =databaseName;
    }

    public void deleteAllCryptoPurchases(int userId, String symbol) throws SQLException {
        String sql = "DELETE FROM `" + databaseName + "`.crypto_purchase_info " +
                "WHERE user_id = ? and crypto_symbol = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,userId);
        statement.setString(2,symbol);
        statement.executeUpdate();
    }
}
