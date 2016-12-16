package com.haulmont.DataFromTable;

/**
 * Created by ermakov on 15.12.2016.
 */
public class Client {
    private int id;
    private String firstName;
    private String surName;
    private String middleName;
    private String telephon;

    public Client(int id) {
        this.id = id;
        this.firstName = firstName;
        this.surName = surName;
        this.middleName = middleName;
        this.telephon = telephon;
    }

    public int getId() {
        return id;
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

    public String getTelephon() {
        return telephon;
    }

    public void setTelephon(String telephon) {
        this.telephon = telephon;
    }
}
