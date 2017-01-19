package com.haulmont.utils;

import com.haulmont.datarows.Client;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO extends Dao{

    public ClientDAO() throws ClassNotFoundException, SQLException {
        super();
    }

    @Override
    public void createTable() {
        try(Statement statement = conn.createStatement()) {
            String sql = "CREATE TABLE clients (id BIGINT IDENTITY," +
                    "firstname VARCHAR(255)," +
                    "surname VARCHAR(255)," +
                    "middlename VARCHAR(255)," +
                    "tel VARCHAR(40)," +
                    "CONSTRAINT id_client PRIMARY KEY (id))";
            statement.executeUpdate(sql);
        } catch (SQLException ignored) {
        }
    }

    @Override
    public void addRow(Object object) {
        Client client = (Client) object;
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

    @Override
    public void updateRow(Object object){
        Client client = (Client) object;
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

    @Override
    public boolean deleteRow(Object object) {
        Client client = (Client) object;
        try(Statement statement = conn.createStatement()) {
            String sql = String.format("DELETE FROM clients WHERE id = %d", client.getClientId());
            int countDeletedRows = statement.executeUpdate(sql);                                                //TODO: исправить
            if (countDeletedRows != 0)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public List<Client> getTable() {
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
        return null;
    }
}
