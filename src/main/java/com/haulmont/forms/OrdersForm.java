package com.haulmont.forms;

import com.haulmont.datarows.Order;
import com.haulmont.utils.MyContainer;
import com.vaadin.ui.*;

public class OrdersForm {
    private Panel ordersPanel;
    private MyContainer container;

    public OrdersForm(Panel ordersPanel, MyContainer container) {
        this.ordersPanel = ordersPanel;
        this.container = container;
    }

    public void getOrderForm() {
        GridLayout gridLayout = new GridLayout(4, 2);
        gridLayout.addStyleName("example-gridlayout");
        gridLayout.setMargin(true);
        gridLayout.setSpacing(true);

        ordersPanel.setContent(gridLayout);

        Grid ordersGrid = createOrdersTable();

        Button addOrderButton = new Button("Добавить");
        addOrderButton.addClickListener(clickEvent -> new OrderCard(ordersPanel.getUI()).addOrder(container));

        Button updateOrderButton = new Button("Редактировать");
        updateOrderButton.addClickListener(clickEvent -> {
            Order order = (Order) ordersGrid.getSelectionModel().getSelectedRows().iterator().next();
            new OrderCard(ordersPanel.getUI()).editorOrder(container, order);
        });
        updateOrderButton.setEnabled(false);

        Button deleteOrderButton = new Button("Удалить", e -> {
            container.deleteOrders(ordersGrid.getSelectionModel().getSelectedRows());
            ordersGrid.getSelectionModel().reset();
        });
        deleteOrderButton.setEnabled(false);

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


        gridLayout.setSizeFull();
        gridLayout.setColumnExpandRatio(3,2);
        gridLayout.addComponent(addOrderButton, 0, 0);
        gridLayout.addComponent(updateOrderButton, 1, 0);
        gridLayout.addComponent(deleteOrderButton, 2, 0);
        gridLayout.addComponent(ordersGrid, 0, 1, 3, 1);
    }


    private Grid createOrdersTable() {
        Grid ordersGrid = new Grid(container.getContainerOrders());
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
