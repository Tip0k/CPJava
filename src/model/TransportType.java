/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author PEOPLE
 */
public class TransportType implements java.io.Serializable {

    private String name;
    private int kmPerH;

    public int getKmPerH() {
        return kmPerH;
    }

    public String getName() {
        return name;
    }

    public void setKmPerH(int kmPerH) {
        this.kmPerH = kmPerH;
    }

    public void setName(String name) {
        this.name = name;
    }        
}
