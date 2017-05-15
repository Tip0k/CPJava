/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.dao;

import model.TransportType;

/**
 *
 * @author PEOPLE
 */
public class Transport implements java.io.Serializable {

    private int Id;
    private String name;
    private TransportType type;
    private int maxWg;
    private int maxWcm;
    private int maxHcm;
    private int maxLcm;

    public int getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public TransportType getType() {
        return type;
    }

    public int getMaxWg() {
        return maxWg;
    }

    public int getMaxHcm() {
        return maxHcm;
    }

    public int getMaxLcm() {
        return maxLcm;
    }

    public int getMaxWcm() {
        return maxWcm;
    }

    public void setMaxHcm(int maxHcm) {
        this.maxHcm = maxHcm;
    }

    public void setMaxLcm(int maxLcm) {
        this.maxLcm = maxLcm;
    }

    public void setMaxWcm(int maxWcm) {
        this.maxWcm = maxWcm;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public void setMaxWg(int maxWg) {
        this.maxWg = maxWg;
    }

    public void setType(TransportType type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }
}
