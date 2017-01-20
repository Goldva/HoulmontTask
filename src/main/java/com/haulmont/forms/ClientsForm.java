package com.haulmont.forms;

import com.haulmont.forms.mvc.ClientController;
import com.haulmont.forms.mvc.ViewsControl;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Panel;

public class ClientsForm implements Form {
    private Panel clientsPanel;
    private ClientController controller;
    private ViewsControl viewsControl;
    Grid clientsGrid;
    Button addClientButton;
    Button updateClientButton;
    Button deleteClientButton;

    public ClientsForm(Panel clientsPanel, ClientController controller) {
        this.clientsPanel = clientsPanel;
        this.controller = controller;
        this.viewsControl = new ViewsControl();
        this.addClientButton = new Button("Добавить");
        this.updateClientButton = new Button("Редактировать");
        this.deleteClientButton = new Button("Удалить");
        createForm();
    }

    private void createForm() {
        GridLayout gridLayout = new GridLayout(4, 2);
        gridLayout.addStyleName("example-gridlayout");
        gridLayout.setMargin(true);
        gridLayout.setSpacing(true);

        clientsPanel.setContent(gridLayout);

        createClientsTable();
        createListener();

        updateClientButton.setEnabled(false);
        deleteClientButton.setEnabled(false);

        gridLayout.setSizeFull();
        gridLayout.setColumnExpandRatio(3, 2);
        gridLayout.addComponent(addClientButton, 0, 0);
        gridLayout.addComponent(updateClientButton, 1, 0);
        gridLayout.addComponent(deleteClientButton, 2, 0);
        gridLayout.addComponent(clientsGrid, 0, 1, 3, 1);
    }

    private void createListener() {
        addClientButton.addClickListener(clickEvent -> controller.createAddCard());
        updateClientButton.addClickListener(clickEvent -> {
            Object client = clientsGrid.getSelectionModel().getSelectedRows().iterator().next();
            controller.createUpdateCard(client);
            clientsGrid.deselectAll();
        });
        deleteClientButton.addClickListener(e -> {
            controller.deleteRows(clientsGrid.getSelectionModel().getSelectedRows());
            clientsGrid.deselectAll();
        });
        clientsGrid.addSelectionListener(e -> {
            int countRowsSelect = clientsGrid.getSelectionModel().getSelectedRows().size();
            viewsControl.formButtonEnable(this, countRowsSelect);
        });

    }

    private void createClientsTable() {
        clientsGrid = new Grid(controller.getContainer());
        clientsGrid.setColumnOrder("clientId", "surName", "firstName", "middleName", "telephone");

        clientsGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        clientsGrid.getColumn("firstName").setExpandRatio(1);
        clientsGrid.getColumn("surName").setExpandRatio(1);
        clientsGrid.getColumn("middleName").setExpandRatio(1);
        clientsGrid.getColumn("telephone").setExpandRatio(1);
        clientsGrid.setSizeFull();
    }

    @Override
    public void enableUpdateButton() {
        updateClientButton.setEnabled(true);
    }

    @Override
    public void disableUpdateButton() {
        updateClientButton.setEnabled(false);
    }

    @Override
    public void enableDeleteButton() {
        deleteClientButton.setEnabled(true);
    }

    @Override
    public void disableDeleteButton() {
        deleteClientButton.setEnabled(false);
    }

}
