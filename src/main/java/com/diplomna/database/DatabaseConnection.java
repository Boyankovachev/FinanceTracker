package com.diplomna.database;

import com.diplomna.database.delete.DeleteFromDb;
import com.diplomna.database.insert.InsertIntoDb;
import com.diplomna.database.read.ReadFromDb;
import com.diplomna.restapi.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private final Logger logger;

    private ReadFromDb read;
    private InsertIntoDb add;
    private DeleteFromDb delete;

    private final String databaseName = "test";
    private Connection con;

    public DatabaseConnection(){

        this.logger = LoggerFactory.getLogger(DatabaseConnection.class);

        String user = "root";
        String password = "1234";
        String connString = "jdbc:mysql://localhost:3306/?user=" + user + "&password=" + password;
        try {
            this.con = DriverManager.getConnection(connString);

            read = new ReadFromDb(this.con, databaseName);
            add = new InsertIntoDb(this.con, databaseName);
            delete = new DeleteFromDb(this.con, databaseName);

        } catch (SQLException throwables) {
            logger.error("Couldn't create DB connection");
            throwables.printStackTrace();
        }
    }

    public ReadFromDb read(){
        return read;
    }
    public InsertIntoDb add(){
        return add;
    }
    public DeleteFromDb delete(){
        return delete;
    }
}
