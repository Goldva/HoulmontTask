package com.haulmont.datarows;

import java.sql.Date;

public class Order implements DataTable{
    private int orderId;
    private String aboutOrder;
    private Client client;
    private Date createDate;
    private Date endDate;
    private double price;
    private String status;

//    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public Order(int orderId, Client client, Date createDate) {
        this.orderId = orderId;
        this.client = client;
        this.createDate = createDate;
    }

    public Order(String aboutOrder, Client client, Date createDate, Date endDate, double price, String status) {
        this.aboutOrder = aboutOrder;
        this.client = client;
        this.createDate = createDate;
        this.endDate = endDate;
        this.price = price;
        this.status = status;
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

    public int getClientId() {
        return client.getClientId();
    }

    public String getFirstName() {
        return client.getFirstName();
    }

    public String getSurName() {
        return client.getSurName();
    }

    public String getMiddleName() {
        return client.getMiddleName();
    }

    public String getTelephone() {
        return client.getTelephone();
    }

    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public Object[] getAsArrayObjects(){
        return new Object[]{orderId, aboutOrder, client.getFirstName(), client.getSurName(), client.getTelephone(),
                createDate, endDate, price, status};
    }

}
