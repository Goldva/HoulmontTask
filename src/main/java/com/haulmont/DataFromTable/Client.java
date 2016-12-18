package com.haulmont.DataFromTable;

public class Client implements DataTable{
    private int id;
    private String firstName;
    private String surName;
    private String middleName;
    private String telephon;

    public Client(int id) {
        this.id = id;
    }

    public Client(String firstName, String surName, String middleName, String telephon) {
        this.firstName = firstName;
        this.surName = surName;
        this.middleName = middleName;
        this.telephon = telephon;
    }

    public Client(Object[] objects) {
        this.id = (int)objects[0];
        this.firstName = (String) objects[1];
        this.surName = (String) objects[2];
        this.middleName = (String) objects[3];
        this.telephon = (String) objects[4];
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

    @Override
    public Object[] getAsArrayObjects(){
        return new Object[]{id, firstName, surName, middleName, telephon};
    }
}
