package com.diplomna.database.read.sub;

import com.diplomna.assets.finished.Commodities;
import com.diplomna.assets.finished.Crypto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        while (resultSet.next()) {
            commodity.setName(commodityName); //  ==  commodity.setName(resultSet.getString(1));
            commodity.setCurrency(resultSet.getString(2));
            commodity.setCurrencySymbol(resultSet.getString(3));
            commodity.setExchangeName(resultSet.getString(4));
            commodity.setDescription(resultSet.getString(5));
        }
        return commodity;
    }
}
/*
            String sql = "CREATE TABLE IF NOT EXISTS `"+databaseName+"`.`commodity`(\n" +
                    "    `commodity_name` VARCHAR(64) NOT NULL PRIMARY KEY,\n" +
                    "    `currency` VARCHAR(32) NOT NULL,\n" +
                    "    `currency_symbol` VARCHAR(12) NOT NULL,\n" +
                    "    `exchange_name` VARCHAR(32),\n" +
                    "    `description` TEXT);\n";
 */