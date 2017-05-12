/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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

    public static int convertAndPowToX(String string, int pow) throws IllegalArgumentException {//кіло-дабл в інт
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
            if(result > 0) {
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

    public static void main(String[] args) {
        System.out.println(convertAndPowToX("4", 3));
        System.out.println(convertAndPowFromX(convertAndPowToX("4", 3), 3));
        System.out.println(convertAndPowToX("4,5", 3));
        System.out.println(convertAndPowFromX(convertAndPowToX("4,5", 3), 3));
        System.out.println(convertAndPowToX("4,12345", 3));
        System.out.println(convertAndPowFromX(convertAndPowToX("4,12345", 3), 3));
        System.out.println(convertAndPowToX("4,01", 2));
        System.out.println(convertAndPowFromX(convertAndPowToX("4,01", 2), 2));
    }
}
