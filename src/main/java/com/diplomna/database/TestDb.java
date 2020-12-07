package com.diplomna.database;

import com.diplomna.database.create.CreateDatabase;

public class TestDb {
    public static void main(String []args) {
        CreateDatabase tester = new CreateDatabase("test");
        tester.createWholeDb();
    }
}
