/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author PEOPLE
 */
public class DataBaseConnection {

    private static volatile DataBaseConnection DataBaseConnection;
    private static Connection connection;

    private DataBaseConnection() throws Exception {
        String url = "jdbc:mysql://127.0.0.1/CourierService";
        String name = "root";
        String password = "14382";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Драйвер подключен");
            connection = DriverManager.getConnection(url, name, password);
            System.out.println("Соединение установлено");
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static Connection getConnection() {
        if (DataBaseConnection == null) {
            synchronized (DataBaseConnection.class) {
                if (DataBaseConnection == null) {
                    try {
                        DataBaseConnection = new DataBaseConnection();
                    } catch (Exception ex) {
                        return null;
                    }
                }
            }
        }
        return DataBaseConnection.connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception ex) {
            }
        }
    }
}
