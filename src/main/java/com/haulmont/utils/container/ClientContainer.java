package com.haulmont.utils.container;

import com.haulmont.datarows.Client;
import com.haulmont.utils.dao.ClientDAO;
import com.vaadin.data.util.BeanItemContainer;

import java.util.Collection;

public class ClientContainer {
    private ClientDAO connection;
    private BeanItemContainer<Client> containerClients;

    public ClientContainer(ClientDAO connection) {
        this.connection = connection;
        containerClients = new BeanItemContainer<>(Client.class);
    }

    public void addClient(Client client) {
        connection.addRow(client);
        refresh();
    }

    public void updateClient(Client client) {
        connection.updateRow(client);
        refresh();
    }

    public void deleteClients(Collection<Object> deleteRows) {
        deleteRows.forEach(connection::deleteRow);
        refresh();
    }

    public BeanItemContainer<Client> getContainerClients() {
        refresh();
        return containerClients;
    }

    private void refresh() {
        containerClients.removeAllItems();
        containerClients.addAll(connection.getTable());
    }

    public Collection<Client> getListClients() {
        return connection.getTable();
    }
}
