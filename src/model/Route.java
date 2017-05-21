package model;

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

    @Override
    public String toString() {
        return getId() + ": " + getStartPoint() + " → "
                + getEndPoint() + " ↔"
                + Tools.convertAndPowFromX(getDistanceM(), 3)
                + "км";
    }
}
