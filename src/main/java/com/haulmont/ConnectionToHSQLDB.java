package com.haulmont;

import com.haulmont.DataFromTable.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by ermakov on 15.12.2016.
 */
public class ConnectionToHSQLDB {
    private Connection conn;
    private String columnsOrders = "";

    public ConnectionToHSQLDB() throws ClassNotFoundException, SQLException {
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
            String path = "/src/main/resources/dataDB/";
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

    public void createTable() {
        try(Statement statement = conn.createStatement()) {
            String sql = "CREATE TABLE clients (clients_id IDENTITY , " +
                    "firstname VARCHAR(255) , " +
                    "surname VARCHAR(255) ," +
                    "middlename VARCHAR(255) , " +
                    "tel VARCHAR(40)) ";
            statement.executeUpdate(sql);
            sql = "CREATE TABLE orders (orders_id IDENTITY , " +
                    "about_order VARCHAR(255) , " +
                    "clients_id INT, " +
                    "create_date DATE , " +
                    "end_date DATE , " +
                    "price DECIMAL (64, 2) , " +
                    "status VARCHAR(255))";
            statement.executeUpdate(sql);
            sql = "ALTER TABLE orders ADD FOREIGN KEY (clients_id) REFERENCES customers(clients_id)";
            statement.executeUpdate(sql);

        } catch (SQLException ignored) {
        }
    }

    public void addRowsToClient(String firstName, String surName, String middleName, String tel) {
        try(PreparedStatement statement =
                    conn.prepareStatement(
                            "INSERT INTO clients (firstname, surname, middlename, tel) " +
                                    "VALUES (?, ?, ?, ?)")) {
            statement.setString(1, firstName);
            statement.setString(2, surName);
            statement.setString(3, middleName);
            statement.setString(4, tel);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addRowsToOrders(String aboutOrder, int clients_id, int days, double price, String status) {
        GregorianCalendar calendar = new GregorianCalendar();
        Date createOrders = new Date(calendar.getTimeInMillis());
        calendar.add(Calendar.DAY_OF_YEAR, days);
        Date endOrders = new Date(calendar.getTimeInMillis());
        try(PreparedStatement statement =
                    conn.prepareStatement(
                            "INSERT INTO orders (about_order, clients_id, create_date, end_date, price, status) " +
                                    "VALUES (?, ?, ?, ?, ?, ?)")) {
            statement.setString(1, aboutOrder);
            statement.setInt(2, clients_id);
            statement.setDate(3, createOrders);
            statement.setDate(4, endOrders);
            statement.setDouble(5, price);
            statement.setString(6, status);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteTable(String tableName, String id) {
        try(Statement statement = conn.createStatement()) {
            String sql = String.format("DELETE FROM %s WHERE id = '%s'", tableName, id);
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Client> getTableClients() {
        List<Client> clients = new ArrayList<>();
        int id;
        String firstName;
        String surName;
        String middleName;
        String telephon;
        try (Statement statement = conn.createStatement()) {
            String sql;
            sql = "SELECT * FROM clients";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                id = resultSet.getInt("clients_id");
                firstName = resultSet.getNString("firstname");
                surName = resultSet.getNString("surname");
                middleName = resultSet.getNString("midllename");
                telephon = resultSet.getNString("tel");

                clients.add(new Client(id, firstName, surName, middleName, telephon));
            }
            return clients;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public List<String> printTable(String tableName) {
//        List<String> rows = new ArrayList<>();
//        try(Statement statement = conn.createStatement()) {
//            String sql = null;
//            if (tableName.equals("orders")) {
//                sql = "SELECT * FROM orders NATURAL JOIN clients";
//            } else if(tableName.equals("clients")){
//                sql = "SELECT * FROM clients";
//            }
//            ResultSet resultSet = statement.executeQuery(sql);
//            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
//            int columnCount = resultSetMetaData.getColumnCount();
//
//            while (resultSet.next()) {
//            }
//            return rows;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public void closeConnection() {
        try(Statement statement = conn.createStatement()) {
            String sql = "SHUTDOWN";
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
