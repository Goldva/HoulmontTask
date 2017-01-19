package com.haulmont.utils;

import com.haulmont.datarows.Client;
import com.vaadin.data.util.BeanItemContainer;

import java.util.Collection;

public class ClientContainer {
    private ClientDAO connection;
    private BeanItemContainer<Client> containerClients;

    public ClientContainer(ClientDAO connection) {
        this.connection = connection;
        containerClients = new BeanItemContainer<>(Client.class, this.connection.getTable());                   //TODO: подумать
    }

    public void addClient(Client client) {
        connection.addRow(client);
        containerClients.removeAllItems();
        containerClients.addAll(connection.getTable());
    }

    public void updateClient(Client client) {
        connection.updateRow(client);
        containerClients.removeAllItems();
        containerClients.addAll(connection.getTable());
    }

    public void deleteClients(Collection<Object> deleteRows) {
        for (Object item : deleteRows) {
            if (connection.deleteRow(item))
                containerClients.removeItem(item);
        }
    }

    public BeanItemContainer<Client> getContainerClients() {
        return containerClients;
    }

    public Collection<Client> getListClients() {
        return connection.getTable();
    }
}
