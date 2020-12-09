package com.diplomna.database.create;

import java.sql.*;

public class InsertTestDataIntoDb {
    private String connString;
    private Connection con;
    public String databaseName;
    public InsertTestDataIntoDb(String databaseName){
        this.connString = "jdbc:mysql://localhost:3306/?user=root&password=1234";
        this.databaseName = databaseName;
        try {
            this.con = DriverManager.getConnection(connString);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void insertIntoUsers(){
        try{
            /*
            Statement statement = con.createStatement();
            String sql = """
                    use test;

                    INSERT INTO user(username, password_hash, salt, email)
                    VALUES("Ivan", "parolatanaivan", "soltanaivan", "ivan@gmail.com");
                    INSERT INTO user(username, password_hash, salt, email)
                    VALUES("Petar", "parolatanapetar", "soltanapetar", "petar@gmail.com");""";
            statement.execute(sql);

             */
            String sql = "INSERT INTO user (username, password_hash, salt, email)"
                    + "?,?,?,?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, "Ivan");
            preparedStatement.setString(2, "parolatanaivan");
            preparedStatement.setString(3, "soltanaivan");
            preparedStatement.setString(4, "ivan@gmail.com");

            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println(throwables.getSQLState());
            System.out.println(throwables.getErrorCode());
            System.out.println(throwables.getMessage());
        }
    }

    public void test_things(){
        try {
            // sql - kato kopiram sql ot tablicata
            // sql2 - аз я написах и започнах да привеждам във вид
            // подобен на sql
            // sql работи - sql2 нищо че буквално са едно и също?!?!?!?!?
            // проблем решен - ' и `  СА РАЗЛИЧНИ ШИБАНИ ЗНАЦИ
            String sql = """
                    CREATE TABLE IF NOT EXISTS `test`.`user` (
                      `user_id` INT NOT NULL AUTO_INCREMENT,
                      `username` VARCHAR(32) NOT NULL,
                      `password_hash` VARCHAR(45) NOT NULL,
                      `salt` VARCHAR(45) NOT NULL,
                      `email` VARCHAR(45) NULL,
                      PRIMARY KEY (`user_id`),
                      UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) VISIBLE)
                    ENGINE = InnoDB""";
            String sql2 = """
                    CREATE TABLE IF NOT EXISTS `test`.`user` (
                      `user_id` INT NOT NULL AUTO_INCREMENT,
                      `username` VARCHAR(32) NOT NULL,
                      `password_hash` VARCHAR(45) NOT NULL,
                      `salt` VARCHAR(45) NOT NULL,
                      `email` VARCHAR(32) NULL,
                      PRIMARY KEY (`user_id`),
                      UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) VISIBLE)
                    ENGINE = InnoDB""";
            String sql3 = "CREATE TABLE IF NOT EXISTS `" + databaseName + "`.`user`(\n" +
                    "    user_id INT NOT NULL AUTO_INCREMENT UNIQUE PRIMARY KEY,\n" +
                    "    username VARCHAR(32) NOT NULL,\n" +
                    "    password_hash VARCHAR(45) NOT NULL,\n" +
                    "    salt VARCHAR(45) NOT NULL,\n" +
                    "    email VARCHAR(32));";
            Statement statement = con.createStatement();
            statement.execute(sql3);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}