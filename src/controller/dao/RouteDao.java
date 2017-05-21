package controller.dao;

import java.util.List;
import model.Route;

public interface RouteDao {

    public boolean insertRoute(Route route);

    public boolean deleteRoute(Route route);

    public Route findRoute(int Id);

    public List<Route> selectRoutesTO();
}
