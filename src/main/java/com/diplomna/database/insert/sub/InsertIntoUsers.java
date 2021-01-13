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

    public void insertUser(User user) throws SQLException {
        /*
        inserts the new user into the user table
         */
        int newUserId = insertNotNullValues(user); //get the Id of the new user
        user.setUserId(newUserId);
        if(user.getEmail() != null && !user.getEmail().isEmpty()){
            updateEmail(user);
        }
        if(user.getIs2FactorAuthenticationRequired()){
            updateIs2faRequired(user);
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
    public void updateEmail(User user) throws SQLException {
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
    public void updateIs2faRequired(User user) throws SQLException {
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

    public void updateUsername(User user) throws SQLException {
        String sql =
                "UPDATE `" + databaseName + "`.user\n" + """
                SET username = ?
                WHERE user_id = ?;
                """;
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,user.getUserName());
        statement.setInt(2,user.getUserId());
        statement.executeUpdate();
    }

    public void updatePassword(User user) throws SQLException {
        String sql =
                "UPDATE `" + databaseName + "`.user\n" + """
                SET password_hash = ?, salt = ?
                WHERE user_id = ?;
                """;
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,user.getPasswordHash());
        statement.setString(2,user.getSalt());
        statement.setInt(3,user.getUserId());
        statement.executeUpdate();
    }
}
