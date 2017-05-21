package model;

public class Tariff implements java.io.Serializable {

    private int Id;
    private String name;
    private TransportType transportType;
    private int uahCPerKm;
    private int uahCPerPoint;
    private int uahCAdditionalCosts;

    public int getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
    }

    public void setUahCAdditionalCosts(int uahCAdditionalCosts) {
        this.uahCAdditionalCosts = uahCAdditionalCosts;
    }

    public void setUahCPerKm(int uahCPerKm) {
        this.uahCPerKm = uahCPerKm;
    }

    public void setUahCPerPoint(int uahCPerPoint) {
        this.uahCPerPoint = uahCPerPoint;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public int getUahCPerKm() {
        return uahCPerKm;
    }

    public int getUahCAdditionalCosts() {
        return uahCAdditionalCosts;
    }

    public int getUahCPerPoint() {
        return uahCPerPoint;
    }

    @Override
    public String toString() {
        return getId() + ": " + getName() + ", " + getTransportType().getName()
                + ", {" + Tools.convertAndPowFromX(getUahCPerKm(), 2)
                + "/" + Tools.convertAndPowFromX(getUahCPerPoint(), 2)
                + "/" + Tools.convertAndPowFromX(getUahCAdditionalCosts(), 2)
                + "}uah";
    }
}
