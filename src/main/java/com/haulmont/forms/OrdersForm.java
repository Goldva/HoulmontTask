package com.haulmont.forms;

import com.haulmont.utils.Controller;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Panel;

public class OrdersForm {
    private Panel ordersPanel;
    Controller controller;

    public OrdersForm(Panel ordersPanel) {
        this.ordersPanel = ordersPanel;
        controller = Controller.getInstance();
        createOrderForm();
    }

    public void createOrderForm() {
        GridLayout gridLayout = new GridLayout(4, 2);
        gridLayout.addStyleName("example-gridlayout");
        gridLayout.setMargin(true);
        gridLayout.setSpacing(true);

        ordersPanel.setContent(gridLayout);

        Grid ordersGrid = createOrdersTable();

        Button addOrderButton = new Button("Добавить");
        Button updateOrderButton = new Button("Редактировать");
        Button deleteOrderButton = new Button("Удалить");

        addOrderButton.addClickListener(clickEvent -> controller.createAddOrderCard(ordersPanel.getUI()));
        updateOrderButton.addClickListener(clickEvent -> {
            Object order = ordersGrid.getSelectionModel().getSelectedRows().iterator().next();
            controller.createUpdateOrderCard(ordersPanel.getUI(), order);
        });
        deleteOrderButton.addClickListener(e ->
                controller.deleteOrders(ordersGrid.getSelectionModel().getSelectedRows()));



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
        gridLayout.setColumnExpandRatio(3,2);
        gridLayout.addComponent(addOrderButton, 0, 0);
        gridLayout.addComponent(updateOrderButton, 1, 0);
        gridLayout.addComponent(deleteOrderButton, 2, 0);
        gridLayout.addComponent(ordersGrid, 0, 1, 3, 1);
    }


    private Grid createOrdersTable() {
        Grid ordersGrid = new Grid(controller.getContainerOrders());
        ordersGrid.removeColumn("client");
        ordersGrid.removeColumn("asArrayObjects");
        ordersGrid.removeColumn("millisecondCreateDate");
        ordersGrid.removeColumn("millisecondEndDate");
        ordersGrid.setColumnOrder("orderId", "aboutOrder", "clientName", "telephone",
                "createDate", "endDate", "price", "status");

        ordersGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        ordersGrid.setImmediate(true);
        ordersGrid.setSizeFull();

        return ordersGrid;
    }

}
