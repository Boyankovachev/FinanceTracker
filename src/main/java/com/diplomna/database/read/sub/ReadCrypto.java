package com.diplomna.database.read.sub;

import com.diplomna.assets.finished.Crypto;
import com.diplomna.assets.finished.Index;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReadCrypto {
    private Connection connection = null;
    private String databaseName = null;
    public ReadCrypto(Connection connection, String databaseName){
        this.connection = connection;
        this.databaseName =databaseName;
    }

    public Crypto readCryptoBySymbol(String symbol) throws SQLException {
        String sql = "SELECT * FROM `" + databaseName +"`.crypto\n" +
                "WHERE symbol = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,symbol);
        ResultSet resultSet = statement.executeQuery();
        Crypto crypto = new Crypto();

        if(!resultSet.next()){
            return null;
        }
        resultSet.beforeFirst();

        while (resultSet.next()) {
            crypto.setSymbol(symbol); //  ==  crypto.setSymbol(resultSet.getString(1));
            crypto.setName(resultSet.getString(2));
            crypto.setCurrency(resultSet.getString(3));
            crypto.setCurrencySymbol(resultSet.getString(4));
            crypto.setDescription(resultSet.getString(5));
            crypto.setCurrentMarketPrice(resultSet.getDouble(6));
        }
        return crypto;
    }
    public List<Crypto> readAllCrypto() throws SQLException {
        String sql = "SELECT * FROM `" + databaseName +"`.crypto;";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        if(!resultSet.next()){
            return null;
        }
        resultSet.beforeFirst();

        List<Crypto> cryptos = new ArrayList<>();

        while (resultSet.next()) {
            Crypto crypto = new Crypto();
            crypto.setSymbol(resultSet.getString(1));
            crypto.setName(resultSet.getString(2));
            crypto.setCurrency(resultSet.getString(3));
            crypto.setCurrencySymbol(resultSet.getString(4));
            crypto.setDescription(resultSet.getString(5));
            crypto.setCurrentMarketPrice(resultSet.getDouble(6));
            cryptos.add(crypto);
        }
        return cryptos;
    }

    public List<String> readAllCryptoSymbols() throws SQLException {
        String sql = "SELECT `symbol` FROM `" + databaseName +"`.crypto";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        List<String> cryptos = new ArrayList<>();
        while (resultSet.next()) {
            cryptos.add(resultSet.getString(1));
        }
        return cryptos;
    }
}



/*
            String sql = "CREATE TABLE IF NOT EXISTS `"+databaseName+"`.`crypto`(\n" +
                    "    `symbol` VARCHAR(12) NOT NULL PRIMARY KEY,\n" +
                    "    `crypto_name` VARCHAR(32) NOT NULL,\n" +
                    "    `currency` VARCHAR(32) NOT NULL,\n" +
                    "    `currency_symbol` VARCHAR(12) NOT NULL,\n" +
                    "    `description` TEXT);\n";
 */
