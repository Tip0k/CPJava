/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PEOPLE
 */
public class MySqlTransportDao implements TransportDao {

    @Override
    public boolean insertTransport(Transport transport) {
        try {
            PreparedStatement preparedStatement;
            preparedStatement = MySqlDaoFactory.getConnection().prepareStatement(
                    "insert into transport values(null, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, transport.getName().toString());
            preparedStatement.setString(2, transport.getType().toString());
            preparedStatement.setInt(3, transport.getMaxWg());
            preparedStatement.setInt(4, transport.getMaxWcm());
            preparedStatement.setInt(5, transport.getMaxHcm());
            preparedStatement.setInt(6, transport.getMaxLcm());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteTransport(Transport transport) {
        try {
            Statement statement = MySqlDaoFactory.getConnection().createStatement();
            statement.executeUpdate("delete from transport where transportId = " + transport.getId());
        } catch (SQLException ex) {
            return false;
        }
        return true;
    }

    @Override
    public List<Transport> selectTransportTO() {
        ArrayList<Transport> result = new ArrayList<>();
        try {
            Statement statement = MySqlDaoFactory.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("select * from transport order by transportId desc");

            while (resultSet.next()) {
                Transport transport = new Transport();
                transport.setId(resultSet.getInt("transportId"));
                transport.setName(resultSet.getString("transportName"));
                transport.setType(resultSet.getString("transportTypeName"));
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

    @Override
    public List<TransportType> getTransportTypes() {
        ArrayList<TransportType> result = new ArrayList<>();
        try {
            Statement statement = MySqlDaoFactory.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("select * from transportType order by transportTypeName");

            while (resultSet.next()) {                
                TransportType type = new TransportType();
                type.setName(resultSet.getString("transportTypeName"));
                type.setKmPerH(resultSet.getInt("transportTypeKmPerH"));
                result.add(type);
            }
        } catch (SQLException ex) {
            return null;
        }
        return result;
    }
}
