package com.diplomna.database.read.sub;

import com.diplomna.assets.finished.Stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReadStock {
    private Connection connection = null;
    private String databaseName = null;
    public ReadStock(Connection connection, String databaseName){
        this.connection = connection;
        this.databaseName =databaseName;
    }

    public Stock readStockBySymbol(String symbol) throws SQLException {
        String sql = "SELECT * FROM `" + databaseName +"`.stock\n" +
                "WHERE symbol = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,symbol);
        ResultSet resultSet = statement.executeQuery();
        Stock stock = new Stock();
        while (resultSet.next()) {
            stock.setSymbol(symbol); //  ==  stock.setSymbol(resultSet.getString(1));
            stock.setName(resultSet.getString(2));
            stock.setCurrency(resultSet.getString(3));
            stock.setCurrencySymbol(resultSet.getString(4));
            stock.setExchangeName(resultSet.getString(5));
            stock.setDescription(resultSet.getString(6));
        }
        return stock;
    }
}