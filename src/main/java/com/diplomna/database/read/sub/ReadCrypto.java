package com.diplomna.database.read.sub;

import com.diplomna.assets.finished.Crypto;
import com.diplomna.assets.finished.Index;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        while (resultSet.next()) {
            crypto.setSymbol(symbol); //  ==  crypto.setSymbol(resultSet.getString(1));
            crypto.setName(resultSet.getString(2));
            crypto.setCurrency(resultSet.getString(3));
            crypto.setCurrencySymbol(resultSet.getString(4));
            crypto.setDescription(resultSet.getString(5));
        }
        return crypto;
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
