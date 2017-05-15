package controller;

import model.Locality;
import model.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import model.Route;
import model.Tools;
import controller.dao.DaoFactory;
import controller.dao.MySqlDaoFactory;
import controller.dao.RouteDao;
import view.MainView;

public class RoutesController {

    private final RouteDao routeDao;

    public RoutesController() {
        routeDao = new MySqlDaoFactory().getRouteDao();
    }

    public ArrayList<Route> getRoutes() {
        return new ArrayList<Route>(routeDao.selectRoutesTO());
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

    public void deleteRoute(int ID) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("delete from routes where routesID = " + ID);
        } catch (Exception ex) {

        }
    }

}
