package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlDaoFactory implements DaoFactory {//fabric method

    //public static final String DRIVER = "com.mysql.jdbc.Driver";
    //public static final String DBURL = "jdbc:mysql://127.0.0.1/CourierService";
    //
    // Использовать DRIVER и DBURL для создания соединения
    // Рекомендовать реализацию/использование пула соединений
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TariffDao getTariffDao() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OrderDao getOrderDao() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
                throw new DaoException(ex);
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
