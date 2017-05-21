package controller.dao;

import model.Transport;
import java.util.List;
import model.TransportType;

public interface TransportDao {

    public boolean insertTransport(Transport transport);

    public boolean deleteTransport(Transport transport);

    public TransportType findTransportType(String name);

    public List<TransportType> selectTransportTypes();

    public Transport findTransport(int Id);

    public List<Transport> selectTransportTO();
}
