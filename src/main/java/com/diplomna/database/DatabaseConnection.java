package com.diplomna.database;

import com.diplomna.database.delete.DeleteFromDb;
import com.diplomna.database.insert.InsertIntoDb;
import com.diplomna.database.read.ReadFromDb;
import com.diplomna.restapi.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class DatabaseConnection {

    @Autowired
    private final DatabaseConfig databaseConfig;

    private ReadFromDb read;
    private InsertIntoDb add;
    private DeleteFromDb delete;

    public DatabaseConnection(DatabaseConfig databaseConfig){
        this.databaseConfig = databaseConfig;

        Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);

        String user = databaseConfig.getUser();
        String password = databaseConfig.getPassword();
        String connString = "jdbc:mysql://localhost:3306/?user=" + user + "&password=" + password;
        try {

            String databaseName = "test";

            Connection con = DriverManager.getConnection(connString);

            this.read = new ReadFromDb(con, databaseName);
            this.add = new InsertIntoDb(con, databaseName);
            this.delete = new DeleteFromDb(con, databaseName);

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
