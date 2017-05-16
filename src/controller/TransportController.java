package controller;

import controller.dao.MySqlDaoFactory;
import controller.dao.TransportDao;
import java.util.ArrayList;
import model.Transport;
import model.TransportType;

public class TransportController {

    private final TransportDao transportDao;

    public TransportController() {
        transportDao = new MySqlDaoFactory().getTransportDao();
    }

    public TransportType getTransportType(String name) {
        return transportDao.findTransportType(name);
    }
    
    public ArrayList<TransportType> getTransportTypes() {
        return new ArrayList<TransportType>(transportDao.selectTransportTypes());
    }

    public ArrayList<Transport> getTransports() {
        return new ArrayList<Transport>(transportDao.selectTransportTO());
    }

    public void addTransport(Transport transport) {
        transportDao.insertTransport(transport);
    }

    public void deleteTransport(int Id) {
        transportDao.deleteTransport(transportDao.findTransport(Id));
    }
}
