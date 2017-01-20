package com.haulmont.utils.container;

import com.haulmont.datarows.Order;
import com.haulmont.utils.dao.OrderDAO;
import com.vaadin.data.util.BeanItemContainer;

import java.util.Collection;

public class OrderContainer {
    private OrderDAO connection;
    private BeanItemContainer<Order> containerOrders;

    public OrderContainer(OrderDAO connection) {
        this.connection = connection;
        containerOrders = new BeanItemContainer<>(Order.class);
    }

    public void addOrder(Order order) {
        connection.addRow(order);
        refresh();
    }

    public void updateOrder(Order order) {
        connection.updateRow(order);
        refresh();
    }

    public void deleteOrders(Collection<Object> deleteRows) {
        deleteRows.forEach(connection::deleteRow);
        refresh();
    }

    public void setFilteredCollectionOrders(Collection<Order> orders) {
        containerOrders.removeAllItems();
        containerOrders.addAll(orders);
    }

    public BeanItemContainer<Order> getContainerOrders() {
        refresh();
        return containerOrders;
    }

    private void refresh(){
        containerOrders.removeAllItems();
        containerOrders.addAll(connection.getTable());
    }

    public Collection<Order> getListOrders() {
        return connection.getTable();
    }
}
