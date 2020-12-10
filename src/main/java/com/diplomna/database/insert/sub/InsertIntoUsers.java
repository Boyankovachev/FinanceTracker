package com.diplomna.database.insert.sub;

import com.diplomna.users.sub.User;

import java.sql.*;

public class InsertIntoUsers {
    private Connection connection = null;
    private String databaseName = null;
    public InsertIntoUsers(Connection connection, String databaseName){
        this.connection = connection;
        this.databaseName =databaseName;
    }

    public void InsertUser(User user) throws SQLException {
        /*
        inserts the new user into the user table
         */
        int newUserId = insertNotNullValues(user);
        user.setUserId(newUserId);
        if(user.getEmail() != null && !user.getEmail().isEmpty()){
            insertEmail(user);
        }
        if(user.getIs2FactorAuthenticationRequired()){
            insertIs2faRequired(user);
        }
    }
    private int insertNotNullValues(User user) throws SQLException {
        /*
        returns the ID created by SQL in int
         */
        String sql =
                "INSERT INTO `" + databaseName + """
                    `.user(username, password_hash, salt)
                    VALUES(?,?,?);""";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.getUserName());
        statement.setString(2, user.getPasswordHash());
        statement.setString(3, user.getSalt());
        statement.executeUpdate();


        Statement selectStatement = connection.createStatement();
        String sql2 = "SELECT user_id FROM `" + databaseName + "`.user\n" +
                "WHERE username = '" + user.getUserName() + "';";
        ResultSet resultSet = selectStatement.executeQuery(sql2);
        resultSet.last();
        return resultSet.getInt(1);
    }
    private void insertEmail(User user) throws SQLException {
        String sql =
                "UPDATE `" + databaseName + "`.user\n" + """
                SET email = ?
                WHERE username = ? and user_id = ?;
                """;
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,user.getEmail());
        statement.setString(2,user.getUserName());
        statement.setInt(3,user.getUserId());
        statement.executeUpdate();
    }
    private void insertIs2faRequired(User user) throws SQLException {
        String sql =
                "UPDATE `" + databaseName + "`.user\n" + """
                SET is_2fa_required = ?
                WHERE username = ? and user_id = ?;
                """;
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setBoolean(1,user.getIs2FactorAuthenticationRequired());
        statement.setString(2,user.getUserName());
        statement.setInt(3,user.getUserId());
        statement.executeUpdate();
    }
}
