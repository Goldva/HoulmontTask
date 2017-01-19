package com.haulmont.utils;

import com.haulmont.datarows.Order;
import com.vaadin.data.util.BeanItemContainer;

import java.util.Collection;

public class OrderContainer {
    private OrderDAO connection;
    private BeanItemContainer<Order> containerOrders;

    public OrderContainer(OrderDAO connection) {
        this.connection = connection;
        containerOrders = new BeanItemContainer<>(Order.class, this.connection.getTable());                   //TODO: подумать
    }

    public void addOrder(Order order) {
        connection.addRow(order);
        containerOrders.removeAllItems();
        containerOrders.addAll(connection.getTable());
    }

    public void updateOrder(Order order) {
        connection.updateRow(order);
        containerOrders.removeAllItems();
        containerOrders.addAll(connection.getTable());
    }

    public void deleteOrders(Collection<Object> deleteRows) {
        for (Object item : deleteRows) {
            if (connection.deleteRow(item))
                containerOrders.removeItem(item);
        }
    }

    public void setFilteredCollectionOrders(Collection<Order> orders) {
        containerOrders.removeAllItems();
        containerOrders.addAll(orders);
    }

    public BeanItemContainer<Order> getContainerOrders() {
        return containerOrders;
    }

    public Collection<Order> getListOrders() {
        return connection.getTable();
    }
}
