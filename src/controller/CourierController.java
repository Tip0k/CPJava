package controller;

import controller.dao.CourierDao;
import controller.dao.MySqlDaoFactory;
import java.util.ArrayList;
import model.Courier;
import model.CourierStatus;
import model.Order;
import model.OrderStatus;
import model.Transport;

public class CourierController {

    private final CourierDao courierDao;

    public CourierController() {
        courierDao = new MySqlDaoFactory().getCourierDao();
    }

    public ArrayList<Courier> getCouriers() {
        return new ArrayList(courierDao.selectCourierTO());
    }

    public ArrayList<Transport> getFreeTransport() {
        return new ArrayList(courierDao.selectFreeTransport());
    }

    public boolean updateCourier(Courier courier) {
        if (courier.getStatus().equals(CourierStatus.REWORK) || courier.getStatus().equals(CourierStatus.FREE)) {
            for (Order o : courierDao.selectCourierOrders(courier)) {
                if (o.getStatus().equals(OrderStatus.IS_PERFORMED)) {
                    return false;
                }
            }
        } else if (courier.getStatus().equals(CourierStatus.IN_WORK)) {
            return false;
        }
        return courierDao.updateCourier(courier);
    }

    public void addCourier(Courier courier) {
        courier.setStatus(CourierStatus.UNKNOWN);
        courierDao.insertCourier(courier);
    }

    public void deleteCourier(int Id) {
        if (courierDao.findCourier(Id).getStatus().equals(CourierStatus.IN_WORK)) {
            throw new IllegalArgumentException("Неможливо видалити кур'єра.");
        }
        courierDao.deleteCourier(courierDao.findCourier(Id));
    }
}
