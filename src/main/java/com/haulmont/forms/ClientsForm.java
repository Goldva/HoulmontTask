package com.haulmont.forms;

import com.haulmont.utils.Controller;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.FieldEvents;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

public class ClientsForm {
    private Panel clientsPanel;
    private Controller controller;
    private Grid clientsGrid;

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

        createClientsTable();
        setColumnFiltering();

        Button addClientButton = new Button("Добавить");
        Button updateClientButton = new Button("Редактировать");
        Button deleteClientButton = new Button("Удалить");

        addClientButton.addClickListener(clickEvent -> controller.createAddClientCard(clientsPanel.getUI()));
        updateClientButton.addClickListener(clickEvent -> {
            Object client = clientsGrid.getSelectionModel().getSelectedRows().iterator().next();
            controller.createUpdateClientCard(clientsPanel.getUI(), client);
            clientsGrid.deselectAll();
        });
        deleteClientButton.addClickListener(e -> {
            controller.deleteClients(clientsGrid.getSelectionModel().getSelectedRows());
            clientsGrid.deselectAll();
        });

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


    private void createClientsTable() {
        clientsGrid = new Grid(controller.getContainerClients());
        clientsGrid.removeColumn("asArrayObjects");
        clientsGrid.setColumnOrder("clientId", "firstName", "surName", "middleName", "telephone");

        clientsGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        clientsGrid.getColumn("firstName").setExpandRatio(1);
        clientsGrid.getColumn("surName").setExpandRatio(1);
        clientsGrid.getColumn("middleName").setExpandRatio(1);
        clientsGrid.getColumn("telephone").setExpandRatio(1);
        clientsGrid.setSizeFull();
    }

    private void setColumnFiltering() {
        Grid.HeaderRow filteringHeader = clientsGrid.appendHeaderRow();
        List<Grid.Column> columns =clientsGrid.getColumns();
        for (Grid.Column column : columns){
            TextField filter = getColumnFilter(column.getPropertyId());
            filteringHeader.getCell(column.getPropertyId()).setComponent(filter);
            filteringHeader.getCell(column.getPropertyId()).setStyleName("filterGrid-header");
        }
    }

    private TextField getColumnFilter(final Object columnId) {
        TextField filter = new TextField();
        filter.setWidth("100%");
        filter.addStyleName(ValoTheme.TEXTFIELD_TINY);
        filter.setInputPrompt("Filter");

        filter.addTextChangeListener(new FieldEvents.TextChangeListener() {
            SimpleStringFilter filterText = null;

            @Override
            public void textChange(FieldEvents.TextChangeEvent textChangeEvent) {
                filterText = controller.filterGrid(clientsGrid, filterText, columnId, textChangeEvent);
            }
        });
        return filter;
    }

}
