package controller;

import controller.dao.MySqlDaoFactory;
import controller.dao.OrderDao;
import controller.dao.RouteDao;
import java.util.ArrayList;
import java.util.Calendar;
import model.Courier;
import model.CourierStatus;
import model.Order;
import model.OrderItem;
import model.OrderStatus;
import model.Route;
import model.Tariff;
import model.Tools;
import model.TransportType;

public class OrderController {

    private final OrderDao orderDao;
    private final RouteDao routeDao;

    public OrderController() {
        orderDao = new MySqlDaoFactory().getOrderDao();
        routeDao = new MySqlDaoFactory().getRouteDao();
    }

    public ArrayList<Route> getRoutes() {
        return new ArrayList(routeDao.selectRoutesTO());
    }

    public ArrayList<Courier> getCouriers(Order order) {
        try {
            String courierStatus = CourierStatus.FREE;
            int maxDistanceM = order.getRoute().getDistanceM();
            int maxWg, maxWcm, maxHcm, maxLcm;
            maxWg = maxWcm = maxHcm = maxLcm = 0;

            for (OrderItem i : order.getContent()) {
                maxWg += i.getWg();
                maxWcm += i.getWcm();
                maxHcm += i.getHcm();
                maxLcm = i.getLcm();
            }
            return new ArrayList(orderDao.selectCouriers(courierStatus, maxWg, maxWcm, maxHcm, maxLcm, maxDistanceM));
        } catch (Exception ex) {
            return null;
        }
    }

    public ArrayList<Tariff> getTariffs(Order order) {
        try {
            TransportType tt = order.getCourier().getTransport().getType();
            return new ArrayList(orderDao.selectTariffs(tt));
        } catch (Exception ex) {
            return null;
        }
    }

    public ArrayList<Order> getOrders() {
        return new ArrayList(orderDao.selectOrderTO());
    }

    public Order getOrder(int Id) {
        return orderDao.findOrder(Id);
    }

    public void deleteOrder(int Id) {
        Order order = orderDao.findOrder(Id);
        if (order.getStatus().equals(OrderStatus.UNKNOWN)) {
            orderDao.deleteOrder(orderDao.findOrder(Id));
            return;
        }
        if (!order.getStatus().equals(OrderStatus.DONE)) {
            throw new IllegalArgumentException("Неможливо видалити замовлення.");
        } else if (Tools.compareDatesInMinutes(Tools.convertDateTimeFromMySql(order.getOrderDate()),
                Tools.convertDateTimeFromMySql(order.getDoneDate())) < 14 * 24 * 60) {
            throw new IllegalArgumentException("Неможливо видалити замовлення.");
        }
        orderDao.deleteOrder(orderDao.findOrder(Id));
    }

    public boolean updateOrder(Order order) {
        if (order.getStatus().equals(OrderStatus.IS_PERFORMED)) {
            if (orderDao.findCourier(order.getCourier().getId()).getStatus().equals(CourierStatus.REWORK)) {
                if (orderDao.updateOrder(order)) {
                    return true;
                }
            }
        } else {
            String prevDoneDate = order.getDoneDate();
            order.setDoneDate(Tools.convertDateTimeToMySql(Tools.getDateTime(Calendar.getInstance())));
            order.setDelayMin((int) Tools.compareDatesInMinutes(
                    Tools.convertDateTimeFromMySql(order.getDoneDate()),
                    Tools.convertDateTimeFromMySql(prevDoneDate)
            ));
            if (orderDao.updateOrder(order)) {
                Courier c = orderDao.findCourier(order.getCourier().getId());
                c.setStatus(CourierStatus.FREE);
                new MySqlDaoFactory().getCourierDao().updateCourier(c);

                return true;
            }
        }
        return false;
    }

    public boolean addOrder(Order order) {
        if (order.getStatus().equals(OrderStatus.IS_PERFORMED)) {
            if (order.getCourier() == null) {
                return false;
            } else if (order.getContent().size() < 1) {
                return false;
            } else {
                return orderDao.insertOrder(order);
            }
        } else if (order.getStatus().equals(OrderStatus.UNKNOWN)) {
            if (order.getRoute() == null) {
                return false;
            } else if (order.getClientName().length() < 3 || order.getClientPhone().length() < 3) {
                return false;
            } else if (order.getStatus().length() < 1) {
                return false;
            } else if (order.getOrderDate().length() < 1) {
                return false;
            }
            return orderDao.insertOrder(order);
        }
        return false;
    }

    public boolean changeOrder(Order order) {
        if (order.getStatus().equals(OrderStatus.IS_PERFORMED)) {
            return orderDao.changeOrder(order);
        } else if (order.getStatus().equals(OrderStatus.UNKNOWN)) {
            return orderDao.changeOrder(order);
        }
        return false;
    }
}
