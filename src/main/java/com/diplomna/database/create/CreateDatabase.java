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
        dropDb();
        createDb();
        createTableUsers();
        createTableNotifications();
        createTablePurchaseInfo();
        createTableStocks();
        createTablePassiveResources();
    }

    private void dropDb(){
        try {
            Statement DbStatement = con.createStatement();
            String sql = "DROP DATABASE IF EXISTS "+databaseName;
            DbStatement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createDb(){
        try {
            Statement DbStatement = con.createStatement();
            String sql = "CREATE DATABASE IF NOT EXISTS "+databaseName;
            DbStatement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTableUsers(){
        try{
            Statement st = con.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS " + databaseName + ".`users` (\n" +
                    "  `user_id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `username` VARCHAR(32) NOT NULL,\n" +
                    "  `password` VARCHAR(32) NOT NULL,\n" +
                    "  `email` VARCHAR(64) NULL,\n" +
                    "  PRIMARY KEY (`user_id`),\n" +
                    "  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) VISIBLE)";
            st.execute(sql);
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void createTableNotifications(){
        try{
            Statement st = con.createStatement();
            String sql = "    CREATE TABLE IF NOT EXISTS " + databaseName + ".`notifications` (\n" +
                    "        `user_id` INT NOT NULL,\n" +
                    "        `assets` TEXT NOT NULL,\n" +
                    "        `notification_price` DOUBLE NOT NULL,\n" +
                    "        `notification_name` VARCHAR(64) NULL,\n" +
                    "        UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC),\n" +
                    "        CONSTRAINT `user_id`\n" +
                    "        FOREIGN KEY (`user_id`)\n" +
                    "        REFERENCES " + databaseName + ".`users` (`user_id`)\n" +
                    "        ON DELETE NO ACTION\n" +
                    "        ON UPDATE NO ACTION)\n";
            st.execute(sql);
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void createTablePurchaseInfo(){
        try{
            Statement st = con.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS " + databaseName + ".`purchase_info` (\n" +
                    "  `purchase_id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `price` DOUBLE NOT NULL,\n" +
                    "  `quantity` DOUBLE NULL,\n" +
                    "  `purchase_date` DATE NULL,\n" +
                    "  PRIMARY KEY (`purchase_id`),\n" +
                    "  UNIQUE INDEX `purchase_id_UNIQUE` (`purchase_id` ASC) VISIBLE)\n";
            st.execute(sql);
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void createTableStocks(){
        try{
            Statement st = con.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS `test`.`stocks` (\n" +
                    "  `stock_user_id` INT NOT NULL,\n" +
                    "  `stock_purchase_id` INT NOT NULL,\n" +
                    "  `stock_symbol` VARCHAR(5) NOT NULL,\n" +
                    "  `stock_name` VARCHAR(32) NOT NULL,\n" +
                    "  `market_price` DOUBLE NOT NULL,\n" +
                    "  `quantity_owned` DOUBLE NOT NULL,\n" +
                    "  `currency` VARCHAR(32) NULL,\n" +
                    "  `description` TEXT NULL,\n" +
                    "  `currency_symbol` VARCHAR(5) NULL,\n" +
                    "  `is_market_open` TINYINT NULL,\n" +
                    "  `exchange_name` VARCHAR(45) NULL,\n" +
                    "  `recommendation_key` VARCHAR(16) NULL,\n" +
                    "  UNIQUE INDEX `user_id_UNIQUE` (`stock_user_id` ASC) VISIBLE,\n" +
                    "  UNIQUE INDEX `purchase_id_UNIQUE` (`stock_purchase_id` ASC) VISIBLE,\n" +
                    "  CONSTRAINT `stock_user_id`\n" +
                    "    FOREIGN KEY (`stock_user_id`)\n" +
                    "    REFERENCES `test`.`users` (`user_id`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT `stock_purchase_id`\n" +
                    "    FOREIGN KEY (`stock_purchase_id`)\n" +
                    "    REFERENCES `test`.`purchase_info` (`purchase_id`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION)\n" +
                    "ENGINE = InnoDB";
            st.execute(sql);
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void createTablePassiveResources(){
        try{
            Statement st = con.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS `test`.`passive_resources` (\n" +
                    "  `passive_resource_user_id` INT NOT NULL,\n" +
                    "  `passive_resource_purchase_id` INT NOT NULL,\n" +
                    "  `name` VARCHAR(32) NOT NULL,\n" +
                    "  `market_price` DOUBLE NOT NULL,\n" +
                    "  `currency` VARCHAR(32) NULL,\n" +
                    "  `description` TEXT NULL,\n" +
                    "  `currency_symbol` VARCHAR(5) NULL,\n" +
                    "  UNIQUE INDEX `user_id_UNIQUE` (`passive_resource_user_id` ASC) VISIBLE,\n" +
                    "  UNIQUE INDEX `purchase_id_UNIQUE` (`passive_resource_purchase_id` ASC) VISIBLE,\n" +
                    "  CONSTRAINT `passive_resource_user_id`\n" +
                    "    FOREIGN KEY (`passive_resource_user_id`)\n" +
                    "    REFERENCES `test`.`users` (`user_id`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT `passive_resource_purchase_id`\n" +
                    "    FOREIGN KEY (`passive_resource_purchase_id`)\n" +
                    "    REFERENCES `test`.`purchase_info` (`purchase_id`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION)\n" +
                    "ENGINE = InnoDB";
            st.execute(sql);
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}