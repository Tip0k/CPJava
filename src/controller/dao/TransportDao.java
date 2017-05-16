/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.dao;

import model.Transport;
import java.util.List;
import model.TransportType;

/**
 *
 * @author PEOPLE
 */
public interface TransportDao {

    public boolean insertTransport(Transport transport);

    public boolean deleteTransport(Transport transport);

    public TransportType findTransportType(String name);

    public List<TransportType> selectTransportTypes();

    public Transport findTransport(int Id);

    public List<Transport> selectTransportTO();
}
