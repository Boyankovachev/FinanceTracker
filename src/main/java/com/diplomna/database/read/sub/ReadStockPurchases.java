package com.diplomna.database.read.sub;

import com.diplomna.assets.finished.Stock;
import com.diplomna.assets.sub.PurchaseInfo;
import com.diplomna.date.DatеManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReadStockPurchases {
    private Connection connection = null;
    private String databaseName = null;
    public ReadStockPurchases(Connection connection, String databaseName){
        this.connection = connection;
        this.databaseName =databaseName;
    }

    public List<Stock> readStockPurchasesByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM `" + databaseName +"`.stock_purchase_info\n" +
                "WHERE user_id = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,userId);
        ResultSet resultSet = statement.executeQuery();
        List<Stock> stocks = new ArrayList<>();
        while (resultSet.next()){
            /*
            System.out.print(resultSet.getInt(1) + " ");
            System.out.print(resultSet.getString(2) + " ");
            System.out.print(resultSet.getDouble(3) + " ");
            System.out.print(resultSet.getDouble(4) + " ");
            System.out.println(resultSet.getString(5) + " ");
             */
            Stock stock = new Stock();
            stock.setSymbol(resultSet.getString(2));
            PurchaseInfo purchaseInfo = new PurchaseInfo();
            purchaseInfo.setPrice(resultSet.getDouble(3));
            purchaseInfo.setQuantity(resultSet.getDouble(4));
            purchaseInfo.setPurchaseDate(new DatеManager(resultSet.getString(5)));
            stock.addPurchase(purchaseInfo);

            stocks.add(stock);
        }
        return stocks;
    }
}
