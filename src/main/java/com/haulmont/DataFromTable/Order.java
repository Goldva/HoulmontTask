package com.haulmont.DataFromTable;

import java.sql.Date;

/**
 * Created by ermakov on 16.12.2016.
 */
public class Order {
    private int orderId;
    private String aboutOrder;
    private Client client;
    private Date createDate;
    private Date endDate;
    private double price;
    private String status;

    public Order(int orderId, Client client, Date createDate) {
        this.orderId = orderId;
        this.client = client;
        this.createDate = createDate;
    }

    public String getAboutOrder() {
        return aboutOrder;
    }

    public void setAboutOrder(String aboutOrder) {
        this.aboutOrder = aboutOrder;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public Client getClient() {
        return client;
    }

    public Date getCreateDate() {
        return createDate;
    }
}
