package controller;

import model.Locality;
import model.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import view.MainView;

public class RoutesController {

    private Connection connection;

    public RoutesController() {
        connection = DataBaseConnection.getConnection();
    }

    public void addRoute(Locality a, Locality b, int distance) {
        new Thread() {
            @Override
            public void run() {
                try {
                    PreparedStatement preparedStatement;
                    preparedStatement = connection.prepareStatement(
                            "insert into routes values(null, ?, ?, ?)");
                    preparedStatement.setString(1, a.toString());
                    preparedStatement.setString(2, b.toString());
                    preparedStatement.setInt(3, distance);
                    preparedStatement.executeUpdate();
                } catch (Exception ex) {
                    MainView.showErrorPane("Не вдалось додати запис у БД.", ex);
                }
            }
        }.start();
    }

    public void updateTableRoutes(JTable table) {
        try {
            table.setModel(new RoutesTableModel());
            table.getColumnModel().getColumn(0).setMaxWidth(50);
            table.getColumnModel().getColumn(1).setMinWidth(180);
            table.getColumnModel().getColumn(2).setMinWidth(180);
            table.updateUI();
        } catch (Exception ex) {
            MainView.showErrorPane("Сталась помилка при завантаженні записів з БД.", ex);
        }
    }

    public void deleteRoute(int ID) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("delete from routes where routesID = " + ID);
        } catch (Exception ex) {

        }
    }

    public class RoutesTableModel extends AbstractTableModel {

//        private ArrayList<String> routesStartPoints = new ArrayList<>();
//        private ArrayList<String> routesEndPoints = new ArrayList<>();
//        private ArrayList<Integer> routesDistances = new ArrayList<>();
        public RoutesTableModel() {
            super();
//            try {
//                Statement statement = connection.createStatement();
//                ResultSet resultSet = statement.executeQuery("select * from routes");
//
//                while (resultSet.next()) {
//                    routesStartPoints.add(resultSet.getString("routesStartPoint"));
//                    routesEndPoints.add(resultSet.getString("routesEndPoint"));
//                    routesDistances.add(resultSet.getInt("routesDistance"));
//                }
//            } catch (Exception ex) {
//                System.out.println(ex.getMessage());
//            }
        }

        @Override
        public int getRowCount() {
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select count(*) from routes");
                resultSet.next();
                return resultSet.getInt("count(*)");
            } catch (Exception ex) {
                return 0;
            }
        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public String getColumnName(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return "ID";
                case 1:
                    return "Пункт відправлення";
                case 2:
                    return "Пункт прибуття";
                case 3:
                    return "Відстань (км)";
                default:
                    return "";
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM routes order by routesID limit " + rowIndex + ",1");

                resultSet.next();
                switch (columnIndex) {
                    case 0:
                        return resultSet.getString("routesID");
                    case 1:
                        return resultSet.getString("routesStartPoint");
                    case 2:
                        return resultSet.getString("routesEndPoint");
                    case 3:
                        return (float) resultSet.getInt("routesDistance") / 1000;
                    default:
                        return "Error";
                }
            } catch (Exception ex) {
                return "Error";
            }

//            switch (columnIndex) {
//                case 0:
//                    return routesStartPoints.get(rowIndex);
//                case 1:
//                    return routesEndPoints.get(rowIndex);
//                case 2:
//                    return routesDistances.get(rowIndex);
//                default:
//                    return "";
//            }
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void addTableModelListener(TableModelListener l) {
            listenerList.add(TableModelListener.class, l);
        }

        @Override
        public void removeTableModelListener(TableModelListener l) {
            listenerList.remove(TableModelListener.class, l);
        }
    }
}
