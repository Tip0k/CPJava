/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;

/**
 *
 * @author PEOPLE
 */
public interface DaoFactory {

    public RouteDao getRouteDao();

    public TransportDao getTransportDao();

    public CourierDao getCourierDao();

    public TariffDao getTariffDao();

    public OrderDao getOrderDao();
}
