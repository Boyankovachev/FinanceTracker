package com.diplomna.database.insert.sub;

import com.diplomna.assets.finished.Stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertIntoStock {
    private Connection connection = null;
    private String databaseName = null;
    public InsertIntoStock(Connection connection, String databaseName){
        this.connection = connection;
        this.databaseName =databaseName;
    }

    public void insertStock(Stock stock) throws SQLException {
        String sql =
                "INSERT INTO `" + databaseName + """
                    `.stock(symbol, stock_name, currency, currency_symbol, 
                        exchange_name, description)
                    VALUES(?,?,?,?,?,?,?,?,?);""";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,stock.getSymbol());
        statement.setString(2,stock.getName());
        statement.setString(3,stock.getCurrency());
        statement.setString(4,stock.getCurrencySymbol());
        statement.setString(5,stock.getExchangeName());
        statement.setString(6,stock.getDescription());
        statement.setDouble(7, stock.getCurrentMarketPrice());
        statement.setBoolean(8, stock.isMarketOpen());
        statement.setString(9, stock.getRecommendationKey());
        statement.executeUpdate();
    }

    public void updateStockApiData(Stock stock) throws SQLException {
        String sql =
                "UPDATE `" + databaseName + "`.stock\n" + """
                SET current_market_price = ?, is_market_open = ?, recommendation_key = ?
                WHERE symbol = ?;
                """;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDouble(1, stock.getCurrentMarketPrice());
        statement.setBoolean(2, stock.isMarketOpen());
        statement.setString(3, stock.getRecommendationKey());
        statement.setString(4, stock.getSymbol());
        statement.executeUpdate();
    }
}
