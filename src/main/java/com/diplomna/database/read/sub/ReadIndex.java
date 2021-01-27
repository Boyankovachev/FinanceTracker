package com.diplomna.database.read.sub;

import com.diplomna.assets.finished.Index;
import com.diplomna.assets.finished.Stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReadIndex {
    private Connection connection = null;
    private String databaseName = null;
    public ReadIndex(Connection connection, String databaseName){
        this.connection = connection;
        this.databaseName =databaseName;
    }

    public Index readIndexBySymbol(String symbol) throws SQLException {
        String sql = "SELECT * FROM `" + databaseName +"`.index\n" +
                "WHERE symbol = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,symbol);
        ResultSet resultSet = statement.executeQuery();

        Index index = new Index();

        if(!resultSet.next()){
            return null;
        }
        resultSet.beforeFirst();

        while (resultSet.next()) {
            index.setSymbol(symbol); //  ==  index.setSymbol(resultSet.getString(1));
            index.setName(resultSet.getString(2));
            index.setCurrency(resultSet.getString(3));
            index.setCurrencySymbol(resultSet.getString(4));
            index.setExchangeName(resultSet.getString(5));
            index.setDescription(resultSet.getString(6));
        }
        return index;
    }

    public List<String> readAllIndexSymbols() throws SQLException {
        String sql = "SELECT `symbol` FROM `" + databaseName +"`.index\n";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        List<String> indexes = new ArrayList<>();
        while (resultSet.next()) {
            indexes.add(resultSet.getString(1));
        }
        return indexes;
    }
}
