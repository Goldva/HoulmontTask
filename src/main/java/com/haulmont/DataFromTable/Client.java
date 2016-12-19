package com.haulmont.DataFromTable;

public class Client implements DataTable{
    private int clientId;
    private String firstName;
    private String surName;
    private String middleName;
    private String telephone;

    public Client(int id) {
        this.clientId = id;
    }

    public Client() {
    }

    public Client(String firstName, String surName, String middleName, String telephone) {
        this.firstName = firstName;
        this.surName = surName;
        this.middleName = middleName;
        this.telephone = telephone;
    }

    public Client(Object[] objects) {
        this.clientId = (int)objects[0];
        this.firstName = (String) objects[1];
        this.surName = (String) objects[2];
        this.middleName = (String) objects[3];
        this.telephone = (String) objects[4];
    }

    public int getClientId() {
        return clientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public Object[] getAsArrayObjects(){
        return new Object[]{clientId, firstName, surName, middleName, telephone};
    }
}
