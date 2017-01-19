package com.haulmont.forms.mvc;

import com.haulmont.datarows.Client;
import com.haulmont.datarows.Order;
import com.haulmont.forms.Card;
import com.haulmont.forms.ClientsForm;
import com.haulmont.forms.OrderCard;
import com.haulmont.forms.OrdersForm;
import com.haulmont.utils.ClientContainer;
import com.haulmont.utils.ClientDAO;
import com.haulmont.utils.OrderContainer;
import com.haulmont.utils.OrderDAO;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.FieldEvents;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderController {
    private OrderContainer orderContainer;
    private ClientContainer clientContainer;
    private Panel menuPanel;
    private OrdersForm ordersForm;
    private OrderCard orderCard;

    private static OrderController instance = null;

    public OrderController() {
        try {
            orderContainer = new OrderContainer(new OrderDAO());
            clientContainer = new ClientContainer(new ClientDAO());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static OrderController getInstance() {
        if (instance==null) {
            instance = new OrderController();
        }
        return instance;
    }

    public void createForm(Panel menuPanel){
        this.menuPanel = menuPanel;
        this.ordersForm = new OrdersForm(menuPanel, this);
    }

    public void createAddCard() {
        orderCard = new OrderCard(menuPanel, this);
        orderCard.getClientsBox().addItems(clientContainer.getListClients());
        orderCard.addOrder();
    }

    public void createUpdateCard(Object object) {
        orderCard = new OrderCard(menuPanel, this);
        orderCard.getClientsBox().addItems(clientContainer.getListClients());

        Order order = (Order) object;
        orderCard.getAboutOrderArea().setValue(order.getAboutOrder());
        orderCard.getClientsBox().setValue(order.getClient());
        orderCard.getCreateDate().setValue(new Date(order.getMillisecondCreateDate()));
        orderCard.getEndDate().setValue(new Date(order.getMillisecondEndDate()));
        String price = String.valueOf(order.getPrice()).replaceAll("\\.", ",");
        orderCard.getPrice().setValue(price);
        orderCard.getStatus().setValue(order.getStatus());
        orderCard.getClientsBox().setEnabled(false);
        orderCard.editorOrder(order);
    }

    public void addRow() {
        Collection<Client> clients = clientContainer.getListClients();
        Client client = null;
        String selectClientName = orderCard.getClientsBox().getValue().toString();
        for (Client nextClient : clients)
            if (selectClientName.equals(nextClient.toString()))
                client = nextClient;

        Order newOrder = new Order(client, orderCard.getCreateDate().getValue());
        newOrder.setAboutOrder(orderCard.getAboutOrderArea().getValue());
        newOrder.setEndDate(orderCard.getEndDate().getValue());
        String price = orderCard.getPrice().getValue().replaceAll(",", "\\.");
        newOrder.setPrice(Double.parseDouble(price));
        newOrder.setStatus(orderCard.getStatus().getValue().toString());

        orderContainer.addOrder(newOrder);
        closeCard(orderCard.getSubWindow());
    }

    public void updateRow(Object object) {
        Order order = (Order) object;
        order.setAboutOrder(orderCard.getAboutOrderArea().getValue());
        order.setEndDate(orderCard.getEndDate().getValue());
        String price = orderCard.getPrice().getValue().replaceAll(",", "\\.");
        order.setPrice(Double.parseDouble(price));
        order.setStatus(orderCard.getStatus().getValue().toString());
        orderContainer.updateOrder(order);
        closeCard(orderCard.getSubWindow());
    }

    public void deleteRows(Collection<Object> deleteOrders) {
        orderContainer.deleteOrders(deleteOrders);
    }

    public SimpleStringFilter filterGrid(Grid grid, SimpleStringFilter filterText, Object columnId, FieldEvents.TextChangeEvent textChangeEvent){
        Container.Filterable filterable = (Container.Filterable) grid.getContainerDataSource();
        if (filterText != null)
            filterable.removeContainerFilter(filterText);

        filterText = new SimpleStringFilter(columnId, textChangeEvent.getText(),
                true, true);
        filterable.addContainerFilter(filterText);

        grid.cancelEditor();

        return filterText;
    }

    public void filtering(String aboutOrder, String clientName, String status){
        Collection<Order> orders = orderContainer.getListOrders();
        Collection<Order> result = new ArrayList<>();
        result.addAll(orders);

        for (Order order : orders){
            if (!checkText(aboutOrder, order.getAboutOrder())) {
                result.remove(order);
                continue;
            }
            if (!checkText(clientName, order.getClientName())) {
                result.remove(order);
                continue;
            }
            if (!checkText(status, order.getStatus())) {
                result.remove(order);
            }
        }
        orderContainer.setFilteredCollectionOrders(result);
    }

    public boolean checkText(String regExp, String text){
        Pattern pattern = Pattern.compile(".*" + regExp + ".*");
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }

    public void buttonOkEnabled(Card card, Button button) {
        for (AbstractComponent component : card.getAllElements()) {
            if (component.getErrorMessage() != null) {
                button.setEnabled(false);
                break;
            } else
                button.setEnabled(true);
        }
    }

    public void closeCard(Window window) {
        window.close();
    }

    public BeanItemContainer<Order> getContainer() {
        return orderContainer.getContainerOrders();
    }
}
