package com.diplomna.database.insert.sub;

import com.diplomna.users.sub.User;

import java.sql.Connection;

public class InsertIntoUsers {
    private Connection connection = null;
    public InsertIntoUsers(Connection connection){
        this.connection = connection;
    }

    public void InsertUser(User user){

    }
}
