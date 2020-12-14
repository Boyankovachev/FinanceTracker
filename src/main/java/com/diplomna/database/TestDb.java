package com.diplomna.database;

import com.diplomna.database.create.CreateDatabase;
import com.diplomna.database.create.InsertTestDataIntoDb;

public class TestDb {
    public static void main(String []args) {

        CreateDatabase createDatabase = new CreateDatabase("test");
        createDatabase.createWholeDb();

        InsertTestDataIntoDb insertTestDataIntoDb = new InsertTestDataIntoDb();
        insertTestDataIntoDb.insertTestData("test");
    }
}
