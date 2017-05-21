package controller.dao;

import java.util.List;
import model.Tariff;
import model.TransportType;

public interface TariffDao {

    public boolean insertTariff(Tariff tariff);

    public boolean deleteTariff(Tariff tariff);

    public Tariff findTariff(int Id);

    public List<Tariff> selectTariffTO();

    public TransportType findTransportType(String name);

    public List<TransportType> selectTransportTypes();
}
