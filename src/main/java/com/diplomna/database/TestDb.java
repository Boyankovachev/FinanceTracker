package com.diplomna.database;

import com.diplomna.database.create.CreateDatabase;
import com.diplomna.database.create.InsertTestDataIntoDb;

public class TestDb {
        public static void main(String []args) {
            InsertTestDataIntoDb test = new InsertTestDataIntoDb("test");
            //test.insertIntoUsers();
            test.test_things();
    }
}