package controller.dao;

import java.util.List;
import model.Courier;
import model.Order;

/**
 *
 * @author PEOPLE
 */
public interface OrderDao {

    //public boolean insertCourier(Courier courier);
    public Courier findCourier(int Id);

    public boolean deleteOrder(Order order);

    public boolean updateOrder(Order order);

    public Order findOrder(int Id);

    public List<Order> selectOrderTO();

    //public List<Transport> selectFreeTransport();
}
