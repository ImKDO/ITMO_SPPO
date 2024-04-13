package project.lab5.models;

import javax.xml.bind.annotation.XmlElement;

public class Coordinates {
    private long x; //Значение поля должно быть больше -737
    private double y;

    public Coordinates(long x, double y) {
        this.x = x;
        this.y = y;
    }
    public Coordinates() {
    }

    public long getX() {
        return x;
    }
    @XmlElement (name = "x")
    public void setX(long x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }
    @XmlElement (name = "y")
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return x + ";" + y;
    }
}
