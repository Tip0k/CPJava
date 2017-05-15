/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Courier;

/**
 *
 * @author PEOPLE
 */
public class MySqlCourierDao implements CourierDao {

    private final MySqlTransportDao mySqlTransportDao;

    public MySqlCourierDao() {
        this.mySqlTransportDao = new MySqlTransportDao();
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
            int tempId = -1;
            for (Transport t : selectFreeTransport()) {
                if (t.getId() == courier.getTransport().getId()) {
                    tempId = t.getId();
                }
            }
            if (tempId < 0) {
                throw new SQLException();
            }
            preparedStatement.setInt(3, tempId);
            preparedStatement.setString(4, courier.getStatus());
            preparedStatement.setInt(5, courier.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            return false;
        }
        return true;
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
            ResultSet resultSet = statement.executeQuery("select * from courier order by courierId desc");

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
