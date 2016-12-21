package com.haulmont.forms;

import com.haulmont.datarows.Client;
import com.haulmont.utils.MyContainer;
import com.vaadin.ui.*;

public class ClientsForm {
    private Panel clientsPanel;
    private MyContainer container;                              //TODO: Delete

    public ClientsForm(Panel clientsPanel, MyContainer container) {
        this.clientsPanel = clientsPanel;
        this.container = container;
    }

    public void getClientForm() {
        GridLayout gridLayout = new GridLayout(4, 2);
        gridLayout.addStyleName("example-gridlayout");
        gridLayout.setMargin(true);
        gridLayout.setSpacing(true);

        clientsPanel.setContent(gridLayout);


        Grid clientsGrid = createClientsTable();

        Button addClientButton = new Button("Добавить");
        addClientButton.addClickListener(clickEvent -> {
            ClientCard cardUI = new ClientCard(clientsPanel.getUI());
            cardUI.addClient(container);
        });

        Button updateClientButton = new Button("Редактировать");
        updateClientButton.addClickListener(clickEvent -> {
            Client client = (Client) clientsGrid.getSelectionModel().getSelectedRows().iterator().next();
            ClientCard cardUI = new ClientCard(clientsPanel.getUI());
            cardUI.editorClient(container, client);
        });
        updateClientButton.setEnabled(false);

        Button deleteClientButton = new Button("Удалить", e -> {
            container.deleteClients(clientsGrid.getSelectionModel().getSelectedRows());
            clientsGrid.getSelectionModel().reset();
        });
        deleteClientButton.setEnabled(false);

        clientsGrid.addSelectionListener(e -> {
            int countRowsSelect = clientsGrid.getSelectionModel().getSelectedRows().size();
            if (countRowsSelect == 1) {
                updateClientButton.setEnabled(true);
            } else {
                updateClientButton.setEnabled(false);
            }
            if (countRowsSelect > 0) {
                deleteClientButton.setEnabled(true);
            } else {
                deleteClientButton.setEnabled(false);
            }
        });

        gridLayout.setSizeFull();
        gridLayout.setColumnExpandRatio(3,2);
        gridLayout.addComponent(addClientButton, 0, 0);
        gridLayout.addComponent(updateClientButton, 1, 0);
        gridLayout.addComponent(deleteClientButton, 2, 0);
        gridLayout.addComponent(clientsGrid, 0, 1, 3, 1);
    }


    private Grid createClientsTable() {
        Grid clientsGrid = new Grid(container.getContainerClients());
        clientsGrid.removeColumn("asArrayObjects");
        clientsGrid.setColumnOrder("clientId", "firstName", "surName", "middleName", "telephone");

        clientsGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        clientsGrid.getColumn("firstName").setExpandRatio(1);
        clientsGrid.getColumn("surName").setExpandRatio(1);
        clientsGrid.getColumn("middleName").setExpandRatio(1);
        clientsGrid.getColumn("telephone").setExpandRatio(1);
        clientsGrid.setSizeFull();
        return clientsGrid;
    }
}
