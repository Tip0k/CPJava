package model;

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

    @Override
    public String toString() {
        return getId() + ": " + getName() + ", " + getPhone() + ", " + getTransport().getName();
    }
}
