package com.haulmont;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;

import javax.servlet.annotation.WebServlet;
import java.sql.Date;
import java.sql.SQLException;


@Theme("mytheme")
public class MyUI extends UI {
    private GridLayout gridLayout;
    private Container containerClients;
    private Container containerOrders;
    private DataProcessing dataProc;


    public MyUI() throws SQLException, ClassNotFoundException {
        gridLayout = new GridLayout(4, 4);
        gridLayout.addStyleName("example-gridlayout");
        containerClients = new IndexedContainer();
        containerOrders = new IndexedContainer();
        dataProc = new DataProcessing(containerClients, containerOrders);
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
//        UI.getCurrent().setPollInterval(1000);
        gridLayout.setMargin(true);
        gridLayout.setSpacing(true);
        setContent(gridLayout);

        Table tableClients = createClientsTable();
        Table tableOrders = createOrdersTable();

        Button addClientButton = new Button("Добавить");
        addClientButton.addClickListener(clickEvent -> dataProc.addClient());
        Button updateClientButton = new Button("Редактировать");
        updateClientButton.addClickListener(clickEvent -> dataProc.updateClient());
        Button deleteClientButton = new Button("Удалить");
        deleteClientButton.addClickListener(clickEvent -> {
            Item text = tableClients.getItem(tableClients.getValue());
            dataProc.deleteClient(text);
        });

        gridLayout.addComponent(addClientButton, 0, 0);
        gridLayout.addComponent(updateClientButton, 1, 0);
        gridLayout.addComponent(deleteClientButton, 2, 0);

    }


    private Table createClientsTable() {
        containerClients.addContainerProperty("ID", Integer.class, null);
        containerClients.addContainerProperty("First name", String.class, null);
        containerClients.addContainerProperty("Surname", String.class, null);
        containerClients.addContainerProperty("Middle name", String.class, null);
        containerClients.addContainerProperty("Telephone", String.class, null);

        Table tableClients = new Table();
        tableClients.setContainerDataSource(containerClients);
        tableClients.setSelectable(true);
        tableClients.setImmediate(true);

        tableClients.setWidth("750px");
        tableClients.setHeight("229px");
        tableClients.addValueChangeListener(event -> tableClients.getValue());

        gridLayout.addComponent(tableClients, 0, 1, 3, 1);
        dataProc.addDataToTableClients();
        return tableClients;
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

    private Table createOrdersTable() {
        containerOrders.addContainerProperty("ID", Integer.class, null);
        containerOrders.addContainerProperty("About order", String.class, null);
        containerOrders.addContainerProperty("First name", String.class, null);
        containerOrders.addContainerProperty("Surname", String.class, null);
        containerOrders.addContainerProperty("Telephone", String.class, null);
        containerOrders.addContainerProperty("Create date", Date.class, null);
        containerOrders.addContainerProperty("End date", Date.class, null);
        containerOrders.addContainerProperty("Price", Double.class, null);
        containerOrders.addContainerProperty("Status", String.class, null);

        Table tableOrders = new Table();
        tableOrders.setContainerDataSource(containerOrders);
        tableOrders.setSelectable(true);
        tableOrders.setImmediate(true);


        tableOrders.setHeight("229px");
        gridLayout.addComponent(tableOrders, 0, 3, 3, 3);
        dataProc.addDataToTableOrders();
        return tableOrders;
    }


    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
