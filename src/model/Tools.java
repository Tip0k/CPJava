package model;

import java.io.File;
import java.util.Calendar;

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

    public static String convertDateTimeToMySql(String dateTime) {
        if (dateTime.length() < 16) {
            return " ";
        }
        return dateTime.substring(6, 10)
                + "-" + dateTime.substring(3, 5)
                + "-" + dateTime.substring(0, 2) + " "
                + dateTime.substring(11, 16) + ":00";
    }

    /**
     *
     * @param date1
     * @param date2
     * @return 0 - fail or equals, int result where date1 > date2 on result days
     */
    public static long compareDatesInMinutes(String date1, String date2) {
        long result = 0;//19.04.2017 17:00:4        

        int day1 = Integer.parseInt(date1.substring(0, 2));
        int month1 = Integer.parseInt(date1.substring(3, 5));
        int year1 = Integer.parseInt(date1.substring(6, 10));
        int hour1 = Integer.parseInt(date1.substring(11, 13));
        int min1 = Integer.parseInt(date1.substring(14, 16));

        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(year1, month1, day1, hour1, min1);

        int day2 = Integer.parseInt(date2.substring(0, 2));
        int month2 = Integer.parseInt(date2.substring(3, 5));
        int year2 = Integer.parseInt(date2.substring(6, 10));
        int hour2 = Integer.parseInt(date2.substring(11, 13));
        int min2 = Integer.parseInt(date2.substring(14, 16));

        Calendar c1 = Calendar.getInstance();
        c1.clear();
        c1.set(year2, month2, day2, hour2, min2);

        int sign = c.compareTo(c1);
        if (sign < 0) {
            sign = -1;
        } else {
            sign = 1;
        }

        result = ((Math.abs(c.getTimeInMillis() - c1.getTimeInMillis()) / 1000) / 60) * sign;
        return result;
    }

    public static String getDateTime(Calendar calendar) {
        String m = calendar.get(Calendar.MONTH) + "";
        if (m.length() < 2) {
            m = "0" + m;
        }
        String d = calendar.get(Calendar.DAY_OF_MONTH) + "";
        if (d.length() < 2) {
            d = "0" + d;
        }
        String h = calendar.get(Calendar.HOUR_OF_DAY) + "";
        if (h.length() < 2) {
            h = "0" + h;
        }
        String min = calendar.get(Calendar.MINUTE) + "";
        if (min.length() < 2) {
            min = "0" + min;
        }

        return Tools.convertDateTimeFromMySql(
                calendar.get(Calendar.YEAR)
                + "-" + m
                + "-" + d
                + " " + h
                + ":" + min)
                + ":" + calendar.get(Calendar.SECOND);
    }
}
