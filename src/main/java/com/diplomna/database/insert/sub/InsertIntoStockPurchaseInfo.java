package com.diplomna.database.insert.sub;

import com.diplomna.assets.finished.Stock;
import com.diplomna.assets.sub.PurchaseInfo;
import com.diplomna.users.sub.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertIntoStockPurchaseInfo {
    private Connection connection = null;
    private String databaseName = null;
    public InsertIntoStockPurchaseInfo(Connection connection, String databaseName){
        this.connection = connection;
        this.databaseName =databaseName;
    }

    public void insertStockPurchaseInfo(int userId, PurchaseInfo purchaseInfo) throws SQLException {
        String sql =
                "INSERT INTO `" + databaseName + """
                    `.stock_purchase_info(user_id, stock_symbol, price, quantity, purchase_date)
                    VALUES(?,?,?,?,?);""";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,userId);
        statement.setString(2,purchaseInfo.getStockSymbol());
        statement.setDouble(3,purchaseInfo.getPrice());
        statement.setDouble(4,purchaseInfo.getQuantity());
        statement.setDate(5, new java.sql.Date(purchaseInfo.getPurchaseDate().calendar.getTime().getTime()));
        statement.executeUpdate();
    }
}
