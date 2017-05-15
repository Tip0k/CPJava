/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.dao;

import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Route;

/**
 *
 * @author PEOPLE
 */
public class MySqlRouteDao implements RouteDao {

    @Override
    public boolean insertRoute(Route route) {
        try {
            PreparedStatement preparedStatement;
            preparedStatement = MySqlDaoFactory.getConnection().prepareStatement(
                    "insert into route values(null, ?, ?, ?)");
            preparedStatement.setString(1, route.getStartPoint());
            preparedStatement.setString(2, route.getEndPoint());
            preparedStatement.setInt(3, route.getDistanceM());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteRoute(Route route) {
        try {
            Statement statement = MySqlDaoFactory.getConnection().createStatement();
            statement.executeUpdate("delete from route where routeId = " + route.getId());
        } catch (SQLException ex) {
            return false;
        }
        return true;
    }

    @Override
    public Route findRoute(int Id) {
        Route result = new Route();
        try {
            Statement statement = MySqlDaoFactory.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("select * from route where routeId = " + Id);

            if (resultSet.next()) {
                result.setId(resultSet.getInt("routeId"));
                result.setStartPoint(resultSet.getString("routeStartPoint"));
                result.setEndPoint(resultSet.getString("routeEndPoint"));
                result.setDistanceM(resultSet.getInt("routeDistanceM"));
            }
            if (resultSet.next()) {
                throw new SQLException();
            }
        } catch (SQLException ex) {
            return null;
        }
        return result;
    }

    @Override
    public List<Route> selectRoutesTO() {
        ArrayList<Route> result = new ArrayList<>();
        try {
            Statement statement = MySqlDaoFactory.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("select * from route order by routeId desc");

            while (resultSet.next()) {
                Route route = new Route();
                route.setId(resultSet.getInt("routeId"));
                route.setStartPoint(resultSet.getString("routeStartPoint"));
                route.setEndPoint(resultSet.getString("routeEndPoint"));
                route.setDistanceM(resultSet.getInt("routeDistanceM"));
                result.add(route);
            }
        } catch (SQLException ex) {
            return null;
        }
        return result;
    }
}
