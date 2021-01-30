package com.diplomna.database.insert.sub;

import com.diplomna.assets.finished.Crypto;
import com.diplomna.assets.finished.Index;
import com.diplomna.assets.finished.Stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertIntoCrypto {
    private Connection connection = null;
    private String databaseName = null;
    public InsertIntoCrypto(Connection connection, String databaseName){
        this.connection = connection;
        this.databaseName =databaseName;
    }

    public void insertCrypto(Crypto crypto) throws SQLException {
        String sql =
                "INSERT INTO `" + databaseName + """
                    `.crypto(symbol, crypto_name, currency, currency_symbol, description)
                    VALUES(?,?,?,?,?,?);""";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,crypto.getSymbol());
        statement.setString(2,crypto.getName());
        statement.setString(3,crypto.getCurrency());
        statement.setString(4,crypto.getCurrencySymbol());
        statement.setString(5,crypto.getDescription());
        statement.setDouble(6, crypto.getCurrentMarketPrice());
        statement.executeUpdate();
    }

    public void updateCryptoApiData(Crypto crypto) throws SQLException {
        String sql =
                "UPDATE `" + databaseName + "`.crypto\n" + """
                SET current_market_price = ?
                WHERE symbol = ?;
                """;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDouble(1, crypto.getCurrentMarketPrice());
        statement.setString(2, crypto.getSymbol());
        statement.executeUpdate();
    }
}
