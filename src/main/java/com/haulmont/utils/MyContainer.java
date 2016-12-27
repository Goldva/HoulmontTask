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
        connection.closeConnection();

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
            if(connection.deleteRowFromTable("clients", id))
                containerClients.removeItem(itemId);
        }
    }

    public void addOrder(Order order){
        connection.addRowsToTable(order);
        containerOrders.removeAllItems();
        containerOrders.addAll(connection.getTableOrders());
    }

    public void updateOrder(Order order) {
        connection.updateTheTable(order);
        containerOrders.removeAllItems();
        containerOrders.addAll(connection.getTableOrders());
    }

    public void deleteOrders(Collection<Object> deleteRows){
        for (Object itemId: deleteRows) {
            int id = ((Order) itemId).getOrderId();
            if (connection.deleteRowFromTable("orders", id))
                containerOrders.removeItem(itemId);
        }
    }

    public void setFilteredCollectionOrders(Collection<Order> orders){
        containerOrders.removeAllItems();
        containerOrders.addAll(orders);
    }

    public BeanItemContainer<Client> getContainerClients() {
        return containerClients;
    }

    public BeanItemContainer<Order> getContainerOrders() {
        return containerOrders;
    }

    public Collection<Client> getListClients(){
        return connection.getTableClients();
    }

    public Collection<Order> getListOrders(){
        return connection.getTableOrders();
    }
}
