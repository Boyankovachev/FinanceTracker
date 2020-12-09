package com.diplomna.database.create;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class InsertIntoDb {
    private String connString;
    private Connection con;
    public String databaseName;
    public InsertIntoDb(String databaseName){
        this.connString = "jdbc:mysql://localhost:3306/?user=root&password=1234";
        this.databaseName = databaseName;
        try {
            this.con = DriverManager.getConnection(connString);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
