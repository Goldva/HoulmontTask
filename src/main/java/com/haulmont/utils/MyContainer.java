package com.haulmont.utils;

import com.haulmont.datarows.Client;
import com.haulmont.datarows.Order;
import com.vaadin.data.util.BeanItemContainer;

import java.util.Collection;

public class MyContainer {
    private ConnectionToHSQLDB connection;
    private BeanItemContainer<Client> containerClients;
    private BeanItemContainer<Order> containerOrders;

    public MyContainer(ConnectionToHSQLDB connection) {
        this.connection = connection;
        containerClients = new BeanItemContainer<>(Client.class, this.connection.getTableClients());
        containerOrders = new BeanItemContainer<>(Order.class, this.connection.getTableOrders());
    }

    public void addClient(Client client){
        connection.addRowsToTable(client);
        containerClients.removeAllItems();
        containerClients.addAll(connection.getTableClients());
    }

    public void updateClient(Client client){
        connection.updateTheTable(client);
        containerClients.removeAllItems();
        containerClients.addAll(connection.getTableClients());
    }
    public void deleteClients(Collection<Object> deleteRows){
        for (Object itemId: deleteRows) {
            int id = ((Client) itemId).getClientId();
            connection.deleteRowFromTable("clients", id);
            containerClients.removeItem(itemId);
        }
    }

    public void deleteOrders(Collection<Object> deleteRows){
        for (Object itemId: deleteRows) {
            int id = ((Order) itemId).getOrderId();
            connection.deleteRowFromTable("orders", id);
            containerOrders.removeItem(itemId);
        }

    }

    public BeanItemContainer<Client> getContainerClients() {
        return containerClients;
    }

    public BeanItemContainer<Order> getContainerOrders() {
        return containerOrders;
    }
}
