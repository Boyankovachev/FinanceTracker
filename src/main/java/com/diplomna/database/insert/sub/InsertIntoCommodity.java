package com.diplomna.database.insert.sub;

import com.diplomna.assets.finished.Commodities;
import com.diplomna.assets.finished.Crypto;
import com.diplomna.assets.finished.Index;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertIntoCommodity {
    private Connection connection = null;
    private String databaseName = null;
    public InsertIntoCommodity(Connection connection, String databaseName){
        this.connection = connection;
        this.databaseName =databaseName;
    }

    public void insertCommodity(Commodities commodity) throws SQLException {
        String sql =
                "INSERT INTO `" + databaseName + """
                    `.commodity(commodity_name, currency, currency_symbol, exchange_name, description)
                    VALUES(?,?,?,?,?,?);""";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, commodity.getName());
        statement.setString(2, commodity.getCurrency());
        statement.setString(3, commodity.getCurrencySymbol());
        statement.setString(4, commodity.getExchangeName());
        statement.setString(5, commodity.getDescription());
        statement.setDouble(6, commodity.getCurrentMarketPrice());
        statement.executeUpdate();
    }
    public void updateCommodityApiData(Commodities commodity) throws SQLException {
        String sql =
                "UPDATE `" + databaseName + "`.commodity\n" + """
                SET current_market_price = ?
                WHERE commodity_name = ?;
                """;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDouble(1, commodity.getCurrentMarketPrice());
        statement.setString(2, commodity.getName());
        statement.executeUpdate();
    }
}
