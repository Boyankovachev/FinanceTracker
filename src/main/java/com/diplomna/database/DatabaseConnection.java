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

        String connString;
        String databaseName;
        try {
            if(databaseConfig.getDbHost().equals("local")) {
                //If my local db
                String user = databaseConfig.getUser();
                String password = databaseConfig.getPassword();
                connString = "jdbc:mysql://localhost:3306/?user=" + user + "&password=" + password;
                databaseName = databaseConfig.getDatabaseName();

            }else {
                connString = "mysql://b361c5f743004c:b180e428@us-cdbr-east-04.cleardb.com/heroku_e44f5a628f2c41c?reconnect=true";
                databaseName = "heroku_e44f5a628f2c41c";
            }
            Connection con = DriverManager.getConnection(connString);

            this.read = new ReadFromDb(con, databaseName);
            this.add = new InsertIntoDb(con, databaseName);
            this.delete = new DeleteFromDb(con, databaseName);

            if(read == null) {
                System.out.println("Read is null");
            }

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
