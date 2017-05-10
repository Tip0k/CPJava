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
public class Locality {

    private String region;
    private String locality;
    
    public Locality(String region, String locality) {
        this.region = region;
        this.locality = locality;
    }
    
    public Locality(String localityDB) {        
        this.region = localityDB.split(",")[0];
        this.locality = localityDB.substring(region.length() + 2);
    }
    
    public String getRegion() {
        return this.region;
    }
    
    public String getLocality() {
        return this.locality;
    }
    
    @Override
    public String toString() {
        return region + ", " + locality;
    }
}
