package controller.dao;

import java.util.List;
import model.Courier;
import model.Order;
import model.Tariff;
import model.TransportType;

public interface OrderDao {

    public Courier findCourier(int Id);

    public boolean insertOrder(Order order);

    public boolean changeOrder(Order order);

    public boolean deleteOrder(Order order);

    public boolean updateOrder(Order order);

    public Order findOrder(int Id);

    public List<Order> selectOrderTO();

    public List<Courier> selectCouriers(String courierStatus, int maxWg,
            int maxWcm, int maxHcm, int maxLcm, int maxDistanceM);

    public List<Tariff> selectTariffs(TransportType type);
}
