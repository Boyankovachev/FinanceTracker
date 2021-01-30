package com.diplomna.database.insert.sub;

import com.diplomna.assets.finished.Index;
import com.diplomna.assets.finished.Stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertIntoIndex {
    private Connection connection = null;
    private String databaseName = null;
    public InsertIntoIndex(Connection connection, String databaseName){
        this.connection = connection;
        this.databaseName =databaseName;
    }

    public void insertIndex(Index index) throws SQLException {
        String sql =
                "INSERT INTO `" + databaseName + """
                    `.index(symbol, index_name, currency, currency_symbol, exchange_name, description, current_market_price, is_market_open)
                    VALUES(?,?,?,?,?,?,?,?);""";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, index.getSymbol());
        statement.setString(2, index.getName());
        statement.setString(3, index.getCurrency());
        statement.setString(4, index.getCurrencySymbol());
        statement.setString(5, index.getExchangeName());
        statement.setString(6, index.getDescription());
        statement.setDouble(7, index.getCurrentMarketPrice());
        statement.setBoolean(8, index.isMarketOpen());
        statement.executeUpdate();
    }

    public void updateIndexApiData(Index index) throws SQLException {
        String sql =
                "UPDATE `" + databaseName + "`.index\n" + """
                SET current_market_price = ?, is_market_open = ?
                WHERE symbol = ?;
                """;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDouble(1, index.getCurrentMarketPrice());
        statement.setBoolean(2, index.isMarketOpen());
        statement.setString(3, index.getSymbol());
        statement.executeUpdate();
    }
}
