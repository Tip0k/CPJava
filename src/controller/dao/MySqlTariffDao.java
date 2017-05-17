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
import model.Tariff;
import model.TransportType;

/**
 *
 * @author PEOPLE
 */
public class MySqlTariffDao implements TariffDao {

    private final MySqlTransportDao mySqlTransportDao;

    public MySqlTariffDao() {
        this.mySqlTransportDao = new MySqlTransportDao();
    }

    @Override
    public boolean insertTariff(Tariff tariff) {
        try {
            PreparedStatement preparedStatement;
            preparedStatement = MySqlDaoFactory.getConnection().prepareStatement(
                    "insert into tariff values(null, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, tariff.getName());
            preparedStatement.setString(2, tariff.getTransportType().getName());
            preparedStatement.setInt(3, tariff.getUahCPerKm());
            preparedStatement.setInt(4, tariff.getUahCPerPoint());
            preparedStatement.setInt(5, tariff.getUahCAdditionalCosts());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteTariff(Tariff tariff) {
        try {
            Statement statement = MySqlDaoFactory.getConnection().createStatement();
            statement.executeUpdate("delete from tariff where tariffId = " + tariff.getId());
        } catch (SQLException ex) {
            return false;
        }
        return true;
    }

    @Override
    public Tariff findTariff(int Id) {
        Tariff result = new Tariff();
        try {
            Statement statement = MySqlDaoFactory.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("select * from tariff where tariffId = " + Id);

            if (resultSet.next()) {
                result.setId(resultSet.getInt("tariffId"));
                result.setName(resultSet.getString("tariffName"));
                result.setTransportType(mySqlTransportDao.findTransportType(resultSet.getString("transportTypeName")));
                result.setUahCPerKm(resultSet.getInt("tariffUahCPerKm"));
                result.setUahCPerPoint(resultSet.getInt("tariffUahCPerPoint"));
                result.setUahCAdditionalCosts(resultSet.getInt("tariffUahCAdditionalCosts"));
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
    public List<Tariff> selectTariffTO() {
        ArrayList<Tariff> result = new ArrayList<>();
        try {
            Statement statement = MySqlDaoFactory.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("select * from tariff order by tariffId desc");

            while (resultSet.next()) {
                Tariff tariff = new Tariff();
                tariff.setId(resultSet.getInt("tariffId"));
                tariff.setName(resultSet.getString("tariffName"));
                tariff.setTransportType(mySqlTransportDao.findTransportType(resultSet.getString("transportTypeName")));
                tariff.setUahCPerKm(resultSet.getInt("tariffUahCPerKm"));
                tariff.setUahCPerPoint(resultSet.getInt("tariffUahCPerPoint"));
                tariff.setUahCAdditionalCosts(resultSet.getInt("tariffUahCAdditionalCosts"));
                result.add(tariff);
            }
        } catch (SQLException ex) {
            return null;
        }
        return result;
    }

    @Override
    public TransportType findTransportType(String name) {
        return mySqlTransportDao.findTransportType(name);
    }

    @Override
    public List<TransportType> selectTransportTypes() {
        return mySqlTransportDao.selectTransportTypes();
    }
}
