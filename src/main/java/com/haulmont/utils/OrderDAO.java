package com.haulmont.utils;

import com.haulmont.datarows.Client;
import com.haulmont.datarows.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends Dao {
    public OrderDAO() throws ClassNotFoundException, SQLException {
        super();
    }

    @Override
    public void createTable() {
        try (Statement statement = conn.createStatement()) {
            String sql = "CREATE TABLE orders (id BIGINT IDENTITY," +
                    "about_order VARCHAR(255)," +
                    "clients_id BIGINT," +
                    "create_date DATE," +
                    "end_date DATE," +
                    "price DECIMAL (64, 2)," +
                    "status VARCHAR(255)," +
                    "CONSTRAINT id_order PRIMARY KEY (id)," +
                    "FOREIGN KEY (clients_id) REFERENCES clients(id))";
            statement.executeUpdate(sql);
        } catch (SQLException ignored) {
        }
    }

    @Override
    public void addRow(Object object) {
        Order order = (Order) object;
        try (PreparedStatement statement =
                     conn.prepareStatement(
                             "INSERT INTO orders (about_order, clients_id, create_date, end_date, price, status) " +
                                     "VALUES (?, ?, ?, ?, ?, ?)")) {
            statement.setString(1, order.getAboutOrder());
            statement.setLong(2, order.getClient().getClientId());
            statement.setDate(3, new Date(order.getMillisecondCreateDate()));
            statement.setDate(4, new Date(order.getMillisecondEndDate()));
            statement.setDouble(5, order.getPrice());
            statement.setString(6, order.getStatus());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateRow(Object object) {
        Order order = (Order) object;
        try (PreparedStatement statement =
                     conn.prepareStatement(
                             "UPDATE orders " +
                                     "SET about_order = ?, end_date = ?, price = ?, status = ?" +
                                     "WHERE id = ?")) {
            statement.setString(1, order.getAboutOrder());
            statement.setDate(2, new Date(order.getMillisecondEndDate()));
            statement.setDouble(3, order.getPrice());
            statement.setString(4, order.getStatus());
            statement.setLong(5, order.getOrderId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteRow(Object object) {
        Order order = (Order) object;
        try (Statement statement = conn.createStatement()) {
            String sql = String.format("DELETE FROM orders WHERE id = %d", order.getOrderId());
            int countDeletedRows = statement.executeUpdate(sql);                                                //TODO: исправить
            if (countDeletedRows != 0)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Order> getTable() {
        List<Order> orders = new ArrayList<>();
        try (Statement statement = conn.createStatement()) {
            String sql;
            sql = "SELECT * FROM orders INNER JOIN clients ON orders.clients_id=clients.id";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Client client = new Client(resultSet.getInt("clients_id"));
                client.setFirstName(resultSet.getNString("firstname"));
                client.setSurName(resultSet.getNString("surname"));
                client.setMiddleName(resultSet.getNString("middlename"));
                client.setTelephone(resultSet.getNString("tel"));


                Order order = new Order(resultSet.getInt("id"), client, resultSet.getDate("create_date"));
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
        return null;
    }
}
