package com.diplomna.database.insert.sub;

import com.diplomna.assets.sub.PurchaseInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertIntoCryptoPurchaseInfo {
    private Connection connection = null;
    private String databaseName = null;
    public InsertIntoCryptoPurchaseInfo(Connection connection, String databaseName){
        this.connection = connection;
        this.databaseName =databaseName;
    }

    public void insertCryptoPurchaseInfo(int userId, PurchaseInfo purchaseInfo) throws SQLException {
        String sql =
                "INSERT INTO `" + databaseName + """
                    `.crypto_purchase_info(user_id, crypto_symbol, price, quantity, purchase_date)
                    VALUES(?,?,?,?,?);""";
        PreparedStatement statement = connection.prepareStatement(sql);
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
    }
}
