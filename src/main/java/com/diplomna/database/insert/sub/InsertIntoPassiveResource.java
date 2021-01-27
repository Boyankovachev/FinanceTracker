package com.diplomna.database.insert.sub;

import com.diplomna.assets.finished.PassiveResource;
import com.diplomna.assets.finished.Stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertIntoPassiveResource {
    private Connection connection = null;
    private String databaseName = null;
    public InsertIntoPassiveResource(Connection connection, String databaseName){
        this.connection = connection;
        this.databaseName =databaseName;
    }

    public void insertPassiveResource(int userId, PassiveResource passiveResource) throws SQLException {
        String sql =
                "INSERT INTO `" + databaseName + """
                    `.passive_resource(user_id, name, purchase_price, purchase_date, current_price, description, currency, currency_symbol)
                    VALUES(?,?,?,?,?,?,?,?);""";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,userId);
        statement.setString(2,passiveResource.getName());
        statement.setDouble(3,passiveResource.getPurchaseInfo().getPrice());
        if(passiveResource.getPurchaseInfo().getPurchaseDate() != null) {
            statement.setDate(4, new java.sql.Date(passiveResource.getPurchaseInfo().getPurchaseDate().calendar.getTime().getTime()));
        }
        else {
            statement.setDate(4, null);
        }
        statement.setDouble(5, passiveResource.getCurrentMarketPrice());
        statement.setString(6,passiveResource.getDescription());
        statement.setString(7,passiveResource.getCurrency());
        statement.setString(8,passiveResource.getCurrencySymbol());
        statement.executeUpdate();
    }

    public void updatePassiveResourceCurrentPrice(int userId, double newPrice, String name) throws SQLException {
        //update passive_resource SET current_price = "69" WHERE user_id = 5 AND name = 'qwrthg';
        String sql = "UPDATE " + databaseName + ".`passive_resource` " +
                "SET current_price = ? " +
                "WHERE user_id = ? AND name = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDouble(1, newPrice);
        statement.setInt(2, userId);
        statement.setString(3, name);
        statement.executeUpdate();
    }
}
