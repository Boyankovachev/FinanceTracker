package com.diplomna.database.delete.sub;


import com.diplomna.assets.finished.Stock;
import com.diplomna.users.sub.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteFromPassiveResource {
    private Connection connection = null;
    private String databaseName = null;
    public DeleteFromPassiveResource(Connection connection, String databaseName){
        this.connection = connection;
        this.databaseName =databaseName;
    }

    public void deletePassiveResource(int userId, String name) throws SQLException {
        String sql = "DELETE FROM `" + databaseName + "`.passive_resource " +
                "WHERE user_id = ? and name = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,userId);
        statement.setString(2,name);
        statement.executeUpdate();
    }

}
