package controller;

import controller.dao.MySqlDaoFactory;
import controller.dao.TariffDao;
import java.util.ArrayList;
import model.Tariff;
import model.TransportType;

public class TariffController {

    private final TariffDao tariffDao;

    public TariffController() {
        tariffDao = new MySqlDaoFactory().getTariffDao();
    }

    public ArrayList<Tariff> getTariffs() {
        return new ArrayList(tariffDao.selectTariffTO());
    }

    public ArrayList<TransportType> getTransportTypes() {
        return new ArrayList(tariffDao.selectTransportTypes());
    }

    public void addTariff(Tariff tariff) {
        tariffDao.insertTariff(tariff);
    }

    public void deleteTariff(int Id) {
        tariffDao.deleteTariff(tariffDao.findTariff(Id));
    }

    public TransportType getTransportType(String name) {
        return tariffDao.findTransportType(name);
    }
}
