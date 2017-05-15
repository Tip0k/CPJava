package controller.dao;

/**
 *
 * @author PEOPLE
 */
public interface OrderDao {
    findOrder(Criteria c);
    deleteOrder(Order order);
    updateOrder(Order order);
}
