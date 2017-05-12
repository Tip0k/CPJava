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

    public static final int MAX_INT = 999999;

    public static String getCurrentDirectory() {
        return new File(".").getAbsolutePath().substring(0, new File(".").getAbsolutePath().length() - 2);
    }

    public static float stringToFloat(String floatS) throws IllegalArgumentException {
        try {
            floatS.replaceAll(",", ".");
            int intPart = Integer.parseInt(floatS.substring(0, floatS.indexOf(".")));
            if (intPart > MAX_INT) {
                throw new IllegalArgumentException();
            }
            String floatPart = (Integer.parseInt(floatS.substring(floatS.indexOf(".") + 1, floatS.length())) + "").substring(0, 2);
            return Float.parseFloat(intPart + "." + floatPart);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    public static int floatToInt(float floatF, int pow) throws IllegalArgumentException {
        try {
            floatF *= pow;
            return Math.round(floatF);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println(stringToFloat("0.123456"));
    }
}
