package com.diplomna.database.create;

import java.util.Scanner;

public class CreateDatabaseExecute {
    public static void main(String []args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter name of database to create:");
        String databaseName = scanner.nextLine();

        CreateDatabase createDatabase = new CreateDatabase(databaseName);
        createDatabase.createWholeDb();
    }
}
