package controller;

import controller.dao.MySqlDaoFactory;
import controller.dao.OrderDao;
import java.util.ArrayList;
import model.CourierStatus;
import model.Order;
import model.OrderStatus;
import model.Tools;

public class OrderController {

    private final OrderDao orderDao;

    public OrderController() {
        orderDao = new MySqlDaoFactory().getOrderDao();
    }

    public ArrayList<Order> getOrders() {
        return new ArrayList(orderDao.selectOrderTO());
    }

    public void deleteOrder(int Id) {
        //перевірка, по статусу замовлення та по даті виконання, якщо більше 2 тижн то видал
        Order order = orderDao.findOrder(Id);
        if (!order.getStatus().equals(OrderStatus.DONE)) {
            throw new IllegalArgumentException("Неможливо видалити замовлення.");
        } else if (Tools.compareDates(Tools.convertDateTimeFromMySql(order.getOrderDate()),
                Tools.convertDateTimeFromMySql(order.getDoneDate())) > 14) {
            throw new IllegalArgumentException("Неможливо видалити замовлення.");
        }
        orderDao.deleteOrder(orderDao.findOrder(Id));
    }

    //////////
//    public ArrayList<Transport> getFreeTransport() {
//        return new ArrayList(courierDao.selectFreeTransport());
//    }
//
    public boolean updateOrder(Order order) {
        if (order.getStatus().equals(OrderStatus.IS_PERFORMED)) {
            if (orderDao.findCourier(order.getCourier().getId()).getStatus().equals(CourierStatus.REWORK)) {
                if (orderDao.updateOrder(order)) {
                    return true;
                }
            }
        } else if (orderDao.findCourier(order.getCourier().getId()).getStatus().equals(CourierStatus.FREE)) {
            if (orderDao.updateOrder(order)) {
                return true;
            }
        }
        return false;
    }
//
//    public void addCourier(Courier courier) {
//        courier.setStatus(CourierStatus.UNKNOWN);
//        courierDao.insertCourier(courier);
//    }
//
//    public void deleteCourier(int Id) {
//        if (courierDao.findCourier(Id).getStatus().equals(CourierStatus.IN_WORK)) {
//            throw new IllegalArgumentException("Неможливо видалити кур'єра.");
//        }
//        courierDao.deleteCourier(courierDao.findCourier(Id));
//    }
}
