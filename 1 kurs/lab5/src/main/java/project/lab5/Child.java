package project.lab5;

import project.lab5.models.Coordinates;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "child")
public class Child {
    private String name;
    private int age;
    private Coordinates coordinates;

    public Child() {
    }

    public Child(String name, int age, Coordinates coordinates) {
        this.name = name;
        this.age = age;
        this.coordinates = coordinates;
    }

    public String getName() {
        return name;
    }

    @XmlElement(name = "name")
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    @XmlElement(name = "age")
    public void setAge(int age) {
        this.age = age;
    }

    @XmlElement(name = "coordinates")
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public String toString() {
        return "Child={name=" + name + ", " + "age=" + age + "}";
    }
}
