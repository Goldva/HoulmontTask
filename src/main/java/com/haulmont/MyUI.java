package com.haulmont;

import com.haulmont.utils.ConnectionToHSQLDB;
import com.haulmont.utils.MyContainer;
import com.haulmont.forms.ClientsForm;
import com.haulmont.forms.OrdersForm;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import javax.servlet.annotation.WebServlet;
import java.sql.SQLException;


@Theme("mytheme")
public class MyUI extends UI {
    private GridLayout gridLayout;
    private MyContainer container;
    private Grid clientsGrid;

    public MyUI() throws SQLException, ClassNotFoundException {
        gridLayout = new GridLayout(4, 4);
        gridLayout.addStyleName("example-gridlayout");
        container = new MyContainer(new ConnectionToHSQLDB());
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        VerticalLayout layout = new VerticalLayout();
        MenuBar menu = new MenuBar();
        menu.addStyleName("mybarmenu");
        layout.addComponent(menu);

        Panel menuPanel = new Panel();

        MenuBar.Command clientCommand = new MenuBar.Command() {
            MenuBar.MenuItem previous = null;

            public void menuSelected(MenuBar.MenuItem selectedItem) {
                ClientsForm clientsForm = new ClientsForm(menuPanel, container);
                clientsForm.getClientForm();

                if (previous != null)
                    previous.setStyleName(null);
                selectedItem.setStyleName("highlight");
                previous = selectedItem;
            }
        };

        MenuBar.Command ordersCommand = new MenuBar.Command() {
            MenuBar.MenuItem previous = null;

            public void menuSelected(MenuBar.MenuItem selectedItem) {
                OrdersForm ordersForm = new OrdersForm(menuPanel, container);
                ordersForm.getOrderForm();
                if (previous != null)
                    previous.setStyleName(null);
                selectedItem.setStyleName("highlight");
                previous = selectedItem;
            }
        };

        layout.addComponent(menuPanel);
        setContent(layout);

        menu.addItem("Clients", null, clientCommand);
        menu.addItem("Orders", null, ordersCommand);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
