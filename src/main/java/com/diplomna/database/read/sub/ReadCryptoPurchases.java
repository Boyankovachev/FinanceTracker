package com.diplomna.database.read.sub;

import com.diplomna.assets.finished.Crypto;
import com.diplomna.assets.finished.Index;
import com.diplomna.assets.sub.PurchaseInfo;
import com.diplomna.date.DatеManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReadCryptoPurchases {
    private Connection connection = null;
    private String databaseName = null;
    public ReadCryptoPurchases(Connection connection, String databaseName){
        this.connection = connection;
        this.databaseName =databaseName;
    }

    public List<Crypto> readCryptoPurchasesByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM `" + databaseName +"`.crypto_purchase_info\n" +
                "WHERE user_id = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,userId);
        ResultSet resultSet = statement.executeQuery();
        List<Crypto> cryptoList = new ArrayList<>();
        while (resultSet.next()){
            Crypto crypto = new Crypto();
            crypto.setSymbol(resultSet.getString(2));
            PurchaseInfo purchaseInfo = new PurchaseInfo();
            purchaseInfo.setPrice(resultSet.getDouble(3));
            purchaseInfo.setQuantity(resultSet.getDouble(4));
            purchaseInfo.setPurchaseDate(new DatеManager(resultSet.getString(5)));
            crypto.addPurchase(purchaseInfo);

            cryptoList.add(crypto);
        }
        return cryptoList;
    }
}
/*
            String sql = "CREATE TABLE IF NOT EXISTS `" + databaseName + "`.`crypto_purchase_info`(\n" +
                    "    `user_id` INT NOT NULL,\n" +
                    "    `crypto_symbol` VARCHAR(12) NOT NULL,\n" +
                    "    `price` DOUBLE NOT NULL,\n" +
                    "    `quantity` DOUBLE NOT NULL,\n" +
                    "    `purchase_date` DATE,\n" +
                    "    FOREIGN KEY(`user_id`) REFERENCES `" + databaseName + "`.user(`user_id`),\n" +
                    "    FOREIGN KEY(`crypto_symbol`) REFERENCES `" + databaseName + "`.crypto(`symbol`));\n";
 */