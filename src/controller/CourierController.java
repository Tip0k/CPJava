package controller;

import controller.dao.CourierDao;
import controller.dao.MySqlDaoFactory;
import java.util.ArrayList;
import model.Courier;
import model.CourierStatus;
import model.Transport;

public class CourierController {

    private final CourierDao courierDao;

    public CourierController() {
        courierDao = new MySqlDaoFactory().getCourierDao();
    }

    public ArrayList<Courier> getCouriers() {
        return new ArrayList(courierDao.selectCourierTO());
    }

    public ArrayList<Transport> getFreeTransport() {
        return new ArrayList(courierDao.selectFreeTransport());
    }

    ////
//    public String[] getFreeCarsID(String partOfID) {
//        try {
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("select cars.carsID from cars\n"
//                    + "left outer join couriers\n"
//                    + "on cars.carsID = couriers.carsID\n"
//                    + "where couriers.carsID is null and cars.carsID like \'%" + partOfID + "%\'\n"
//                    + "order by cars.carsID desc");
//
//            int n = 0;
//            while (resultSet.next()) {
//                n++;
//            }
//            resultSet.first();
//            resultSet.previous();
//            String[] result = new String[n];
//            n--;
//            while (resultSet.next()) {
//                result[n--] = resultSet.getString("carsID");
//            }
//            if (result.length > 0) {
//                return result;
//            } else {
//                return new String[]{"none"};
//            }
//        } catch (Exception ex) {
//            return null;
//        }
//    }
//
//    public String[] getFreeCarsName(String partOfName) {
//        try {
//            if (partOfName.contains("#")) {
//                partOfName = partOfName.substring(0, partOfName.indexOf("#") - 1);
//            }
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("select cars.carsName, cars.carsID from cars\n"
//                    + "left outer join couriers\n"
//                    + "on cars.carsID = couriers.carsID\n"
//                    + "where couriers.carsID is null and cars.carsName like \'%" + partOfName + "%\'\n"
//                    + "order by cars.carsName desc");
//
//            int n = 0;
//            while (resultSet.next()) {
//                n++;
//            }
//            resultSet.first();
//            resultSet.previous();
//            String[] result = new String[n];
//            n--;
//            while (resultSet.next()) {
//                result[n--] = resultSet.getString("carsName") + " #" + resultSet.getString("carsID");
//            }
//            if (result.length > 0) {
//                return result;
//            } else {
//                return new String[]{"none"};
//            }
//        } catch (Exception ex) {
//            return null;
//        }
//    }
//
//    public String getItemForID(String ID) {
//        try {
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("select cars.carsName from cars\n"
//                    + "left outer join couriers\n"
//                    + "on cars.carsID = couriers.carsID\n"
//                    + "where couriers.carsID is null and cars.carsID = " + ID + "\n");
//            resultSet.next();
//            return resultSet.getString("carsName") + " #" + ID;
//        } catch (Exception ex) {
//            return null;
//        }
//    }
//
//    public String getItemForName(String name) {
//        try {
//            if (name.contains("#")) {
//                return name.substring(name.indexOf("#") + 1, name.length());
//            }
//
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("select cars.carsID from cars\n"
//                    + "left outer join couriers\n"
//                    + "on cars.carsID = couriers.carsID\n"
//                    + "where couriers.carsID is null and cars.carsName = " + name + "\n");
//            resultSet.next();
//            return resultSet.getString("carsID");
//        } catch (Exception ex) {
//            return null;
//        }
//    }
//
    public void addCourier(Courier courier) {
        courier.setStatus(CourierStatus.UNKNOWN);
        courierDao.insertCourier(courier);
    }

    public void deleteCourier(int Id) {
        courierDao.deleteCourier(courierDao.findCourier(Id));
    }
}
