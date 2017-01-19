package com.haulmont.utils;

import java.sql.*;
import java.util.List;

public abstract class Dao {
    protected Connection conn;

    public Dao() throws ClassNotFoundException, SQLException {
        loadDriver();
        getConnection();
        createTable();
    }

    private void loadDriver() throws ClassNotFoundException {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Драйвер не найден");
            e.printStackTrace();
            throw new ClassNotFoundException();
        }
    }

    private void getConnection() throws SQLException {
        try {
//            String path = "./src/main/resources/dataDB/";
            String path = "D:/Java/Houlmont/HoulmontTask/src/main/resources/dataDB/";
            String dbname = "mydb";
            String connectionString = "jdbc:hsqldb:file:" + path + dbname;
            String login = "joe";
            String password = "password";
            conn = DriverManager.getConnection(connectionString, login, password);
        } catch (SQLException e) {
            System.out.println("Соединение не создано");
            e.printStackTrace();
            throw new SQLException();
        }
    }

    public void closeConnection() {
        try(Statement statement = conn.createStatement()) {
            String sql = "SHUTDOWN";
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public abstract void createTable();

    public abstract void addRow(Object object);

    public abstract void updateRow(Object object);

    public abstract boolean deleteRow(Object object);

    public abstract List getTable();

}
