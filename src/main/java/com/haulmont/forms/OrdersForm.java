package com.haulmont.forms;

import com.haulmont.forms.mvc.OrderController;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.FieldEvents;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

public class OrdersForm {
    Panel ordersPanel;
    OrderController controller;
    Grid ordersGrid;
    TextField aboutOrderFilter;
    TextField clientNamesFilter;
    TextField statusFilter;


    public OrdersForm(Panel clientsPanel, OrderController orderController) {
        this.ordersPanel = clientsPanel;
        controller = orderController;
        createOrderForm();
    }

    public void createOrderForm() {
        GridLayout gridLayout = new GridLayout(4, 4);
        gridLayout.addStyleName("example-gridlayout");
        gridLayout.setMargin(true);
        gridLayout.setSpacing(true);

        ordersPanel.setContent(gridLayout);

        createOrdersTable();
        setColumnFiltering();

        Button addOrderButton = new Button("Добавить");
        Button updateOrderButton = new Button("Редактировать");
        Button deleteOrderButton = new Button("Удалить");
        Button applyFilter = new Button("Применить");

        aboutOrderFilter = new TextField("About order");
        clientNamesFilter = new TextField("Client name");
        statusFilter = new TextField("Status");


        addOrderButton.addClickListener(clickEvent -> controller.createAddCard());
        updateOrderButton.addClickListener(clickEvent -> {
            Object order = ordersGrid.getSelectionModel().getSelectedRows().iterator().next();
            controller.createUpdateCard(order);
            ordersGrid.deselectAll();
        });
        deleteOrderButton.addClickListener(e -> {
            controller.deleteRows(ordersGrid.getSelectionModel().getSelectedRows());
            ordersGrid.deselectAll();
        });

        applyFilter.addClickListener(clickEvent -> {
            String aboutOrder = aboutOrderFilter.getValue();
            String clientName = clientNamesFilter.getValue();
            String sttus = statusFilter.getValue();
            controller.filtering(aboutOrder, clientName, sttus);
        });

        ordersGrid.addSelectionListener(e -> {
            int countRowsSelect = ordersGrid.getSelectionModel().getSelectedRows().size();
            if (countRowsSelect == 1) {
                updateOrderButton.setEnabled(true);
            } else {
                updateOrderButton.setEnabled(false);
            }
            if (countRowsSelect > 0) {
                deleteOrderButton.setEnabled(true);
            } else {
                deleteOrderButton.setEnabled(false);
            }
        });

        updateOrderButton.setEnabled(false);
        deleteOrderButton.setEnabled(false);

        gridLayout.setSizeFull();
        gridLayout.setColumnExpandRatio(3, 2);
        gridLayout.addComponent(addOrderButton, 0, 0);
        gridLayout.addComponent(updateOrderButton, 1, 0);
        gridLayout.addComponent(deleteOrderButton, 2, 0);
        gridLayout.addComponent(ordersGrid, 0, 1, 3, 1);
        gridLayout.addComponent(aboutOrderFilter, 0, 2);
        gridLayout.addComponent(clientNamesFilter, 1, 2);
        gridLayout.addComponent(statusFilter, 2, 2);
        gridLayout.addComponent(applyFilter, 0, 3);
    }


    private void createOrdersTable() {
        ordersGrid = new Grid(controller.getContainer());
        ordersGrid.removeColumn("client");
        ordersGrid.removeColumn("asArrayObjects");
        ordersGrid.removeColumn("millisecondCreateDate");
        ordersGrid.removeColumn("millisecondEndDate");
        ordersGrid.setColumnOrder("orderId", "aboutOrder", "clientName", "telephone",
                "createDate", "endDate", "price", "status");

        ordersGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        ordersGrid.setImmediate(true);
        ordersGrid.setSizeFull();

    }

    private void setColumnFiltering() {
        Grid.HeaderRow filteringHeader = ordersGrid.appendHeaderRow();

        List<Grid.Column> columns =ordersGrid.getColumns();
        for (Grid.Column column : columns){
            String columnName = column.getHeaderCaption().toLowerCase();
            if (columnName.equals("about order") || columnName.equals("client name") || columnName.equals("status")) {
                TextField filter = getColumnFilter(column.getPropertyId());
                filteringHeader.getCell(column.getPropertyId()).setComponent(filter);
                filteringHeader.getCell(column.getPropertyId()).setStyleName("filterGrid-header");
            }
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
                filterText = controller.filterGrid(ordersGrid, filterText, columnId, textChangeEvent);
            }
        });
        return filter;
    }
}
