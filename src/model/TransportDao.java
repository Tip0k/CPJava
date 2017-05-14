/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;

/**
 *
 * @author PEOPLE
 */
public interface TransportDao {

    public boolean insertTransport(Transport transport);

    public boolean deleteTransport(Transport transport);

    public List<Transport> selectTransportTO();
    
    public List<TransportType> getTransportTypes();
}
