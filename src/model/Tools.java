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
    public static String getCurrentDirectory() {
        return new File(".").getAbsolutePath().substring(0, new File(".").getAbsolutePath().length() - 2);
    }
}
