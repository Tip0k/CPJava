package controller.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlDaoFactory implements DaoFactory {//fabric method

    // пул з'єднань...
    public static Connection getConnection() {
        return DataBaseConnection.getConnection();
    }

    @Override
    public RouteDao getRouteDao() {
        return new MySqlRouteDao();
    }

    @Override
    public TransportDao getTransportDao() {
        return new MySqlTransportDao();
    }

    @Override
    public CourierDao getCourierDao() {
        return new MySqlCourierDao();
    }

    @Override
    public TariffDao getTariffDao() {
        return new MySqlTariffDao();
    }

    @Override
    public OrderDao getOrderDao() {
        return new MySqlOrderDao();
    }

    private static class DataBaseConnection {

        private static volatile DataBaseConnection DataBaseConnection;
        private static Connection connection;

        private DataBaseConnection() throws Exception {
            String url = "jdbc:mysql://127.0.0.1/CourierService";
            String name = "root";
            String password = "14382";
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(url, name, password);
            } catch (SQLException ex) {
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
}
