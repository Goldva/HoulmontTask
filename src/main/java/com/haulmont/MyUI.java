package com.haulmont;

import javax.servlet.annotation.WebServlet;

import com.haulmont.DataFromTable.Client;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Item;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.List;


@Theme("mytheme")
public class MyUI extends UI {
    private ConnectionToHSQLDB connection;

    public MyUI() throws SQLException, ClassNotFoundException {
        this.connection = new ConnectionToHSQLDB();
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();

        Table tableClients = new Table();

        tableClients.addContainerProperty("ID", Integer.class, null);
        tableClients.addContainerProperty("First name", String.class, null);
        tableClients.addContainerProperty("Surname", String.class, null);
        tableClients.addContainerProperty("Middle name", String.class, null);
        tableClients.addContainerProperty("Telephone", String.class, null);


        List<Client> clients = connection.getTableClients();
        Object newItemId;
        for (Client client : clients){
            newItemId = tableClients.addItem();
            Item row = tableClients.getItem(newItemId);
            row.getItemProperty("ID").setValue(client.getId());
            row.getItemProperty("First name").setValue(client.getFirstName());
            row.getItemProperty("Surname").setValue(client.getFirstName());
            row.getItemProperty("Middle name").setValue(client.getFirstName());
            row.getItemProperty("Telephone").setValue(client.getFirstName());

        }


        layout.addComponents(tableClients);
        layout.setMargin(true);
        layout.setSpacing(true);
        
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
