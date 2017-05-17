/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.dao.TransportDao;
import controller.dao.MySqlDaoFactory;
import controller.dao.DaoFactory;
import java.io.File;

/**
 *
 * @author PEOPLE
 */
public class Tools {

    public static final int MAX_INT = 999_999_999;

    public static String getCurrentDirectory() {
        return new File(".").getAbsolutePath().substring(0, new File(".").getAbsolutePath().length() - 2);
    }

    public static int convertAndPowToX(String string, int pow) throws IllegalArgumentException {
        try {
            int intPart;
            String floatPart = "";

            string = string.replace(',', '.');
            if (!string.contains(".")) {
                intPart = Integer.parseInt(string);
                for (int i = 0; i < pow; i++) {
                    floatPart += "0";
                }
            } else {
                intPart = Integer.parseInt(string.substring(0, string.indexOf(".")));
                floatPart = string.substring(string.indexOf(".") + 1, string.length());
                int length = floatPart.length();
                if (length < pow) {
                    for (int i = 0; i < pow - length; i++) {
                        floatPart += "0";
                    }
                } else if (length > pow) {
                    floatPart = floatPart.substring(0, pow);
                }
            }
            if (intPart > (MAX_INT / (int) Math.pow(10.0d, pow))) {
                throw new IllegalArgumentException();
            }
            int result = Integer.parseInt(intPart + "" + floatPart);
            if (result > 0) {
                return result;
            } else {
                throw new IllegalArgumentException();
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    public static String convertAndPowFromX(int integer, int pow) throws IllegalArgumentException {
        try {
            return (integer / Math.pow(10.0d, pow)) + "";
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    public static String convertFromMinutes(int min) {
        return min / 60 + "h " + min % 60 + "m";
    }

    public static String convertDateTimeFromMySql(String mySqlDateTime) {
        return mySqlDateTime.substring(8, 10)
                + "." + mySqlDateTime.substring(5, 7)
                + "." + mySqlDateTime.substring(0, 4) + " "
                + mySqlDateTime.substring(11, 16);
    }
    
    /**
     * 
     * @param date1
     * @param date2
     * @return 0 - fail or equals, int result where date1 > date2 on result days
     */
    public static int compareDates(String date1, String date2) {
        try {
            int result = 0;
            int sign = 1;
            
            int day1 = Integer.parseInt(date1.substring(0, 2));
            int month1 = Integer.parseInt(date1.substring(3, 5));
            int year1 = Integer.parseInt(date1.substring(6, 10));
            
            int day2 = Integer.parseInt(date1.substring(0, 2));
            int month2 = Integer.parseInt(date1.substring(3, 5));
            int year2 = Integer.parseInt(date1.substring(6, 10));
            
            if(year1 < year2) {
                sign = -1;
            } else if(year1 == year2){
                if(month1 < month2) {
                    sign = -1;
                } else if(month1 == month2) {
                    if(day1 < day2) {
                        sign = -1;
                    } else if(day1 == day2){
                        return 0;
                    }
                }
            }
            
            result = Math.abs(year1 - year2) * 365;
            result += Math.abs(month1 - month2) * 31;
            result += Math.abs(day1 - day2);
            return result * sign;
        } catch(Exception ex) {
            return 0;
        }
    }

    public static void main(String[] args) {
        System.out.println(convertDateTimeFromMySql("2000-09-05 13:28:00"));

        System.out.println(convertAndPowToX("4", 3));
        System.out.println(convertAndPowFromX(convertAndPowToX("4", 3), 3));
        System.out.println(convertAndPowToX("4,5", 3));
        System.out.println(convertAndPowFromX(convertAndPowToX("4,5", 3), 3));
        System.out.println(convertAndPowToX("4,12345", 3));
        System.out.println(convertAndPowFromX(convertAndPowToX("4,12345", 3), 3));
        System.out.println(convertAndPowToX("4,01", 2));
        System.out.println(convertAndPowFromX(convertAndPowToX("4,01", 2), 2));

        DaoFactory df = new MySqlDaoFactory();
        TransportDao td = df.getTransportDao();
        for (int i = 0; i < 100; i++) {
            Transport c = new Transport();
            c.setName("df");
            c.setMaxHcm(1);
            c.setMaxLcm(11);
            c.setMaxWcm(111);
            c.setMaxWg(1111);
            TransportType tt = new TransportType();
            tt.setName("Вело");
            c.setType(tt);
            td.insertTransport(c);
        }
        for (Transport t : td.selectTransportTO()) {
            System.out.println(t.getId());
        }

    }

    /*public int insertCustomer(...) {
    // Реализовать здесь операцию добавления клиента.
    // Возвратить номер созданного клиента
    // или -1 при ошибке
  }

  public boolean deleteCustomer(...) {
    // Реализовать здесь операцию удаления клиента.
    // Возвратить true при успешном выполнении, false при ошибке
  }

  public Customer findCustomer(...) {
    // Реализовать здесь операцию поиска клиента, используя
    // предоставленные значения аргументов в качестве критерия поиска.
    // Возвратить объект Transfer Object при успешном поиске,
    // null или ошибку, если клиент не найден.
  }

  public boolean updateCustomer(...) {
    // Реализовать здесь операцию обновления записи,
    // используя данные из customerData Transfer Object
    // Возвратить true при успешном выполнении, false при ошибке
  }

  public RowSet selectCustomersRS(...) {
    // Реализовать здесь операцию выбора клиентов,
    // используя предоставленный критерий.
    // Возвратить RowSet.
  }

  public Collection selectCustomersTO(...) {
    // Реализовать здесь операцию выбора клиентов,
    // используя предоставленный критерий.
    // В качестве альтернативы, реализовать возврат
    // коллекции объектов Transfer Object.
  }*/
}
