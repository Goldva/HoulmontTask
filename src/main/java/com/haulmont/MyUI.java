package com.haulmont;

import com.haulmont.DataFromTable.Client;
import com.haulmont.DataFromTable.Order;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import javax.servlet.annotation.WebServlet;
import java.sql.SQLException;
import java.util.Collection;


@Theme("mytheme")
public class MyUI extends UI {
    private GridLayout gridLayout;
    private BeanItemContainer<Client> containerClients;
    private BeanItemContainer<Order> containerOrders;
    private ConnectionToHSQLDB connection = new ConnectionToHSQLDB();
    private Grid clientsGrid;

    public MyUI() throws SQLException, ClassNotFoundException {
        gridLayout = new GridLayout(4, 4);
        gridLayout.addStyleName("example-gridlayout");
//        dataProc = new DataProcessing(containerClients, containerOrders);
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        gridLayout.setMargin(true);
        gridLayout.setSpacing(true);
        setContent(gridLayout);

        Grid clientsGrid = createClientsTable();
        Grid ordersGrid = createOrdersTable();

        Button addClientButton = new Button("Добавить");
        addClientButton.addClickListener(clickEvent -> new ClientCardUI(this).addClient());

        Button updateClientButton = new Button("Редактировать");
        updateClientButton.addClickListener(clickEvent -> new ClientCardUI(this).editorClient());

        Button deleteClientButton = new Button("Удалить", e -> {
            for (Object itemId: clientsGrid.getSelectionModel().getSelectedRows()) {
                int id = ((Client) itemId).getClientId();
                connection.deleteRowFromTable("clients", id);
                clientsGrid.getContainerDataSource().removeItem(itemId);
            }
            clientsGrid.getSelectionModel().reset();
        });

        gridLayout.addComponent(addClientButton, 0, 0);
        gridLayout.addComponent(updateClientButton, 1, 0);
        gridLayout.addComponent(deleteClientButton, 2, 0);
    }


    private Grid createClientsTable() {
        Collection<Client> clients = connection.getTableClients();

        containerClients = new BeanItemContainer<>(Client.class, clients);
        clientsGrid = new Grid(containerClients);
        clientsGrid.removeColumn("asArrayObjects");
        clientsGrid.setColumnOrder("clientId", "firstName", "surName", "middleName", "telephone");

        clientsGrid.setImmediate(true);

        clientsGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        clientsGrid.setWidth("750px");
        clientsGrid.setHeight("229px");

        gridLayout.addComponent(clientsGrid, 0, 1, 3, 1);
        return clientsGrid;
    }

    private Grid createOrdersTable() {
        Collection<Order> orders = connection.getTableOrders();

        containerOrders = new BeanItemContainer<>(Order.class, orders);

        Grid ordersGrid = new Grid(containerOrders);
        ordersGrid.removeColumn("client");
        ordersGrid.removeColumn("clientId");
        ordersGrid.removeColumn("middleName");
        ordersGrid.removeColumn("asArrayObjects");
        ordersGrid.setColumnOrder("orderId", "aboutOrder", "firstName", "surName", "telephone",
                "createDate", "endDate", "price", "status");

        ordersGrid.setImmediate(true);

        ordersGrid.setWidth("1050px");
        ordersGrid.setHeight("229px");

        gridLayout.addComponent(ordersGrid, 0, 3, 3, 3);
        return ordersGrid;
    }


    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
