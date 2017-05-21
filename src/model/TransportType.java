package model;

public class TransportType implements java.io.Serializable {

    private String name;
    private int kmPerH;
    private int maxDistanceM;

    public int getKmPerH() {
        return kmPerH;
    }

    public String getName() {
        return name;
    }

    public int getMaxDistanceM() {
        return maxDistanceM;
    }

    public void setMaxDistanceM(int maxDistanceM) {
        this.maxDistanceM = maxDistanceM;
    }

    public void setKmPerH(int kmPerH) {
        this.kmPerH = kmPerH;
    }

    public void setName(String name) {
        this.name = name;
    }
}
