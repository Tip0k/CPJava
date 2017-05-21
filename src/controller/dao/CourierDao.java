package controller.dao;

import model.Transport;
import java.util.List;
import model.Courier;
import model.Order;

public interface CourierDao {

    public boolean insertCourier(Courier courier);

    public boolean deleteCourier(Courier courier);

    public boolean updateCourier(Courier courier);

    public Courier findCourier(int Id);

    public List<Order> selectCourierOrders(Courier courier);

    public List<Courier> selectCourierTO();

    public List<Transport> selectFreeTransport();
}
