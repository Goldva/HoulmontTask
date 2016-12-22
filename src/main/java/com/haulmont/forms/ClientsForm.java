package com.haulmont.forms;

import com.haulmont.utils.Controller;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Panel;

public class ClientsForm {
    private Panel clientsPanel;
    private Controller controller;

    public ClientsForm(Panel clientsPanel) {
        this.clientsPanel = clientsPanel;
        controller = Controller.getInstance();
        createForm();
    }

    public void createForm() {
        GridLayout gridLayout = new GridLayout(4, 2);
        gridLayout.addStyleName("example-gridlayout");
        gridLayout.setMargin(true);
        gridLayout.setSpacing(true);

        clientsPanel.setContent(gridLayout);

        Grid clientsGrid = createClientsTable();

        Button addClientButton = new Button("Добавить");
        Button updateClientButton = new Button("Редактировать");
        Button deleteClientButton = new Button("Удалить");

        addClientButton.addClickListener(clickEvent -> controller.createAddClientCard(clientsPanel.getUI()));
        updateClientButton.addClickListener(clickEvent -> {
            Object client = clientsGrid.getSelectionModel().getSelectedRows().iterator().next();
            controller.createUpdateClientCard(clientsPanel.getUI(), client);
        });
        deleteClientButton.addClickListener(e ->
                controller.deleteClients(clientsGrid.getSelectionModel().getSelectedRows()));

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

        updateClientButton.setEnabled(false);
        deleteClientButton.setEnabled(false);

        gridLayout.setSizeFull();
        gridLayout.setColumnExpandRatio(3,2);
        gridLayout.addComponent(addClientButton, 0, 0);
        gridLayout.addComponent(updateClientButton, 1, 0);
        gridLayout.addComponent(deleteClientButton, 2, 0);
        gridLayout.addComponent(clientsGrid, 0, 1, 3, 1);
    }


    private Grid createClientsTable() {
        Grid clientsGrid = new Grid(controller.getContainerClients());
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

//    private void setColumnFiltering(boolean filtered) {
//        if (filtered && filteringHeader == null) {
//            filteringHeader = sample.appendHeaderRow();
//
//            // Add new TextFields to each column which filters the data from
//            // that column
//            String columnId = ExampleUtil.BUDGET_ITEM_NAME_PROPERTY_ID
//                    .toString();
//            TextField filter = getColumnFilter(columnId);
//            filteringHeader.getCell(columnId).setComponent(filter);
//            filteringHeader.getCell(columnId).setStyleName("filter-header");
//        } else if (!filtered && filteringHeader != null) {
//            sample.removeHeaderRow(filteringHeader);
//            filteringHeader = null;
//        }
//    }

}
