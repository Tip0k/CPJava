package controller;

import model.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import view.MainView;

public class CarsController {

    private Connection connection;

    public CarsController() {
        connection = DataBaseConnection.getConnection();
    }

    public String[] getCarTypes() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select carTypesType from carTypes order by carTypesType desc");

            int n = 0;
            while (resultSet.next()) {
                n++;
            }
            resultSet.first();
            resultSet.previous();
            String[] result = new String[n];
            n--;
            while (resultSet.next()) {
                result[n--] = resultSet.getString("carTypesType");
            }
            return result;
        } catch (Exception ex) {
            return new String[]{"Error"};
        }
    }

    public void addCar(String name, String type, int maxWkg, int maxWm, int maxHm, int maxLm) {
        new Thread() {
            @Override
            public void run() {
                try {
                    PreparedStatement preparedStatement;
                    preparedStatement = connection.prepareStatement(
                            "insert into cars values(null, ?, ?, ?, ?, ?, ?)");
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, type);
                    preparedStatement.setInt(3, maxWkg);
                    preparedStatement.setInt(4, maxWm);
                    preparedStatement.setInt(5, maxHm);
                    preparedStatement.setInt(6, maxLm);
                    preparedStatement.executeUpdate();
                } catch (Exception ex) {
                    MainView.showErrorPane("Не вдалось додати запис у БД.", ex);
                }
            }
        }.start();
    }

    public void updateTableCars(JTable table) {
        try {
            table.setModel(new CarsTableModel());
            table.getColumnModel().getColumn(0).setMaxWidth(50);
            table.getColumnModel().getColumn(1).setMinWidth(150);
            table.getColumnModel().getColumn(2).setMinWidth(100);
            table.getColumnModel().getColumn(2).setMaxWidth(120);
            table.getColumnModel().getColumn(3).setMinWidth(100);
            table.getColumnModel().getColumn(3).setMaxWidth(100);
            table.getColumnModel().getColumn(4).setMinWidth(110);
            table.updateUI();

        } catch (Exception ex) {
            MainView.showErrorPane("Сталась помилка при завантаженні записів з БД.", ex);
        }
    }

    public void deleteCar(int ID) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("delete from cars where carsID = " + ID);
        } catch (Exception ex) {

        }
    }

    public class CarsTableModel extends AbstractTableModel {

        public CarsTableModel() {
            super();
        }

        @Override
        public int getRowCount() {
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select count(*) from cars");
                resultSet.next();
                return resultSet.getInt("count(*)");
            } catch (Exception ex) {
                return 0;
            }
        }

        @Override
        public int getColumnCount() {
            return 5;
        }

        @Override
        public String getColumnName(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return "ID";
                case 1:
                    return "Назва";
                case 2:
                    return "Тип";
                case 3:
                    return "Макс. Вага (кг)";
                case 4:
                    return "Макс. Габарити (м)";
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
                ResultSet resultSet = statement.executeQuery("select * from cars order by carsID limit " + rowIndex + ",1");

                resultSet.next();
                switch (columnIndex) {
                    case 0:
                        return resultSet.getString("carsID");
                    case 1:
                        return resultSet.getString("carsName");
                    case 2:
                        return resultSet.getString("carTypesType");
                    case 3:
                        return resultSet.getInt("carsMaxWkg");
                    case 4:
                        return "" + resultSet.getInt("carsMaxWm") + "x" + resultSet.getInt("carsMaxHm") + "x" + resultSet.getInt("carsMaxLm");
                    default:
                        return "Error";
                }
            } catch (Exception ex) {
                return "Error";
            }
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
