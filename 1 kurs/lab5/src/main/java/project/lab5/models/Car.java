package project.lab5.models;

import javax.xml.bind.annotation.XmlElement;

public class Car {
    private String name; //Поле не может быть null
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

