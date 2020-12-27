package com.diplomna.database.read.sub;

import com.diplomna.assets.finished.Commodities;
import com.diplomna.assets.finished.Index;
import com.diplomna.assets.sub.PurchaseInfo;
import com.diplomna.date.DatеManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReadCommodityPurchases {
    private Connection connection = null;
    private String databaseName = null;
    public ReadCommodityPurchases(Connection connection, String databaseName){
        this.connection = connection;
        this.databaseName =databaseName;
    }

    public List<Commodities> readCommodityPurchasesByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM `" + databaseName +"`.commodity_purchase_info\n" +
                "WHERE user_id = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,userId);
        ResultSet resultSet = statement.executeQuery();
        List<Commodities> commoditiesList = new ArrayList<>();
        while (resultSet.next()){
            Commodities commodity = new Commodities();
            commodity.setName(resultSet.getString(2));
            PurchaseInfo purchaseInfo = new PurchaseInfo();
            purchaseInfo.setPrice(resultSet.getDouble(3));
            purchaseInfo.setQuantity(resultSet.getDouble(4));
            purchaseInfo.setPurchaseDate(new DatеManager(resultSet.getString(5)));
            commodity.addPurchase(purchaseInfo);

            commoditiesList.add(commodity);
        }
        return commoditiesList;
    }
}
/*
            String sql = "CREATE TABLE IF NOT EXISTS `" + databaseName + "`.`commodity_purchase_info`(\n" +
                    "    `user_id` INT NOT NULL,\n" +
                    "    `commodity_name` VARCHAR(64) NOT NULL,\n" +
                    "    `price` DOUBLE NOT NULL,\n" +
                    "    `quantity` DOUBLE NOT NULL,\n" +
                    "    `purchase_date` DATE,\n" +
                    "    FOREIGN KEY(`user_id`) REFERENCES `" + databaseName + "`.user(`user_id`),\n" +
                    "    FOREIGN KEY(`commodity_name`) REFERENCES `" + databaseName + "`.commodity(`commodity_name`));\n";
 */
