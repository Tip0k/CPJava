/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.dao;

import java.util.List;
import model.Tariff;
import model.TransportType;

/**
 *
 * @author PEOPLE
 */
public interface TariffDao {

    public boolean insertTariff(Tariff tariff);

    public boolean deleteTariff(Tariff tariff);
    
    public Tariff findTariff(int Id);

    public List<Tariff> selectTariffTO();
    
    public TransportType findTransportType(String name);

    public List<TransportType> selectTransportTypes();
}
