/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Courier;
import model.CourierStatus;
import model.Order;
import model.OrderItem;
import model.Tariff;
import model.TransportType;

/**
 *
 * @author PEOPLE
 */
public class MySqlOrderDao implements OrderDao {

    private final RouteDao routeDao;
    private final CourierDao courierDao;
    private final TransportDao transportDao;
    private final TariffDao tariffDao;

    public MySqlOrderDao() {
        this.routeDao = new MySqlRouteDao();
        this.courierDao = new MySqlCourierDao();
        this.transportDao = new MySqlTransportDao();
        this.tariffDao = new MySqlTariffDao();
    }

    @Override
    public boolean updateOrder(Order order) {
        try {
            PreparedStatement preparedStatement;
            preparedStatement = MySqlDaoFactory.getConnection().prepareStatement(
                    "update `order` set orderStatus = ?, orderDoneDate = ?, orderDelayMin = ? where courierId = ? and orderId = ?");
            preparedStatement.setString(1, order.getStatus());
            preparedStatement.setString(2, order.getDoneDate());
            preparedStatement.setInt(3, order.getDelayMin());
            preparedStatement.setInt(4, order.getCourier().getId());
            preparedStatement.setInt(5, order.getId());
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

    @Override
    public List<Courier> selectCouriers(String courierStatus, int maxWg, int maxWcm, int maxHcm, int maxLcm, int maxDistanceM) {
        ArrayList<Courier> result = new ArrayList<>();
        try {
            Statement statement = MySqlDaoFactory.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("select distinct courier.courierId,"
                    + "\ncourier.courierName, courier.courierPhone, courier.transportId, courier.courierStatus"
                    + "\nfrom courier, transport, transportType"
                    + "\nwhere courier.transportId = transport.transportId"
                    + "\nand courier.courierStatus = \'" + courierStatus + "\'"
                    + "\nand transport.transportMaxWg >= " + maxWg
                    + "\nand transport.transportMaxWcm >= " + maxWcm
                    + "\nand transport.transportMaxHcm >= " + maxHcm
                    + "\nand transport.transportMaxLcm >= " + maxLcm
                    + "\nand transportType.transportTypeMaxDistanceM >= " + maxDistanceM
                    + "\norder by courier.courierId");

            while (resultSet.next()) {
                Courier courier = new Courier();
                courier.setId(resultSet.getInt("courierId"));
                courier.setName(resultSet.getString("courierName"));
                courier.setPhone(resultSet.getString("courierPhone"));
                courier.setTransport(transportDao.findTransport(resultSet.getInt("transportId")));
                courier.setStatus(resultSet.getString("courierStatus"));
                result.add(courier);
            }
        } catch (SQLException ex) {
            return null;
        }
        return result;
    }

    @Override
    public List<Tariff> selectTariffs(TransportType type) {
        ArrayList<Tariff> result = new ArrayList<>();
        try {
            Statement statement = MySqlDaoFactory.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("select * from tariff"
                    + "\nwhere transportTypeName = \'" + type.getName() + "\'"
                    + "\norder by tariffId desc");

            while (resultSet.next()) {
                Tariff tariff = new Tariff();
                tariff.setId(resultSet.getInt("tariffId"));
                tariff.setName(resultSet.getString("tariffName"));
                tariff.setTransportType(transportDao.findTransportType(resultSet.getString("transportTypeName")));
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
    public boolean insertOrder(Order order) {
        try {
            Connection con = MySqlDaoFactory.getConnection();
            con.setAutoCommit(false);

            PreparedStatement preparedStatement;
            preparedStatement = con.prepareStatement("insert into `order` values("
                    + "null, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
                    + " ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, order.getRoute().getId());
            try {
                if (order.getStartPointAdress().length() > 3) {
                    preparedStatement.setString(2, order.getStartPointAdress());
                } else {
                    preparedStatement.setString(2, "None");
                }
            } catch (Exception e) {
                preparedStatement.setString(2, "None");
            }
            try {
                if (order.getEndPointAdress().length() > 3) {
                    preparedStatement.setString(3, order.getEndPointAdress());
                } else {
                    preparedStatement.setString(3, "None");
                }
            } catch (Exception e) {
                preparedStatement.setString(3, "None");
            }
            try {
                if (courierDao.findCourier(order.getCourier().getId()).getStatus().equals(CourierStatus.FREE)) {
                    preparedStatement.setInt(6, order.getCourier().getId());
                } else {
                    preparedStatement.setInt(6, 1);
                }
            } catch (Exception e) {
                preparedStatement.setInt(6, 1);
            }
            try {
                if (order.getTariff().getId() > 0) {
                    preparedStatement.setInt(7, order.getTariff().getId());
                } else {
                    preparedStatement.setInt(7, 1);
                }
            } catch (Exception e) {
                preparedStatement.setInt(7, 1);
            }
            try {
                if (order.getCostUahC() > 0) {
                    preparedStatement.setInt(8, order.getCostUahC());
                } else {
                    preparedStatement.setInt(8, 0);
                }
            } catch (Exception e) {
                preparedStatement.setInt(8, 0);
            }
            try {
                if (order.getDoneDate().length() > 7) {
                    preparedStatement.setString(10, order.getDoneDate());
                } else {
                    preparedStatement.setString(10, "2000-01-01 00:00:00");
                }
            } catch (Exception e) {
                preparedStatement.setString(10, "2000-01-01 00:00:00");
            }
            try {
                if (order.getDelayMin() > 0) {
                    preparedStatement.setInt(11, order.getDelayMin());
                } else {
                    preparedStatement.setInt(11, 0);
                }
            } catch (Exception e) {
                preparedStatement.setInt(11, 0);
            }

            preparedStatement.setString(4, order.getClientName());
            preparedStatement.setString(5, order.getClientPhone());
            preparedStatement.setString(9, order.getOrderDate());
            preparedStatement.setString(12, order.getStatus());

            try {
                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                rs.next();
                order.setId(rs.getInt(1));

                PreparedStatement preparedStatement1 = con.prepareStatement("insert into orderContent values("
                        + "?, ?, ?, ?, ?, ?)");

                try {
                    if (order.getContent().size() > 0) {
                        for (OrderItem oi : order.getContent()) {
                            preparedStatement1.setInt(1, order.getId());
                            preparedStatement1.setString(2, oi.getItem());
                            preparedStatement1.setInt(3, oi.getWg());
                            preparedStatement1.setInt(4, oi.getWcm());
                            preparedStatement1.setInt(5, oi.getHcm());
                            preparedStatement1.setInt(6, oi.getLcm());
                            preparedStatement1.executeUpdate();
                        }
                    } else {
                        preparedStatement1.setInt(1, order.getId());
                        preparedStatement1.setString(2, "None");
                        preparedStatement1.setInt(3, 0);
                        preparedStatement1.setInt(4, 0);
                        preparedStatement1.setInt(5, 0);
                        preparedStatement1.setInt(6, 0);
                        preparedStatement1.executeUpdate();
                    }
                } catch (Exception e) {
                    throw e;
                }

                if (order.getStatus().equals("Виконується")) {
                    if (order.getContent().size() > 0) {
                        PreparedStatement preparedStatement2;
                        preparedStatement2 = MySqlDaoFactory.getConnection().prepareStatement(
                                "update courier set courierStatus = ? where courierId = ?");
                        preparedStatement2.setString(1, CourierStatus.IN_WORK);
                        preparedStatement2.setInt(2, order.getCourier().getId());

                        preparedStatement2.executeUpdate();
                    } else {
                        throw new IllegalArgumentException();
                    }
                }
                con.commit();
            } catch (Exception ex) {
                con.rollback();
                order.setId(0);
                throw ex;
            }

            con.setAutoCommit(true);
        } catch (SQLException ex) {
            return false;
        }
        return true;
    }

    @Override
    public boolean changeOrder(Order order) {
        try {
            Connection con = MySqlDaoFactory.getConnection();
            con.setAutoCommit(false);

            PreparedStatement preparedStatement;
            preparedStatement = con.prepareStatement("update `order` "
                    + "set routeId = ?, "
                    + "orderStartPointAdress = ?, "
                    + "orderEndPointAdress = ?, "
                    + "orderClientName = ?, "
                    + "orderClientPhone = ?, "
                    + "courierId = ?, "
                    + "tariffId = ?, "
                    + "orderCostUahC = ?, "
                    + "orderOrderDate = ?, "
                    + "orderDoneDate = ?, "
                    + "orderDelayMin = ?, "
                    + "orderStatus = ? "
                    + "where orderId = ?");

            preparedStatement.setInt(1, order.getRoute().getId());
            try {
                if (order.getStartPointAdress().length() > 3) {
                    preparedStatement.setString(2, order.getStartPointAdress());
                } else {
                    preparedStatement.setString(2, "None");
                }
            } catch (Exception e) {
                preparedStatement.setString(2, "None");
            }
            try {
                if (order.getEndPointAdress().length() > 3) {
                    preparedStatement.setString(3, order.getEndPointAdress());
                } else {
                    preparedStatement.setString(3, "None");
                }
            } catch (Exception e) {
                preparedStatement.setString(3, "None");
            }
            try {
                if (courierDao.findCourier(order.getCourier().getId()).getStatus().equals(CourierStatus.FREE)) {
                    preparedStatement.setInt(6, order.getCourier().getId());
                } else {
                    preparedStatement.setInt(6, 1);
                }
            } catch (Exception e) {
                preparedStatement.setInt(6, 1);
            }
            try {
                if (order.getTariff().getId() > 0) {
                    preparedStatement.setInt(7, order.getTariff().getId());
                } else {
                    preparedStatement.setInt(7, 1);
                }
            } catch (Exception e) {
                preparedStatement.setInt(7, 1);
            }
            try {
                if (order.getCostUahC() > 0) {
                    preparedStatement.setInt(8, order.getCostUahC());
                } else {
                    preparedStatement.setInt(8, 0);
                }
            } catch (Exception e) {
                preparedStatement.setInt(8, 0);
            }
            try {
                if (order.getDoneDate().length() > 7) {
                    preparedStatement.setString(10, order.getDoneDate());
                } else {
                    preparedStatement.setString(10, "2000-01-01 00:00:00");
                }
            } catch (Exception e) {
                preparedStatement.setString(10, "2000-01-01 00:00:00");
            }
            try {
                if (order.getDelayMin() > 0) {
                    preparedStatement.setInt(11, order.getDelayMin());
                } else {
                    preparedStatement.setInt(11, 0);
                }
            } catch (Exception e) {
                preparedStatement.setInt(11, 0);
            }

            preparedStatement.setString(4, order.getClientName());
            preparedStatement.setString(5, order.getClientPhone());
            preparedStatement.setString(9, order.getOrderDate());
            preparedStatement.setString(12, order.getStatus());
            preparedStatement.setInt(13, order.getId());

            try {
                preparedStatement.executeUpdate();

                Statement delStatement = con.createStatement();
                delStatement.execute("delete from orderContent where orderId = " + order.getId());

                PreparedStatement preparedStatementInsert = con.prepareStatement("insert into orderContent values("
                        + "?, ?, ?, ?, ?, ?)");
                try {
                    if (order.getContent().size() > 0) {
                        for (OrderItem oi : order.getContent()) {
                            preparedStatementInsert.setInt(1, order.getId());
                            preparedStatementInsert.setString(2, oi.getItem());
                            preparedStatementInsert.setInt(3, oi.getWg());
                            preparedStatementInsert.setInt(4, oi.getWcm());
                            preparedStatementInsert.setInt(5, oi.getHcm());
                            preparedStatementInsert.setInt(6, oi.getLcm());
                            preparedStatementInsert.executeUpdate();
                        }
                    } else {
                        preparedStatementInsert.setInt(1, order.getId());
                        preparedStatementInsert.setString(2, "None");
                        preparedStatementInsert.setInt(3, 0);
                        preparedStatementInsert.setInt(4, 0);
                        preparedStatementInsert.setInt(5, 0);
                        preparedStatementInsert.setInt(6, 0);
                        preparedStatementInsert.executeUpdate();
                    }
                } catch (Exception e) {
                    throw e;
                }

                if (order.getStatus().equals("Виконується")) {
                    if (order.getContent().size() > 0) {
                        PreparedStatement preparedStatement2;
                        preparedStatement2 = MySqlDaoFactory.getConnection().prepareStatement(
                                "update courier set courierStatus = ? where courierId = ?");
                        preparedStatement2.setString(1, CourierStatus.IN_WORK);
                        preparedStatement2.setInt(2, order.getCourier().getId());

                        preparedStatement2.executeUpdate();
                    } else {
                        throw new IllegalArgumentException();
                    }
                }
                con.commit();
            } catch (Exception ex) {
                con.rollback();
                order.setId(0);
                throw ex;
            }

            con.setAutoCommit(true);
        } catch (SQLException ex) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteOrder(Order order) {
        try {
            Connection con = MySqlDaoFactory.getConnection();
            con.setAutoCommit(false);
            try {
                Statement statement1 = con.createStatement();
                Statement statement2 = con.createStatement();
                statement1.executeUpdate("delete from orderContent where orderId = " + order.getId());
                statement2.executeUpdate("delete from `order` where orderId = " + order.getId());

                con.commit();
            } catch (SQLException ex) {
                con.rollback();
                return false;
            }
            con.setAutoCommit(true);
            return true;
        } catch (Exception ex1) {
            return false;
        }
    }

    @Override
    public Order findOrder(int Id) {
        Order result = new Order();
        try {
            Statement statement = MySqlDaoFactory.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("select * from `order` where orderId = " + Id);

            if (resultSet.next()) {
                result.setId(resultSet.getInt("orderId"));
                result.setStartPointAdress(resultSet.getString("orderStartPointAdress"));
                result.setEndPointAdress(resultSet.getString("orderEndPointAdress"));
                result.setRoute(routeDao.findRoute(resultSet.getInt("routeId")));
                result.setClientName(resultSet.getString("orderClientName"));
                result.setClientPhone(resultSet.getString("orderClientPhone"));
                result.setCourier(courierDao.findCourier(resultSet.getInt("courierId")));
                result.setTariff(tariffDao.findTariff(resultSet.getInt("tariffId")));
                result.setCostUahC(resultSet.getInt("orderCostUahC"));
                result.setOrderDate(resultSet.getString("orderOrderDate"));
                result.setDoneDate(resultSet.getString("orderDoneDate"));
                result.setDelayMin(resultSet.getInt("orderDelayMin"));
                result.setStatus(resultSet.getString("orderStatus"));

                ArrayList<OrderItem> content = new ArrayList<>();
                Statement statement1 = MySqlDaoFactory.getConnection().createStatement();
                ResultSet resultSet1 = statement1.executeQuery("select * from orderContent where orderId = " + Id);
                while (resultSet1.next()) {
                    if (resultSet1.getInt("orderId") == result.getId()) {
                        OrderItem i = new OrderItem();
                        i.setItem(resultSet1.getString("orderContentItem"));
                        i.setWg(resultSet1.getInt("orderContentWg"));
                        i.setWcm(resultSet1.getInt("orderContentWcm"));
                        i.setHcm(resultSet1.getInt("orderContentHcm"));
                        i.setLcm(resultSet1.getInt("orderContentLcm"));
                        content.add(i);
                    }
                }
                if (content.size() > 0) {
                    result.setContent(content);
                } else {
                    result.setContent(null);
                }
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
                order.setTariff(tariffDao.findTariff(resultSet.getInt("tariffId")));
                order.setCostUahC(resultSet.getInt("orderCostUahC"));
                order.setOrderDate(resultSet.getString("orderOrderDate"));
                order.setDoneDate(resultSet.getString("orderDoneDate"));
                order.setDelayMin(resultSet.getInt("orderDelayMin"));
                order.setStatus(resultSet.getString("orderStatus"));

                ArrayList<OrderItem> content = new ArrayList<>();
                Statement statement1 = MySqlDaoFactory.getConnection().createStatement();
                ResultSet resultSet1 = statement1.executeQuery("select * from orderContent order by orderId desc");
                while (resultSet1.next()) {
                    if (resultSet1.getInt("orderId") == order.getId()) {
                        OrderItem i = new OrderItem();
                        i.setItem(resultSet1.getString("orderContentItem"));
                        i.setWg(resultSet1.getInt("orderContentWg"));
                        i.setWcm(resultSet1.getInt("orderContentWcm"));
                        i.setHcm(resultSet1.getInt("orderContentHcm"));
                        i.setLcm(resultSet1.getInt("orderContentLcm"));
                        content.add(i);
                    }
                }
                if (content.size() > 0) {
                    order.setContent(content);
                } else {
                    order.setContent(null);
                }

                result.add(order);
            }
        } catch (SQLException ex) {
            return null;
        }
        return result;
    }
}
