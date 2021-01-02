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
        createIndexTable();
        createIndexPurchaseInfoTable();
        createCryptoTable();
        createCryptoPurchaseInfoTable();
        createCommodityTable();
        createCommodityPurchaseInfoTable();
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
                    "    `password_hash` VARCHAR(64) NOT NULL,\n" +
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
        // 24.12.2020 - vij zapiskite
        try {
            Statement DbStatement = con.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS `"+databaseName+"`.`notification`(\n" +
                    "    `user_id` INT NOT NULL,\n" +
                    "    `asset_type` ENUM(\"stock\", \"passive_resource\", \"index\", \"crypto\", \"commodity\", \"global\") NOT NULL,\n" +
                    "    `asset_type_settings` BOOL,\n" +
                    "    `notification_target` VARCHAR(12),\n" +
                    "    `notification_price` DOUBLE NOT NULL,\n" +
                    "    `notification_name` VARCHAR(32) NOT NULL,\n" +
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
    public void createIndexTable(){
        try {
            Statement DbStatement = con.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS `"+databaseName+"`.`index`(\n" +
                    "    `symbol` VARCHAR(12) NOT NULL PRIMARY KEY,\n" +
                    "    `index_name` VARCHAR(32) NOT NULL,\n" +
                    "    `currency` VARCHAR(32) NOT NULL,\n" +
                    "    `currency_symbol` VARCHAR(12) NOT NULL,\n" +
                    "    `exchange_name` VARCHAR(32),\n" +
                    "    `description` TEXT);\n";
            DbStatement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createIndexPurchaseInfoTable(){
        try {
            Statement DbStatement = con.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS `" + databaseName + "`.`index_purchase_info`(\n" +
                    "    `user_id` INT NOT NULL,\n" +
                    "    `index_symbol` VARCHAR(12) NOT NULL,\n" +
                    "    `price` DOUBLE NOT NULL,\n" +
                    "    `quantity` DOUBLE NOT NULL,\n" +
                    "    `purchase_date` DATE,\n" +
                    "    FOREIGN KEY(`user_id`) REFERENCES `" + databaseName + "`.user(`user_id`),\n" +
                    "    FOREIGN KEY(`index_symbol`) REFERENCES `" + databaseName + "`.index(`symbol`));\n";
            DbStatement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createCryptoTable(){
        try {
            Statement DbStatement = con.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS `"+databaseName+"`.`crypto`(\n" +
                    "    `symbol` VARCHAR(12) NOT NULL PRIMARY KEY,\n" +
                    "    `crypto_name` VARCHAR(32) NOT NULL,\n" +
                    "    `currency` VARCHAR(32) NOT NULL,\n" +
                    "    `currency_symbol` VARCHAR(12) NOT NULL,\n" +
                    "    `description` TEXT);\n";
            DbStatement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createCryptoPurchaseInfoTable(){
        try {
            Statement DbStatement = con.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS `" + databaseName + "`.`crypto_purchase_info`(\n" +
                    "    `user_id` INT NOT NULL,\n" +
                    "    `crypto_symbol` VARCHAR(12) NOT NULL,\n" +
                    "    `price` DOUBLE NOT NULL,\n" +
                    "    `quantity` DOUBLE NOT NULL,\n" +
                    "    `purchase_date` DATE,\n" +
                    "    FOREIGN KEY(`user_id`) REFERENCES `" + databaseName + "`.user(`user_id`),\n" +
                    "    FOREIGN KEY(`crypto_symbol`) REFERENCES `" + databaseName + "`.crypto(`symbol`));\n";
            DbStatement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createCommodityTable(){
        try {
            Statement DbStatement = con.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS `"+databaseName+"`.`commodity`(\n" +
                    "    `commodity_name` VARCHAR(64) NOT NULL PRIMARY KEY,\n" +
                    "    `currency` VARCHAR(32) NOT NULL,\n" +
                    "    `currency_symbol` VARCHAR(12) NOT NULL,\n" +
                    "    `exchange_name` VARCHAR(32),\n" +
                    "    `description` TEXT);\n";
            DbStatement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createCommodityPurchaseInfoTable(){
        try {
            Statement DbStatement = con.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS `" + databaseName + "`.`commodity_purchase_info`(\n" +
                    "    `user_id` INT NOT NULL,\n" +
                    "    `commodity_name` VARCHAR(64) NOT NULL,\n" +
                    "    `price` DOUBLE NOT NULL,\n" +
                    "    `quantity` DOUBLE NOT NULL,\n" +
                    "    `purchase_date` DATE,\n" +
                    "    FOREIGN KEY(`user_id`) REFERENCES `" + databaseName + "`.user(`user_id`),\n" +
                    "    FOREIGN KEY(`commodity_name`) REFERENCES `" + databaseName + "`.commodity(`commodity_name`));\n";
            DbStatement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
