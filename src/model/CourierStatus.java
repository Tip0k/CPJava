/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author PEOPLE
 */
public class CourierStatus {

    public static final String FREE = "Вільний";
    public static final String IN_WORK = "Зайнятий";
    public static final String UNKNOWN = "Невідомо";
    public static final String REWORK = "Переробляє";
    //...
    
    public static ArrayList<String> getAllStatuses() {
        ArrayList<String> s = new ArrayList<>();
        
        s.add(FREE);
        s.add(IN_WORK);
        s.add(REWORK);
        
        return  s;
    }
}
