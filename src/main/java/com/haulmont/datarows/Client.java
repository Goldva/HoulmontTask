package com.haulmont.datarows;

public class Client {
    private long clientId;
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

    public long getClientId() {
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
    public String toString() {
        return String.format("%s %s %s", getSurName(), getFirstName(), getMiddleName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (clientId != client.clientId) return false;
        if (!firstName.equals(client.firstName)) return false;
        if (!surName.equals(client.surName)) return false;
        if (middleName != null ? !middleName.equals(client.middleName) : client.middleName != null) return false;
        return telephone.equals(client.telephone);

    }

    @Override
    public int hashCode() {
        int result = (int) (clientId ^ (clientId >>> 32));
        result = 31 * result + firstName.hashCode();
        result = 31 * result + surName.hashCode();
        result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
        result = 31 * result + telephone.hashCode();
        return result;
    }
}
