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
public class Order {
    private int Id;
    private Route route;
    private String startPointAdress;
    private String endPointAdress;
    private String clientName;
    private String clientPhone;
    private Courier courier;
    private int costUahC;
    private String orderDate;
    private String doneDate;
    private int delayMin;
    private String status;

    public void setId(int Id) {
        this.Id = Id;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public void setStartPointAdress(String startPointAdress) {
        this.startPointAdress = startPointAdress;
    }

    public void setEndPointAdress(String endPointAdress) {
        this.endPointAdress = endPointAdress;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    public void setCostUahC(int costUahC) {
        this.costUahC = costUahC;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setDoneDate(String doneDate) {
        this.doneDate = doneDate;
    }

    public void setDelayMin(int delayMin) {
        this.delayMin = delayMin;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return Id;
    }

    public Route getRoute() {
        return route;
    }

    public String getStartPointAdress() {
        return startPointAdress;
    }

    public String getEndPointAdress() {
        return endPointAdress;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public Courier getCourier() {
        return courier;
    }

    public int getCostUahC() {
        return costUahC;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getDoneDate() {
        return doneDate;
    }

    public int getDelayMin() {
        return delayMin;
    }

    public String getStatus() {
        return status;
    }
}
