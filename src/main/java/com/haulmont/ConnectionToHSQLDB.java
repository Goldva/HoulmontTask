package com.haulmont;

import com.haulmont.DataFromTable.Client;
import com.haulmont.DataFromTable.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectionToHSQLDB {
    private Connection conn;

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
            String path = "G:/java/houlmont/HoulmontTask/src/main/resources/dataDB/";
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

    public void addRowsToTable(Client client) {
        try(PreparedStatement statement =
                    conn.prepareStatement(
                            "INSERT INTO clients (firstname, surname, middlename, tel) " +
                                    "VALUES (?, ?, ?, ?)")) {
            statement.setString(1, client.getFirstName());
            statement.setString(2, client.getSurName());
            statement.setString(3, client.getMiddleName());
            statement.setString(4, client.getTelephon());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addRowsToTable(Order order) {
        try(PreparedStatement statement =
                    conn.prepareStatement(
                            "INSERT INTO orders (about_order, clients_id, create_date, end_date, price, status) " +
                                    "VALUES (?, ?, ?, ?, ?, ?)")) {
            statement.setString(1, order.getAboutOrder());
            statement.setInt(2, order.getClient().getId());
            statement.setDate(3, order.getCreateDate());
            statement.setDate(4, order.getEndDate());
            statement.setDouble(5, order.getPrice());
            statement.setString(6, order.getStatus());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteTable(String tableName, int id) {
        try(Statement statement = conn.createStatement()) {
            String sql = String.format("DELETE FROM %s WHERE clients_id = %d", tableName, id);        //TODO: Нельзя удалить клиента если есть заказ
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Client> getTableClients() {
        List<Client> clients = new ArrayList<>();
        try (Statement statement = conn.createStatement()) {
            String sql;
            sql = "SELECT * FROM clients";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Client client = new Client(resultSet.getInt("clients_id"));
                client.setFirstName(resultSet.getNString("firstname"));
                client.setSurName(resultSet.getNString("surname"));
                client.setMiddleName(resultSet.getNString("middlename"));
                client.setTelephon(resultSet.getNString("tel"));

                clients.add(client);
            }
            return clients;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;                                            //TODO: Подумать над return
    }

    public List<Order> getTableOrders() {
        List<Order> orders = new ArrayList<>();
        try (Statement statement = conn.createStatement()) {
            String sql;
            sql = "SELECT * FROM orders NATURAL JOIN clients";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Client client = new Client(resultSet.getInt("clients_id"));
                client.setFirstName(resultSet.getNString("firstname"));
                client.setSurName(resultSet.getNString("surname"));
                client.setMiddleName(resultSet.getNString("middlename"));
                client.setTelephon(resultSet.getNString("tel"));


                Order order  = new Order(resultSet.getInt("orders_id"), client, resultSet.getDate("create_date"));
                order.setAboutOrder(resultSet.getString("about_order"));
                order.setEndDate(resultSet.getDate("end_date"));
                order.setPrice(resultSet.getDouble("price"));
                order.setStatus(resultSet.getString("status"));

                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;                                            //TODO: Подумать над return
    }

    public void closeConnection() {
        try(Statement statement = conn.createStatement()) {
            String sql = "SHUTDOWN";
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
