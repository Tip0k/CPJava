package controller.dao;

import model.Transport;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.TransportType;

public class MySqlTransportDao implements TransportDao {

    @Override
    public boolean insertTransport(Transport transport) {
        try {
            PreparedStatement preparedStatement;
            preparedStatement = MySqlDaoFactory.getConnection().prepareStatement(
                    "insert into transport values(null, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, transport.getName());
            preparedStatement.setString(2, transport.getType().getName());
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
    public Transport findTransport(int Id) {
        Transport result = new Transport();
        try {
            Statement statement = MySqlDaoFactory.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("select * from transport where transportId = " + Id);

            if (resultSet.next()) {
                result.setId(resultSet.getInt("transportId"));
                result.setName(resultSet.getString("transportName"));
                result.setType(findTransportType(resultSet.getString("transportTypeName")));
                result.setMaxWg(resultSet.getInt("transportMaxWg"));
                result.setMaxWcm(resultSet.getInt("transportMaxWcm"));
                result.setMaxHcm(resultSet.getInt("transportMaxHcm"));
                result.setMaxLcm(resultSet.getInt("transportMaxLcm"));
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
    public TransportType findTransportType(String name) {
        TransportType result = new TransportType();
        try {
            Statement statement = MySqlDaoFactory.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("select * from transportType where transportTypeName = \'" + name + "\'");

            if (resultSet.next()) {
                result.setName(resultSet.getString("transportTypeName"));
                result.setKmPerH(resultSet.getInt("transportTypeKmPerH"));
                result.setMaxDistanceM(resultSet.getInt("transportTypeMaxDistanceM"));
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
    public List<Transport> selectTransportTO() {
        ArrayList<Transport> result = new ArrayList<>();
        try {
            Statement statement = MySqlDaoFactory.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("select * from transport where transportId != 1 order by transportId desc");

            while (resultSet.next()) {
                Transport transport = new Transport();
                transport.setId(resultSet.getInt("transportId"));
                transport.setName(resultSet.getString("transportName"));
                transport.setType(findTransportType(resultSet.getString("transportTypeName")));
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
    public List<TransportType> selectTransportTypes() {
        ArrayList<TransportType> result = new ArrayList<>();
        try {
            Statement statement = MySqlDaoFactory.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("select * from transportType where transportTypeName != \'reserved\' order by transportTypeName");

            while (resultSet.next()) {
                TransportType type = new TransportType();
                type.setName(resultSet.getString("transportTypeName"));
                type.setKmPerH(resultSet.getInt("transportTypeKmPerH"));
                type.setMaxDistanceM(resultSet.getInt("transportTypeMaxDistanceM"));
                result.add(type);
            }
        } catch (SQLException ex) {
            return null;
        }
        return result;
    }
}
