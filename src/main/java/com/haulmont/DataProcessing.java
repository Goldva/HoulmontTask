package com.haulmont;

import com.haulmont.DataFromTable.Client;
import com.haulmont.DataFromTable.Order;
import com.vaadin.data.Container;
import com.vaadin.data.Item;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Aleksei on 18.12.2016.
 */
public class DataProcessing {
    private Container containerClients;
    private Container containerOrders;
    private ConnectionToHSQLDB connection;

    public DataProcessing(Container containerClients, Container containerOrders) throws SQLException, ClassNotFoundException {
        connection = new ConnectionToHSQLDB();
        this.containerClients = containerClients;
        this.containerOrders = containerOrders;
    }

    public void addDataToTableClients() {
        List<Client> rows = connection.getTableClients();
        for (Client row : rows) {
            Object itemId = containerClients.addItem();
            Item item = containerClients.getItem(itemId);
            item.getItemProperty("ID").setValue(row.getId());
            item.getItemProperty("First name").setValue(row.getFirstName());
            item.getItemProperty("Surname").setValue(row.getSurName());
            item.getItemProperty("Middle name").setValue(row.getMiddleName());
            item.getItemProperty("Telephone").setValue(row.getTelephon());
        }
    }

    public void addDataToTableOrders() {
        List<Order> rows = connection.getTableOrders();
        for (Order row : rows) {
            Object itemId = containerOrders.addItem();
            Item item = containerOrders.getItem(itemId);
            item.getItemProperty("ID").setValue(row.getOrderId());
            item.getItemProperty("About order").setValue(row.getAboutOrder());
            item.getItemProperty("First name").setValue(row.getClient().getFirstName());
            item.getItemProperty("Surname").setValue(row.getClient().getSurName());
            item.getItemProperty("Telephone").setValue(row.getClient().getTelephon());
            item.getItemProperty("Create date").setValue(row.getCreateDate());
            item.getItemProperty("End date").setValue(row.getEndDate());
            item.getItemProperty("Price").setValue(row.getPrice());
            item.getItemProperty("Status").setValue(row.getStatus());
        }

    }

    public void addClient() {

    }

    public void updateClient() {

    }

    public void deleteClient(Item text) {
        int id = (Integer) text.getItemProperty("ID").getValue();
        connection.deleteTable("clients", id);
//        tableClients.clear();
//        addDataToTableOrders(connection.getTableClients(), tableClients);

    }


}