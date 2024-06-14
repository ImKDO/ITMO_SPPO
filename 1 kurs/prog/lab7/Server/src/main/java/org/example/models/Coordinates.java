package org.example.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

//@XmlRootElement(name = "coordinates")
@JacksonXmlRootElement(localName = "coordinates")
public class Coordinates implements Serializable {
    @JacksonXmlProperty(localName = "x")
    private long x; //Значение поля должно быть больше -737
    @JacksonXmlProperty(localName = "y")
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
