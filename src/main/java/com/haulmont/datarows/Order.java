package com.haulmont.datarows;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Order implements DataTable{
    private int orderId;
    private String aboutOrder;
    private Client client;
    private Date createDate;
    private Date endDate;
    private double price;
    private String status;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");


    public Order(Client client, Date createDate) {
        this.client = client;
        this.createDate = createDate;
    }

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

    public int getOrderId() {
        return orderId;
    }

    public String getAboutOrder() {
        return aboutOrder;
    }

    public void setAboutOrder(String aboutOrder) {
        this.aboutOrder = aboutOrder;
    }

    public Client getClient() {
        return client;
    }

    public String getClientName(){
        return client.toString();
    }

    public String getTelephone() {
        return client.getTelephone();
    }

    public String getCreateDate() {
        return sdf.format(createDate);
    }

    public long getMillisecondCreateDate() {
        return createDate.getTime();
    }

    public String getEndDate() {
        return sdf.format(endDate);
    }

    public long getMillisecondEndDate() {
        return endDate.getTime();
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

    @Override
    public Object[] getAsArrayObjects(){
        return new Object[]{orderId, aboutOrder, client.getFirstName(), client.getSurName(), client.getTelephone(),
                createDate, endDate, price, status};
    }

}
