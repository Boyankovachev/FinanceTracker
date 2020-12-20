package com.diplomna.database.read.sub;

import com.diplomna.users.sub.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReadUsers {
    private Connection connection = null;
    private String databaseName = null;
    public ReadUsers(Connection connection, String databaseName){
        this.connection = connection;
        this.databaseName =databaseName;
    }

    public List<User> readUsers() throws SQLException {
        String sql = "SELECT * FROM `" + databaseName +"`.user;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        List<User> users = new ArrayList<>();
        while (resultSet.next()){
            /*
            System.out.print(resultSet.getString(1) + " ");
            System.out.print(resultSet.getString(2) + " ");
            System.out.print(resultSet.getString(3) + " ");
            System.out.print(resultSet.getString(4) + " ");
            System.out.print(resultSet.getString(5) + " ");
            System.out.println(resultSet.getString(6) + " ");
             */
            users.add(new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getString(5), resultSet.getBoolean(6)));
        }
        return users;
    }
}
