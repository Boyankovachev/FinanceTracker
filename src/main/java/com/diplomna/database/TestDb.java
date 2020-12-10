package com.diplomna.database;

import com.diplomna.database.create.CreateDatabase;
import com.diplomna.database.create.InsertTestDataIntoDb;
import com.diplomna.database.insert.InsertIntoDb;
import com.diplomna.users.sub.User;

public class TestDb {
        public static void main(String []args) {
            //InsertTestDataIntoDb test = new InsertTestDataIntoDb("test");
            //test.insertIntoUsers();
            //test.test_things();

            InsertIntoDb insert = new InsertIntoDb("test");
            //insert.InsertUser(new User("Venci2", "veneee", "soltanavenci2"));
            User user1 = new User("Bojidar", "bojidarhash","bojidarsol");
            user1.setEmail("bojidar@gmail.com");
            user1.setIs2FactorAuthenticationRequired(true);
            insert.InsertUser(user1);
    }
}