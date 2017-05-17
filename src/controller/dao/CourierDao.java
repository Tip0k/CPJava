/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.dao;

import model.Transport;
import java.util.List;
import model.Courier;
import model.Order;

/**
 *
 * @author PEOPLE
 */
public interface CourierDao {

    public boolean insertCourier(Courier courier);

    public boolean deleteCourier(Courier courier);

    public boolean updateCourier(Courier courier);

    public Courier findCourier(int Id);
    
    public List<Order> selectCourierOrders(Courier courier);

    public List<Courier> selectCourierTO();

    public List<Transport> selectFreeTransport();
}
