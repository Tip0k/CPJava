package controller.dao;

public interface DaoFactory {

    public RouteDao getRouteDao();

    public TransportDao getTransportDao();

    public CourierDao getCourierDao();

    public TariffDao getTariffDao();

    public OrderDao getOrderDao();
}
