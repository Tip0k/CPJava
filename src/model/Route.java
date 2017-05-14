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
public class Route implements java.io.Serializable {

    private int Id;
    private String startPoint;
    private String endPoint;
    private int distanceM;

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public void setDistanceM(int distanceM) {
        this.distanceM = distanceM;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public int getDistanceM() {
        return distanceM;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public int getId() {
        return Id;
    }
}
