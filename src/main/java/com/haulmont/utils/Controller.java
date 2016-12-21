package com.haulmont.utils;

import java.sql.SQLException;

public class Controller {
    private MyContainer container;

    private static Controller instance = null;

    public static Controller getInstance() throws SQLException, ClassNotFoundException {
        if (instance==null) {
            instance = new Controller();
        }
        return instance;
    }

    public Controller() throws SQLException, ClassNotFoundException {
        container = new MyContainer(new ConnectionToHSQLDB());
    }
}
