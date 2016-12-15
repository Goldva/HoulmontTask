package com.haulmont;

import java.sql.SQLException;

/**
 * Created by Aleksei on 08.12.2016.
 */
public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ConnectionToHSQLDB conn = new ConnectionToHSQLDB();
        conn.createTable();
        conn.addRowsToClient("zzzz", "xxxx", "vvvv", "nnnn");

//        conn.addRowsToOrders("wwwww", 5, 46, 23.4, "dsfss");
//        conn.addRowsToClient("testTable", "cccc");
//        conn.addRowsToClient("testTable", "dddd");
//        conn.addRowsToClient("testTable", "eeee");
//        conn.addRowsToClient("testTable", "ffff");
//        conn.addRowsToClient("testTable", "gggg");
//        conn.deleteTable("testTable", "19");
        conn.getTableClients();
//        conn.printTable("orders");
        conn.closeConnection();
    }
}
