/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.dao;

import java.util.List;
import model.Route;

/**
 *
 * @author PEOPLE
 */
public interface RouteDao {

    public boolean insertRoute(Route route);

    public boolean deleteRoute(Route route);

    public Route findRoute(int Id);

    public List<Route> selectRoutesTO();
}
