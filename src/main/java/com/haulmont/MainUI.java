package com.haulmont;

import com.haulmont.forms.mvc.ClientController;
import com.haulmont.forms.mvc.OrderController;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;


@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {
    private ClientController clientController;
    private OrderController orderController;


    @Override
    protected void init(VaadinRequest vaadinRequest) {
        clientController = ClientController.getInstance();
        orderController = OrderController.getInstance();

        VerticalLayout layout = new VerticalLayout();
        MenuBar menu = new MenuBar();
        menu.addStyleName(ValoTheme.THEME_NAME);
        layout.addComponent(menu);

        Panel menuPanel = new Panel();

        MenuBar.Command clientCommand = new MenuBar.Command() {
            MenuBar.MenuItem previous = null;

            public void menuSelected(MenuBar.MenuItem selectedItem) {
                clientController.createForm(menuPanel);
                if (previous != null)
                    previous.setStyleName(null);
                selectedItem.setStyleName(ValoTheme.THEME_NAME);
                previous = selectedItem;
            }
        };

        MenuBar.Command ordersCommand = new MenuBar.Command() {
            MenuBar.MenuItem previous = null;

            public void menuSelected(MenuBar.MenuItem selectedItem) {
                orderController.createForm(menuPanel);
                if (previous != null)
                    previous.setStyleName(null);
                selectedItem.setStyleName(ValoTheme.THEME_NAME);
                previous = selectedItem;
            }
        };

        layout.addComponent(menuPanel);
        setContent(layout);

        menu.addItem("Clients", null, clientCommand);
        menu.addItem("Orders", null, ordersCommand);
    }

}
