/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Andrey
 */
public class Car {

    private final String carID;
    private final String carName;

    public Car(String carID, String carName) {
        this.carID = carID;
        this.carName = carName;
    }

    public String getCarID() {
        return carID;
    }

    public String getCarName() {
        return carName;
    }
}
