package com.diplomna.database.create;


import java.sql.*;

public class CreateDatabase {
    private String connString;
    private Connection con;
    public String databaseName;
    public CreateDatabase(String databaseName){
        this.connString = "jdbc:mysql://localhost:3306/?user=root&password=1234";
        this.databaseName = databaseName;
        try {
            this.con = DriverManager.getConnection(connString);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createWholeDb(){
        //works
        createDb();
        createUserTable();
        createStockTable();
        createNotificationTable();
        createStockPurchaseInfoTable();
        createPassiveResourceInfoTable();
    }

    public void createDb(){
        //works
        try{
            Statement st = con.createStatement();
            String sql = "CREATE DATABASE IF NOT EXISTS " + databaseName + ";";
            st.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createUserTable(){
        //works
        try {
            Statement DbStatement = con.createStatement();
            String sql =
                    "CREATE TABLE IF NOT EXISTS `"+databaseName+"`.`user`(\n" +
                    "    `user_id` INT NOT NULL AUTO_INCREMENT UNIQUE PRIMARY KEY,\n" +
                    "    `username` VARCHAR(32) NOT NULL,\n" +
                    "    `password_hash` VARCHAR(45) NOT NULL,\n" +
                    "    `salt` VARCHAR(45) NOT NULL,\n" +
                    "    `email` VARCHAR(32),\n" +
                    "    `is_2fa_required` BOOL);";
            DbStatement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createStockTable(){
        //works
        try {
            Statement DbStatement = con.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS `"+databaseName+"`.`stock`(\n" +
                    "    `symbol` VARCHAR(12) NOT NULL PRIMARY KEY,\n" +
                    "    `stock_name` VARCHAR(32) NOT NULL,\n" +
                    "    `currency` VARCHAR(32) NOT NULL,\n" +
                    "    `currency_symbol` VARCHAR(12) NOT NULL,\n" +
                    "    `exchange_name` VARCHAR(32),\n" +
                    "    `description` TEXT);\n";
            DbStatement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createNotificationTable(){
        //works
        try {
            Statement DbStatement = con.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS `"+databaseName+"`.`notification`(\n" +
                    "    `user_id` INT NOT NULL,\n" +
                    "    `asset_type` ENUM(\"stock\", \"passive_resource\", \"global\") NOT NULL,\n" +
                    "    `notification_price` DOUBLE NOT NULL,\n" +
                    "    `notification_name` VARCHAR(32) NOT NULL,\n" +
                    "    `stock_symbol` VARCHAR(12),\n" +
                    "    `passive_asset_name` VARCHAR(12),\n" +
                    "    FOREIGN KEY (`user_id`) REFERENCES `" + databaseName + "`.user(`user_id`));\n";
            DbStatement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createStockPurchaseInfoTable(){
        //works
        try {
            Statement DbStatement = con.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS `" + databaseName + "`.`stock_purchase_info`(\n" +
                    "    `user_id` INT NOT NULL,\n" +
                    "    `stock_symbol` VARCHAR(12) NOT NULL,\n" +
                    "    `price` DOUBLE NOT NULL,\n" +
                    "    `quantity` DOUBLE NOT NULL,\n" +
                    "    `purchase_date` DATE,\n" +
                    "    FOREIGN KEY(`user_id`) REFERENCES `" + databaseName + "`.user(`user_id`),\n" +
                    "    FOREIGN KEY(`stock_symbol`) REFERENCES `" + databaseName + "`.stock(`symbol`));\n";
            DbStatement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createPassiveResourceInfoTable(){
        //works
        try {
            Statement DbStatement = con.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS `" + databaseName + "`.`passive_resource`(\n" +
                    "    `user_id` INT NOT NULL,\n" +
                    "    `name` VARCHAR(32) NOT NULL,\n" +
                    "    `purchase_price` DOUBLE NOT NULL,\n" +
                    "    `purchase_date` DATE,\n" +
                    "    `current_price` DOUBLE,\n" +
                    "    `description` TEXT,\n" +
                    "    `currency` VARCHAR(32),\n" +
                    "    `currency_symbol` VARCHAR(12),\n" +
                    "    FOREIGN KEY(`user_id`) REFERENCES `" + databaseName + "`.user(`user_Id`));\n";
            DbStatement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
