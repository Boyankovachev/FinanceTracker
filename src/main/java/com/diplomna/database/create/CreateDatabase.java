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
        // deletes the previous database
        // raboti v sql workbench
        // ne raboti kato statement.execute
        try {
            Statement DbStatement = con.createStatement();
            String sql = "DROP DATABASE IF EXISTS " + databaseName + ";\n" +
                    "\n" +
                    "CREATE DATABASE IF NOT EXISTS " + databaseName + ";\n" +
                    "\n" +
                    "use " + databaseName + ";\n" +
                    "\n" +
                    "CREATE TABLE IF NOT EXISTS user(\n" +
                    "\tuser_id INT NOT NULL AUTO_INCREMENT UNIQUE PRIMARY KEY,\n" +
                    "    username VARCHAR(32) NOT NULL,\n" +
                    "    password_hash VARCHAR(45) NOT NULL,\n" +
                    "    salt VARCHAR(45) NOT NULL,\n" +
                    "    email VARCHAR(32));\n" +
                    "    \n" +
                    "CREATE TABLE IF NOT EXISTS stock(\n" +
                    "\tsymbol VARCHAR(12) NOT NULL PRIMARY KEY,\n" +
                    "    stock_name VARCHAR(32) NOT NULL,\n" +
                    "    currency VARCHAR(32) NOT NULL,\n" +
                    "    currency_symbol VARCHAR(12) NOT NULL,\n" +
                    "    exchange_name VARCHAR(32),\n" +
                    "    description TEXT);\n" +
                    "    \n" +
                    "CREATE TABLE IF NOT EXISTS notification(\n" +
                    "\tuser_id INT NOT NULL,\n" +
                    "    asset_type ENUM(\"stock\", \"passive_resource\", \"global\") NOT NULL,\n" +
                    "    asset_id INT,\n" +
                    "    notification_price DOUBLE NOT NULL,\n" +
                    "    notification_name VARCHAR(32),\n" +
                    "\tFOREIGN KEY(user_id) REFERENCES user(user_id)\n" +
                    ");\n" +
                    "\n" +
                    "CREATE TABLE IF NOT EXISTS stock_purchase_info(\n" +
                    "\tuser_id INT NOT NULL,\n" +
                    "    stock_symbol VARCHAR(12) NOT NULL,\n" +
                    "    price DOUBLE NOT NULL,\n" +
                    "    quantity DOUBLE NOT NULL,\n" +
                    "    FOREIGN KEY(user_id) REFERENCES user(user_id),\n" +
                    "    FOREIGN KEY(stock_symbol) REFERENCES stock(symbol)\n" +
                    ");\n" +
                    "\n" +
                    "CREATE TABLE IF NOT EXISTS passive_resource(\n" +
                    "\tuser_id INT NOT NULL,\n" +
                    "    name VARCHAR(32) NOT NULL,\n" +
                    "    purchase_price DOUBLE NOT NULL,\n" +
                    "    purchase_date DOUBLE,\n" +
                    "    current_price DOUBLE,\n" +
                    "    description TEXT,\n" +
                    "    currency VARCHAR(32),\n" +
                    "    currency_symbol VARCHAR(12),\n" +
                    "    FOREIGN KEY(user_id) REFERENCES user(user_Id)\n" +
                    ");";
            DbStatement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}