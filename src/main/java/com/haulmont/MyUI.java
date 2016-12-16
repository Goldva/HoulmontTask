package com.haulmont;

import javax.servlet.annotation.WebServlet;

import com.haulmont.DataFromTable.Client;
import com.haulmont.DataFromTable.Order;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Item;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;


@Theme("mytheme")
public class MyUI extends UI  {
    private ConnectionToHSQLDB connection;
    private VerticalLayout layout;

    public MyUI() throws SQLException, ClassNotFoundException {
        this.connection = new ConnectionToHSQLDB();
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);
//        UI.getCurrent().setPollInterval(1000);
        getClientsTable();
        getOrdersTable();
    }

    private void getClientsTable() {
        Table tableClients = new Table();
        tableClients.setSelectable(true);
        tableClients.setImmediate(true);

        tableClients.addContainerProperty("ID", Integer.class, null);
        tableClients.addContainerProperty("First name", String.class, null);
        tableClients.addContainerProperty("Surname", String.class, null);
        tableClients.addContainerProperty("Middle name", String.class, null);
        tableClients.addContainerProperty("Telephone", String.class, null);

        tableClients.setWidth("750");
        tableClients.setHeight("229");
        layout.addComponents(tableClients);

//        new Thread(() -> {
//            while (true) {
//                getSession().lock();
//        tableClients.removeAllItems();

        List<Client> clients = connection.getTableClients();
        Object newItemId;
        for (Client client : clients) {
            newItemId = tableClients.addItem();
            Item row = tableClients.getItem(newItemId);
            row.getItemProperty("ID").setValue(client.getId());
            row.getItemProperty("First name").setValue(client.getFirstName());
            row.getItemProperty("Surname").setValue(client.getSurName());
            row.getItemProperty("Middle name").setValue(client.getMiddleName());
            row.getItemProperty("Telephone").setValue(client.getTelephon());


        }

//                getSession().unlock();
//                try {
//                    Thread.sleep(1000);
//                } catch (final InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }

    private void getOrdersTable() {
        Table tableOrders = new Table();
        tableOrders.setSelectable(true);
        tableOrders.setImmediate(true);

        tableOrders.addContainerProperty("ID", Integer.class, null);
        tableOrders.addContainerProperty("About order", String.class, null);
        tableOrders.addContainerProperty("First name", String.class, null);
        tableOrders.addContainerProperty("Surname", String.class, null);
        tableOrders.addContainerProperty("Telephone", String.class, null);
        tableOrders.addContainerProperty("Create date", Date.class, null);
        tableOrders.addContainerProperty("End date", Date.class, null);
        tableOrders.addContainerProperty("Price", Double.class, null);
        tableOrders.addContainerProperty("Status", String.class, null);

        tableOrders.setHeight("229");
        layout.addComponents(tableOrders);
        List<Order> orders = connection.getTableOrders();
        Object newItemId;
        for (Order order : orders) {
            newItemId = tableOrders.addItem();
            Item row = tableOrders.getItem(newItemId);
            row.getItemProperty("ID").setValue(order.getOrderId());
            row.getItemProperty("About order").setValue(order.getAboutOrder());
            row.getItemProperty("First name").setValue(order.getClient().getFirstName());
            row.getItemProperty("Surname").setValue(order.getClient().getSurName());
            row.getItemProperty("Telephone").setValue(order.getClient().getTelephon());
            row.getItemProperty("Create date").setValue(order.getCreateDate());
            row.getItemProperty("End date").setValue(order.getEndDate());
            row.getItemProperty("Price").setValue(order.getPrice());
            row.getItemProperty("Status").setValue(order.getStatus());

        }
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
