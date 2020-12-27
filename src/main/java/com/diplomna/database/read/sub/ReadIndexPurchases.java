package com.diplomna.database.read.sub;

import com.diplomna.assets.finished.Index;
import com.diplomna.assets.finished.Stock;
import com.diplomna.assets.sub.PurchaseInfo;
import com.diplomna.date.DatеManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReadIndexPurchases {
    private Connection connection = null;
    private String databaseName = null;
    public ReadIndexPurchases(Connection connection, String databaseName){
        this.connection = connection;
        this.databaseName =databaseName;
    }

    public List<Index> readIndexPurchasesByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM `" + databaseName +"`.index_purchase_info\n" +
                "WHERE user_id = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,userId);
        ResultSet resultSet = statement.executeQuery();
        List<Index> indexList = new ArrayList<>();
        while (resultSet.next()){
            /*
            System.out.print(resultSet.getInt(1) + " ");
            System.out.print(resultSet.getString(2) + " ");
            System.out.print(resultSet.getDouble(3) + " ");
            System.out.print(resultSet.getDouble(4) + " ");
            System.out.println(resultSet.getString(5) + " ");
             */
            Index index = new Index();
            index.setSymbol(resultSet.getString(2));
            PurchaseInfo purchaseInfo = new PurchaseInfo();
            purchaseInfo.setPrice(resultSet.getDouble(3));
            purchaseInfo.setQuantity(resultSet.getDouble(4));
            purchaseInfo.setPurchaseDate(new DatеManager(resultSet.getString(5)));
            index.addPurchase(purchaseInfo);

            indexList.add(index);
        }
        return indexList;
    }
}
/*
            String sql = "CREATE TABLE IF NOT EXISTS `" + databaseName + "`.`index_purchase_info`(\n" +
                    "    `user_id` INT NOT NULL,\n" +
                    "    `index_symbol` VARCHAR(12) NOT NULL,\n" +
                    "    `price` DOUBLE NOT NULL,\n" +
                    "    `quantity` DOUBLE NOT NULL,\n" +
                    "    `purchase_date` DATE,\n" +
                    "    FOREIGN KEY(`user_id`) REFERENCES `" + databaseName + "`.user(`user_id`),\n" +
                    "    FOREIGN KEY(`index_symbol`) REFERENCES `" + databaseName + "`.index(`symbol`));\n";
 */
