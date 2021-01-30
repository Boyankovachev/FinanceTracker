package com.diplomna.database.read.sub;

import com.diplomna.assets.finished.Commodities;
import com.diplomna.assets.finished.Crypto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReadCommodity {
    private Connection connection = null;
    private String databaseName = null;
    public ReadCommodity(Connection connection, String databaseName){
        this.connection = connection;
        this.databaseName =databaseName;
    }

    public Commodities readCommodityByName(String commodityName) throws SQLException {
        String sql = "SELECT * FROM `" + databaseName +"`.commodity\n" +
                "WHERE commodity_name = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,commodityName);
        ResultSet resultSet = statement.executeQuery();
        Commodities commodity = new Commodities();

        if(!resultSet.next()){
            return null;
        }
        resultSet.beforeFirst();

        while (resultSet.next()) {
            commodity.setName(commodityName); //  ==  commodity.setName(resultSet.getString(1));
            commodity.setCurrency(resultSet.getString(2));
            commodity.setCurrencySymbol(resultSet.getString(3));
            commodity.setExchangeName(resultSet.getString(4));
            commodity.setDescription(resultSet.getString(5));
            commodity.setCurrentMarketPrice(resultSet.getDouble(6));
        }
        return commodity;
    }
    public List<Commodities> readAllCommodities()throws SQLException {
        String sql = "SELECT * FROM `" + databaseName +"`.commodity;";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        if(!resultSet.next()){
            return null;
        }
        resultSet.beforeFirst();

        List<Commodities> commodities = new ArrayList<>();

        while (resultSet.next()) {
            Commodities commodity = new Commodities();
            commodity.setName(resultSet.getString(1));
            commodity.setCurrency(resultSet.getString(2));
            commodity.setCurrencySymbol(resultSet.getString(3));
            commodity.setExchangeName(resultSet.getString(4));
            commodity.setDescription(resultSet.getString(5));
            commodity.setCurrentMarketPrice(resultSet.getDouble(6));
            commodities.add(commodity);
        }
        return commodities;
    }

    public List<String> readAllCommodityNames() throws SQLException {
        String sql = "SELECT `commodity_name` FROM `" + databaseName +"`.commodity";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        List<String> commodity = new ArrayList<>();
        while (resultSet.next()) {
            commodity.add(resultSet.getString(1));
        }
        return commodity;
    }
}
