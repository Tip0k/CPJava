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

public class MySqlCourierDao implements CourierDao {

    private final MySqlTransportDao mySqlTransportDao;
    private final RouteDao routeDao;

    public MySqlCourierDao() {
        this.mySqlTransportDao = new MySqlTransportDao();
        this.routeDao = new MySqlRouteDao();
    }

    @Override
    public boolean insertCourier(Courier courier) {
        try {
            PreparedStatement preparedStatement;
            preparedStatement = MySqlDaoFactory.getConnection().prepareStatement(
                    "insert into courier values(null, ?, ?, ?, ?)");
            preparedStatement.setString(1, courier.getName());
            preparedStatement.setString(2, courier.getPhone());
            preparedStatement.setInt(3, courier.getTransport().getId());
            preparedStatement.setString(4, courier.getStatus());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteCourier(Courier courier) {
        try {
            Statement statement = MySqlDaoFactory.getConnection().createStatement();
            statement.executeUpdate("delete from courier where courierId = " + courier.getId());
        } catch (SQLException ex) {
            return false;
        }
        return true;
    }

    @Override
    public boolean updateCourier(Courier courier) {
        try {
            PreparedStatement preparedStatement;
            preparedStatement = MySqlDaoFactory.getConnection().prepareStatement(
                    "update courier set courierName = ?, courierPhone = ?, transportId = ?, courierStatus = ? where courierId = ?");
            preparedStatement.setString(1, courier.getName());
            preparedStatement.setString(2, courier.getPhone());
            preparedStatement.setInt(3, courier.getTransport().getId());
            preparedStatement.setString(4, courier.getStatus());
            preparedStatement.setInt(5, courier.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            return false;
        }
        return true;
    }

    public List<Order> selectCourierOrders(Courier courier) {
        ArrayList<Order> result = new ArrayList<>();
        try {
            Statement statement = MySqlDaoFactory.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("select * from `order` where courierId = " + courier.getId());

            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt("orderId"));
                order.setStartPointAdress(resultSet.getString("orderStartPointAdress"));
                order.setEndPointAdress(resultSet.getString("orderEndPointAdress"));
                order.setRoute(routeDao.findRoute(resultSet.getInt("routeId")));
                order.setClientName(resultSet.getString("orderClientName"));
                order.setClientPhone(resultSet.getString("orderClientPhone"));
                order.setCourier(findCourier(resultSet.getInt("courierId")));
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

    @Override
    public Courier findCourier(int Id) {
        Courier result = new Courier();
        try {
            Statement statement = MySqlDaoFactory.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("select * from courier where courierId = " + Id);

            if (resultSet.next()) {
                result.setId(resultSet.getInt("courierId"));
                result.setName(resultSet.getString("courierName"));
                result.setPhone(resultSet.getString("courierPhone"));
                result.setTransport(mySqlTransportDao.findTransport(resultSet.getInt("transportId")));
                result.setStatus(resultSet.getString("courierStatus"));
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
    public List<Courier> selectCourierTO() {
        ArrayList<Courier> result = new ArrayList<>();
        try {
            Statement statement = MySqlDaoFactory.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("select * from courier where courierId != 1 order by courierId desc");

            while (resultSet.next()) {
                Courier courier = new Courier();
                courier.setId(resultSet.getInt("courierId"));
                courier.setName(resultSet.getString("courierName"));
                courier.setPhone(resultSet.getString("courierPhone"));
                courier.setTransport(mySqlTransportDao.findTransport(resultSet.getInt("transportId")));
                courier.setStatus(resultSet.getString("courierStatus"));
                result.add(courier);
            }
        } catch (SQLException ex) {
            return null;
        }
        return result;
    }

    @Override
    public List<Transport> selectFreeTransport() {
        ArrayList<Transport> result = new ArrayList<>();
        try {
            Statement statement = MySqlDaoFactory.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("select * from transport\n"
                    + "left outer join courier\n"
                    + "on transport.transportId = courier.transportId\n"
                    + "where courier.transportId is null\n"
                    + "order by transport.transportId desc");

            while (resultSet.next()) {
                Transport transport = new Transport();
                transport.setId(resultSet.getInt("transportId"));
                transport.setName(resultSet.getString("transportName"));
                transport.setType(mySqlTransportDao.findTransportType(resultSet.getString("transportTypeName")));
                transport.setMaxWg(resultSet.getInt("transportMaxWg"));
                transport.setMaxWcm(resultSet.getInt("transportMaxWcm"));
                transport.setMaxHcm(resultSet.getInt("transportMaxHcm"));
                transport.setMaxLcm(resultSet.getInt("transportMaxLcm"));
                result.add(transport);
            }
        } catch (SQLException ex) {
            return null;
        }
        return result;
    }
}
