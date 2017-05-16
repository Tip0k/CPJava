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
public class Courier implements java.io.Serializable {

    private int Id;
    private String name;
    private String phone;
    private Transport transport;
    private String status;

    public int getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getStatus() {
        return status;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setName(String name) {
        this.name = name;
    }
}
