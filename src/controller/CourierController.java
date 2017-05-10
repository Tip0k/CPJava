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
import model.CourierStatus;

public class CourierController {

    private Connection connection;

    public CourierController() {
        connection = DataBaseConnection.getConnection();
    }

    public String[] getFreeCarsID(String partOfName) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select cars.carsID from cars\n"
                    + "left outer join couriers\n"
                    + "on cars.carsID = couriers.carsID\n"
                    + "where couriers.carsID is null and cars.carsName like \'%" + partOfName + "%\'\n"
                    + "order by cars.carsID desc");

            int n = 0;
            while (resultSet.next()) {
                n++;
            }
            resultSet.first();
            resultSet.previous();
            String[] result = new String[n];
            n--;
            while (resultSet.next()) {
                result[n--] = resultSet.getString("carsID");
            }
            return result;
        } catch (Exception ex) {
            return new String[]{"Error"};
        }
    }

    public String[] getFreeCarsName(String ID) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select cars.carsName from cars\n"
                    + "left outer join couriers\n"
                    + "on cars.carsID = couriers.carsID\n"
                    + "where couriers.carsID is null and cars.carsID like \'%" + ID + "%\'\n"
                    + "order by cars.carsName desc");

            int n = 0;
            while (resultSet.next()) {
                n++;
            }
            resultSet.first();
            resultSet.previous();
            String[] result = new String[n];
            n--;
            while (resultSet.next()) {
                result[n--] = resultSet.getString("carsName");
            }
            return result;
        } catch (Exception ex) {
            return new String[]{"Error"};
        }
    }

    public void addCourier(String PIP, int ID) {
        new Thread() {
            @Override
            public void run() {
                try {
                    PreparedStatement preparedStatement;
                    preparedStatement = connection.prepareStatement(
                            "insert into couriers values(null, ?, ?, ?)");
                    preparedStatement.setString(1, PIP);
                    preparedStatement.setInt(2, ID);
                    preparedStatement.setString(3, CourierStatus.UNKNOWN);
                    preparedStatement.executeUpdate();
                } catch (Exception ex) {
                    MainView.showErrorPane("Не вдалось додати запис у БД.", ex);
                }
            }
        }.start();
    }

    public void updateTableCouriers(JTable table) {
        try {
            table.setModel(new CourierTableModel());
            table.getColumnModel().getColumn(0).setMaxWidth(50);
            table.getColumnModel().getColumn(1).setMinWidth(180);
            table.getColumnModel().getColumn(2).setMinWidth(100);
            table.getColumnModel().getColumn(2).setMaxWidth(120);
            table.getColumnModel().getColumn(3).setMinWidth(100);
            table.getColumnModel().getColumn(3).setMaxWidth(100);
            table.updateUI();

        } catch (Exception ex) {
            MainView.showErrorPane("Сталась помилка при завантаженні записів з БД.", ex);
        }
    }

    public void deleteCourier(int ID) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("delete from couriers where couriersID = " + ID);
        } catch (Exception ex) {

        }
    }

    public class CourierTableModel extends AbstractTableModel {

        public CourierTableModel() {
            super();
        }

        @Override
        public int getRowCount() {
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select count(*) from couriers");
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
                    return "ПІП";
                case 2:
                    return "Транспорт (ID)";
                case 3:
                    return "Статус";
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
                ResultSet resultSet = statement.executeQuery("select * from couriers order by couriersID limit " + rowIndex + ",1");

                resultSet.next();
                switch (columnIndex) {
                    case 0:
                        return resultSet.getString("couriersID");
                    case 1:
                        return resultSet.getString("couriersName");
                    case 2:
                        return resultSet.getString("carsID");
                    case 3:
                        return resultSet.getString("couriersStatus");
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