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
}
