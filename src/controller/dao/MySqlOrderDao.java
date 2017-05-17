/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.dao;

import model.Transport;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Courier;
import model.Order;

/**
 *
 * @author PEOPLE
 */
public class MySqlOrderDao implements OrderDao {

    private final RouteDao routeDao;
    private final CourierDao courierDao;

    public MySqlOrderDao() {
        this.routeDao = new MySqlRouteDao();
        this.courierDao = new MySqlCourierDao();
    }

//    @Override
//    public boolean insertCourier(Courier courier) {
//        try {
//            PreparedStatement preparedStatement;
//            preparedStatement = MySqlDaoFactory.getConnection().prepareStatement(
//                    "insert into courier values(null, ?, ?, ?, ?)");
//            preparedStatement.setString(1, courier.getName());
//            preparedStatement.setString(2, courier.getPhone());
//            preparedStatement.setInt(3, courier.getTransport().getId());
//            preparedStatement.setString(4, courier.getStatus());
//            preparedStatement.executeUpdate();
//        } catch (SQLException ex) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public boolean deleteCourier(Courier courier) {
//        try {
//            Statement statement = MySqlDaoFactory.getConnection().createStatement();
//            statement.executeUpdate("delete from courier where courierId = " + courier.getId());
//        } catch (SQLException ex) {
//            return false;
//        }
//        return true;
//    }
//
    @Override
    public boolean updateOrder(Order order) {
        try {
            PreparedStatement preparedStatement;
            preparedStatement = MySqlDaoFactory.getConnection().prepareStatement(
                    "update `order` set orderStatus = ? where courierId = ?");
            preparedStatement.setString(1, order.getStatus());
            preparedStatement.setInt(2, order.getCourier().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            return false;
        }
        return true;
    }

    @Override
    public Courier findCourier(int Id) {
        return courierDao.findCourier(Id);
    }
//
//    @Override
//    public List<Courier> selectCourierTO() {
//        ArrayList<Courier> result = new ArrayList<>();
//        try {
//            Statement statement = MySqlDaoFactory.getConnection().createStatement();
//            ResultSet resultSet = statement.executeQuery("select * from courier order by courierId desc");
//
//            while (resultSet.next()) {
//                Courier courier = new Courier();
//                courier.setId(resultSet.getInt("courierId"));
//                courier.setName(resultSet.getString("courierName"));
//                courier.setPhone(resultSet.getString("courierPhone"));
//                courier.setTransport(mySqlTransportDao.findTransport(resultSet.getInt("transportId")));
//                courier.setStatus(resultSet.getString("courierStatus"));
//                result.add(courier);
//            }
//        } catch (SQLException ex) {
//            return null;
//        }
//        return result;
//    }
//
//    @Override
//    public List<Transport> selectFreeTransport() {
//        ArrayList<Transport> result = new ArrayList<>();
//        try {
//            Statement statement = MySqlDaoFactory.getConnection().createStatement();
//            ResultSet resultSet = statement.executeQuery("select * from transport\n"
//                    + "left outer join courier\n"
//                    + "on transport.transportId = courier.transportId\n"
//                    + "where courier.transportId is null\n"
//                    + "order by transport.transportId desc");
//
//            while (resultSet.next()) {
//                Transport transport = new Transport();
//                transport.setId(resultSet.getInt("transportId"));
//                transport.setName(resultSet.getString("transportName"));
//                transport.setType(mySqlTransportDao.findTransportType(resultSet.getString("transportTypeName")));
//                transport.setMaxWg(resultSet.getInt("transportMaxWg"));
//                transport.setMaxWcm(resultSet.getInt("transportMaxWcm"));
//                transport.setMaxHcm(resultSet.getInt("transportMaxHcm"));
//                transport.setMaxLcm(resultSet.getInt("transportMaxLcm"));
//                result.add(transport);
//            }
//        } catch (SQLException ex) {
//            return null;
//        }
//        return result;
//    }
    @Override
    public boolean deleteOrder(Order order) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Order findOrder(int Id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Order> selectOrderTO() {
        ArrayList<Order> result = new ArrayList<>();
        try {
            Statement statement = MySqlDaoFactory.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("select * from `order` order by orderId desc");

            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt("orderId"));
                order.setStartPointAdress(resultSet.getString("orderStartPointAdress"));
                order.setEndPointAdress(resultSet.getString("orderEndPointAdress"));
                order.setRoute(routeDao.findRoute(resultSet.getInt("routeId")));
                order.setClientName(resultSet.getString("orderClientName"));
                order.setClientPhone(resultSet.getString("orderClientPhone"));
                order.setCourier(courierDao.findCourier(resultSet.getInt("courierId")));
                order.setCostUahC(resultSet.getInt("orderCostUahC"));
                order.setOrderDate(resultSet.getString("orderOrderDate"));
                order.setDoneDate(resultSet.getString("orderDoneDate"));
                order.setDelayMin(resultSet.getInt("orderDelayMin"));
                order.setStatus(resultSet.getString("orderStatus"));
                result.add(order);
            }
        } catch (SQLException ex) {
            return null;
        }
        return result;
    }
}
