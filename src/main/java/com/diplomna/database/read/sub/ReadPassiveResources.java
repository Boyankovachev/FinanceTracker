package com.diplomna.database.read.sub;

import com.diplomna.assets.finished.PassiveResource;
import com.diplomna.assets.finished.Stock;
import com.diplomna.assets.sub.PurchaseInfo;
import com.diplomna.date.DatеManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReadPassiveResources {
    private Connection connection = null;
    private String databaseName = null;
    public ReadPassiveResources(Connection connection, String databaseName){
        this.connection = connection;
        this.databaseName =databaseName;
    }

    public List<PassiveResource> readPassiveResourceByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM `" + databaseName +"`.passive_resource\n" +
                "WHERE user_id = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,userId);
        ResultSet resultSet = statement.executeQuery();
        List<PassiveResource> passiveResources = new ArrayList<>();
        while (resultSet.next()){
            PassiveResource passiveResource = new PassiveResource();
            passiveResource.setName(resultSet.getString(2));
            PurchaseInfo purchaseInfo = new PurchaseInfo();
            purchaseInfo.setPrice(resultSet.getDouble(3));
            purchaseInfo.setPurchaseDate(new DatеManager(resultSet.getString(4)));
            passiveResource.setPurchaseInfo(purchaseInfo);
            passiveResource.setCurrentMarketPrice(resultSet.getDouble(5));
            passiveResource.setDescription(resultSet.getString(6));
            passiveResource.setCurrency(resultSet.getString(7));
            passiveResource.setCurrencySymbol(resultSet.getString(8));

            passiveResources.add(passiveResource);
        }
        return passiveResources;
    }
}
