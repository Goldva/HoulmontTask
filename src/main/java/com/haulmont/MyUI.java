package com.haulmont;

import javax.servlet.annotation.WebServlet;

import com.haulmont.DataFromTable.DataTable;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Property;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;


@Theme("mytheme")
public class MyUI extends UI {
    private ConnectionToHSQLDB connection;
    private GridLayout gridLayout;
    private Table tableClients;


    public MyUI() throws SQLException, ClassNotFoundException {
        this.connection = new ConnectionToHSQLDB();
        gridLayout = new GridLayout(4, 4);
        gridLayout.addStyleName("example-gridlayout");
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
//        UI.getCurrent().setPollInterval(1000);
        gridLayout.setMargin(true);
        gridLayout.setSpacing(true);
        setContent(gridLayout);

        Button addClientButton = new Button("Добавить");
        addClientButton.addClickListener(clickEvent -> addClient());
        Button updateClientButton = new Button("Редактировать");
        updateClientButton.addClickListener(clickEvent -> updateClient());
        Button deleteClientButton = new Button("Удалить");
        deleteClientButton.addClickListener(clickEvent -> deleteClient());

        gridLayout.addComponent(addClientButton, 0, 0);
        gridLayout.addComponent(updateClientButton, 1, 0);
        gridLayout.addComponent(deleteClientButton, 2, 0);

        getClientsTable();
//        getOrdersTable();
    }

    private void addClient () {

    }
    private void updateClient () {

    }
    private void deleteClient () {
//        int = (Integer)tableClients.getValue();

    }

    private void getClientsTable() {
        tableClients = new Table();
        tableClients.setSelectable(true);
        tableClients.setImmediate(true);

        tableClients.addContainerProperty("ID", Integer.class, null);
        tableClients.addContainerProperty("First name", String.class, null);
        tableClients.addContainerProperty("Surname", String.class, null);
        tableClients.addContainerProperty("Middle name", String.class, null);
        tableClients.addContainerProperty("Telephone", String.class, null);

        tableClients.setWidth("750px");
        tableClients.setHeight("229px");
//        final Label current = new Label("Selected: -");
        tableClients.addValueChangeListener(event -> tableClients.getValue());
//        gridLayout.addComponent(current, 2, 2);


        gridLayout.addComponent(tableClients, 0, 1, 3, 1);
        addDataToTable(connection.getTableClients(), tableClients);

//        new Thread(() -> {
//            while (true) {
//                getSession().lock();
//                tableClients.removeAllItems();
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

        tableOrders.setHeight("229px");
        gridLayout.addComponent(tableOrders, 0, 3, 3, 3);
        addDataToTable(connection.getTableOrders(), tableOrders);
    }

    private void addDataToTable(List<DataTable> objects, Table table) {
        int newRowId = 0;
        for (DataTable object : objects) {
            table.addItem(object.getAsArrayObjects(), newRowId++);
        }
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
