package com.diplomna.database.insert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class InsertIntoDb {
    private String connString;
    private Connection con;
    private String user;
    private String password;
    public String databaseName;
    public InsertIntoDb(String databaseName){
        user = "root";
        password = "1234";
        this.connString = "jdbc:mysql://localhost:3306/?user=" + user + "&password=" + password;
        this.databaseName = databaseName;
        try {
            this.con = DriverManager.getConnection(connString);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
