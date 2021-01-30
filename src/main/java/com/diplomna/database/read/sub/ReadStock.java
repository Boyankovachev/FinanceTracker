package com.diplomna.database.read.sub;

import com.diplomna.assets.finished.Stock;
import com.diplomna.assets.sub.ActiveAsset;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

        if(!resultSet.next()){
            return null;
        }
        resultSet.beforeFirst();

        while (resultSet.next()) {
            stock.setSymbol(symbol); //  ==  stock.setSymbol(resultSet.getString(1));
            stock.setName(resultSet.getString(2));
            stock.setCurrency(resultSet.getString(3));
            stock.setCurrencySymbol(resultSet.getString(4));
            stock.setExchangeName(resultSet.getString(5));
            stock.setDescription(resultSet.getString(6));
            stock.setCurrentMarketPrice(resultSet.getDouble(7));
            stock.setMarketOpen(resultSet.getBoolean(8));
            stock.setRecommendationKey(resultSet.getString(9));
        }
        return stock;
    }

    public List<Stock> readAllStocks() throws SQLException {
        String sql = "SELECT * FROM `" + databaseName +"`.stock;";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        List<Stock> stocks = new ArrayList<>();

        if(!resultSet.next()){
            return null;
        }
        resultSet.beforeFirst();

        while (resultSet.next()) {
            Stock stock = new Stock();
            stock.setSymbol(resultSet.getString(1));
            stock.setName(resultSet.getString(2));
            stock.setCurrency(resultSet.getString(3));
            stock.setCurrencySymbol(resultSet.getString(4));
            stock.setExchangeName(resultSet.getString(5));
            stock.setDescription(resultSet.getString(6));
            stock.setCurrentMarketPrice(resultSet.getDouble(7));
            stock.setMarketOpen(resultSet.getBoolean(8));
            stock.setRecommendationKey(resultSet.getString(9));

            stocks.add(stock);
        }
        return stocks;
    }

    public List<String> readAllStocksSymbols() throws SQLException {
        String sql = "SELECT `symbol` FROM `" + databaseName +"`.stock";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        List<String> stocks = new ArrayList<>();
        while (resultSet.next()) {
            stocks.add(resultSet.getString(1));
        }
        return stocks;
    }
}
