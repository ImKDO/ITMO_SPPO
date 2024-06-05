package project.lab5.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import javax.xml.bind.annotation.XmlElement;

@JacksonXmlRootElement(localName = "car")
public class Car {
    @JacksonXmlProperty(localName = "name")
    private String name; //Поле не может быть null
    @JacksonXmlProperty(localName = "cool")
    private boolean cool;

    public Car(String name, boolean cool) {
        this.name = name;
        this.cool = cool;
    }
    public Car() {

    }

    @Override
    public String toString() {
        return name + ";" + cool;
    }


    @XmlElement(name = "name")
    public void setName(String name) {
        this.name = name;
    }
    @XmlElement(name = "cool")
    public void setCool(boolean cool) {
        this.cool = cool;
    }

    public String getName() {
        return name;
    }

    public boolean getCool() {
        return cool;
    }
}

