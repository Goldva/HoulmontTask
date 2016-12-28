package com.haulmont.utils;

import com.haulmont.datarows.Client;
import com.haulmont.datarows.Order;

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
//            String path = "G:/java/houlmont/HoulmontTask/src/main/resources/dataDB/";
            String path = "D:/Java/Houlmont/HoulmontTask/src/main/resources/dataDB/";
//            String path = "./src/main/resources/dataDB/";
            String dbname = "mydb";
            String connectionString = "jdbc:hsqldb:file:" + path + dbname;
            String login = "joe";
            String password = "password";
//            conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb", "SA", "");
            conn = DriverManager.getConnection(connectionString, login, password);
        } catch (SQLException e) {
            System.out.println("Соединение не создано");
            e.printStackTrace();
            throw new SQLException();
        }
    }

    public void createTable() {
        try(Statement statement = conn.createStatement()) {
            String sql = "CREATE TABLE clients (id BIGINT IDENTITY," +
                    "firstname VARCHAR(255)," +
                    "surname VARCHAR(255)," +
                    "middlename VARCHAR(255)," +
                    "tel VARCHAR(40))," +
                    "CONSTRAINT id_client PRIMARY KEY (id)";
            statement.executeUpdate(sql);
            sql = "CREATE TABLE orders (id BIGINT IDENTITY," +
                    "about_order VARCHAR(255)," +
                    "clients_id BIGINT," +
                    "create_date DATE," +
                    "end_date DATE," +
                    "price DECIMAL (64, 2)," +
                    "status VARCHAR(255)," +
                    "CONSTRAINT id_order PRIMARY KEY (id)," +
                    "CONSTRAINT id_clients FOREIGN KEY (clients_id) REFERENCES clients(id))";
            statement.executeUpdate(sql);
            sql = "INSERT INTO clients (firstname, surname, middlename, tel) VALUES ('Петров', 'Петр', 'Петрович', 86132255213)\n";
            statement.executeUpdate(sql);
            sql = "INSERT INTO clients (firstname, surname, middlename, tel) VALUES ('Иванов', 'Иван', 'Иваныч', 5578668763)\n";
            statement.executeUpdate(sql);
            sql = "INSERT INTO clients (firstname, surname, middlename, tel) VALUES ('Соколов', 'Федор', 'Петрович', 557453664)\n";
            statement.executeUpdate(sql);
            sql = "INSERT INTO clients (firstname, surname, middlename, tel) VALUES ('Хлебушкин', 'Хлебец', 'Хлебович', 777896321)\n";
            statement.executeUpdate(sql);
            sql = "INSERT INTO clients (firstname, surname, middlename, tel) VALUES ('Люк', 'яТвой', 'Отец', 68777985132)\n";
            statement.executeUpdate(sql);
            sql = "INSERT INTO orders (about_order, clients_id, create_date, end_date, price, status) VALUES ('Ремонт КП', 2, '2009-01-10', '2010-12-04', 6000.00, 'Запланирован')\n";
            statement.executeUpdate(sql);
            sql = "INSERT INTO orders (about_order, clients_id, create_date, end_date, price, status) VALUES ('Смена масла', 4, '2009-01-10', '2010-12-04', 2000.00, 'Выполнен')\n";
            statement.executeUpdate(sql);
            sql = "INSERT INTO orders (about_order, clients_id, create_date, end_date, price, status) VALUES ('Чистка гиперпрыжкового двягателя модели \"Звезда смерти\"', 5, '2070-01-10', '2075-12-04', 1000000.00, 'Запланирован')\n";
            statement.executeUpdate(sql);
            sql = "INSERT INTO orders (about_order, clients_id, create_date, end_date, price, status) VALUES ('Ремонт тостера', 4, '2012-01-04', '2012-12-05', 500.00, 'Запланирован')\n";
//            sql = "ALTER TABLE orders ADD FOREIGN KEY (clients_id) REFERENCES clients(id)";
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
            statement.setString(4, client.getTelephone());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTheTable(Client client){
        try(PreparedStatement statement =
                    conn.prepareStatement(
                            "UPDATE clients " +
                                    "SET firstname = ?, surname = ?, middlename = ?, tel = ?" +
                                    "WHERE id = ?")) {
            statement.setString(1, client.getFirstName());
            statement.setString(2, client.getSurName());
            statement.setString(3, client.getMiddleName());
            statement.setString(4, client.getTelephone());
            statement.setLong(5, client.getClientId());
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

    public void updateTheTable(Order order) {
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

    public boolean deleteRowFromTable(String tableName, long id) {
        try(Statement statement = conn.createStatement()) {
            String sql = String.format("DELETE FROM %s WHERE id = %d", tableName, id);                                       //TODO: ������ ������� ������� ���� ���� �����
            int countDeletedRows = statement.executeUpdate(sql);
            if (countDeletedRows != 0)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public List<Client> getTableClients() {
        List<Client> clients = new ArrayList<>();
        try (Statement statement = conn.createStatement()) {
            String sql;
            sql = "SELECT * FROM clients";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Client client = new Client(resultSet.getInt("id"));
                client.setFirstName(resultSet.getString("firstname"));
                client.setSurName(resultSet.getString("surname"));
                client.setMiddleName(resultSet.getString("middlename"));
                client.setTelephone(resultSet.getString("tel"));

                clients.add(client);
            }
            return clients;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;                                                                                                        //TODO: �������� ��� return
    }

    public List<Order> getTableOrders() {
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


                Order order  = new Order(resultSet.getInt("id"), client, resultSet.getDate("create_date"));
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
        return null;                                                                                                        //TODO: �������� ��� return
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