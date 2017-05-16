package controller;

import java.util.ArrayList;
import model.Route;
import controller.dao.MySqlDaoFactory;
import controller.dao.RouteDao;

public class RouteController {

    private final RouteDao routeDao;

    public RouteController() {
        routeDao = new MySqlDaoFactory().getRouteDao();
    }

    public ArrayList<Route> getRoutes() {
        return new ArrayList<Route>(routeDao.selectRoutesTO());
    }

    public void addRoute(Route route) {
        routeDao.insertRoute(route);
    }

    public void deleteRoute(int Id) {
        routeDao.deleteRoute(routeDao.findRoute(Id));
    }
}
