package com.haulmont.utils;

import com.haulmont.datarows.Client;
import com.haulmont.datarows.Order;
import com.haulmont.forms.Card;
import com.haulmont.forms.ClientCard;
import com.haulmont.forms.OrderCard;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.FieldEvents;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

public class Controller {
    private MyContainer container;

    private static Controller instance = null;

    public Controller() {
        try {
            container = new MyContainer(new ConnectionToHSQLDB());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Controller getInstance() {
        if (instance==null) {
            instance = new Controller();
        }
        return instance;
    }

    public void createAddClientCard(UI myUI) {
        new ClientCard(myUI).addClient();

//        new UI() {
//            @Override
//            protected void init(VaadinRequest vaadinRequest) {
//            }
//        };
//        uiTable.close();
    }

    public void createAddOrderCard(UI myUI) {
        OrderCard card = new OrderCard(myUI);
        card.getClientsBox().addItems(container.getListClients());
        card.getStatus().addItems(Arrays.asList("������������", "��������", "������ ��������"));
        card.addOrder();
    }

    public void addRowToTable(ClientCard card) {
        Client client = new Client();
        client.setFirstName(card.getFirstNameField().getValue());
        client.setSurName(card.getSurNameField().getValue());
        client.setMiddleName(card.getMiddleNameField().getValue());
        client.setTelephone(card.getTelephoneField().getValue());
        container.addClient(client);
        closeCard(card.getSubWindow());

    }

    public void addRowToTable(OrderCard card) {
        Collection<Client> clients = container.getListClients();
        Client client = null;
        String selectClientName = card.getClientsBox().getValue().toString();
        for (Client nextClient : clients)
            if (selectClientName.equals(nextClient.toString()))
                client = nextClient;

        Order newOrder = new Order(client, card.getCreateDate().getValue());
        newOrder.setAboutOrder(card.getAboutOrderField().getValue());
        newOrder.setEndDate(card.getEndDate().getValue());
        String price = card.getPrice().getValue().replaceAll(",", "\\.");
        newOrder.setPrice(Double.parseDouble(price));
        newOrder.setStatus(card.getStatus().getValue().toString());

        container.addOrder(newOrder);
        closeCard(card.getSubWindow());
    }

    public void createUpdateClientCard(UI myUI, Object object) {
        Client client = (Client) object;
        ClientCard update = new ClientCard(myUI);
        update.getFirstNameField().setValue(client.getFirstName());
        update.getSurNameField().setValue(client.getSurName());
        update.getMiddleNameField().setValue(client.getMiddleName());
        update.getTelephoneField().setValue(client.getTelephone());
        update.editorClient(client);
    }

    public void createUpdateOrderCard(UI myUI, Object object) {
        Order order = (Order) object;
        OrderCard card = new OrderCard(myUI);

        card.getClientsBox().addItems(container.getListClients());
        card.getStatus().addItems(Arrays.asList("������������", "��������", "������ ��������"));

        card.getAboutOrderField().setValue(order.getAboutOrder());
        card.getClientsBox().setValue(order.getClient());
        card.getCreateDate().setValue(new Date(order.getMillisecondCreateDate()));
        card.getEndDate().setValue(new Date(order.getMillisecondEndDate()));
        String price = String.valueOf(order.getPrice()).replaceAll("\\.", ",");
        card.getPrice().setValue(price);
        card.getStatus().setValue(order.getStatus());
        card.getClientsBox().setEnabled(false);
        card.editorOrder(order);
    }

    public void updateRowInTheTable(ClientCard card, Client client) {
        client.setFirstName(card.getFirstNameField().getValue());
        client.setSurName(card.getSurNameField().getValue());
        client.setMiddleName(card.getMiddleNameField().getValue());
        client.setTelephone(card.getTelephoneField().getValue());
        container.updateClient(client);
        closeCard(card.getSubWindow());
    }

    public void updateRowInTheTable(OrderCard card, Order order) {
        order.setAboutOrder(card.getAboutOrderField().getValue());
        order.setEndDate(card.getEndDate().getValue());
        String price = card.getPrice().getValue().replaceAll(",", "\\.");
        order.setPrice(Double.parseDouble(price));
        order.setStatus(card.getStatus().getValue().toString());
        container.updateOrder(order);
        closeCard(card.getSubWindow());
    }

    public SimpleStringFilter filter(Grid grid, SimpleStringFilter filterText, Object columnId, FieldEvents.TextChangeEvent textChangeEvent){
        Container.Filterable filterable = (Container.Filterable) grid.getContainerDataSource();
        if (filterText != null)
            filterable.removeContainerFilter(filterText);

        filterText = new SimpleStringFilter(columnId, textChangeEvent.getText(),
                true, true);
        filterable.addContainerFilter(filterText);

        grid.cancelEditor();

        return filterText;
    }

    public void deleteClients(Collection<Object> deleteClients) {
        container.deleteClients(deleteClients);
    }

    public void deleteOrders(Collection<Object> deleteOrders) {
        container.deleteOrders(deleteOrders);
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

    public BeanItemContainer<Client> getContainerClients() {
        return container.getContainerClients();
    }

    public BeanItemContainer<Order> getContainerOrders() {
        return container.getContainerOrders();
    }
}