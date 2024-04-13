package project.lab5.models;


import project.lab5.examinationData.Element;
import project.lab5.examinationData.Validation;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "HumanBeing")
public class HumanBeing extends Element implements Validation {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Boolean realHero; //Поле не может быть null
    private Boolean hasToothpick; //Поле может быть null
    private Double impactSpeed; //Максимальное значение поля: 118, Поле может быть null
    private String soundtrackName; //Поле не может быть null
    private WeaponType weaponType; //Поле может быть null
    private Mood mood; //Поле не может быть null
    private Car car; //Поле не может быть null

    public HumanBeing() {
    }

    public HumanBeing(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
//java.time.LocalDateTime creationDate
//    public HumanBeing(Integer id, String name, Coordinates coordinates, Boolean realHero, Boolean hasToothpick, Double impactSpeed, java.time.LocalDateTime creationDate, String soundtrackName, WeaponType weaponType, Mood mood, Car car) {
//        this.id = id;
//        this.name = name;
//        this.coordinates = coordinates;
//        this.realHero = realHero;
//        this.hasToothpick = hasToothpick;
//        this.impactSpeed = impactSpeed;
//        this.creationDate = creationDate;
//        this.soundtrackName = soundtrackName;
//        this.weaponType = weaponType;
//        this.mood = mood;
//        this.car = car;
//    }
    public HumanBeing(Integer id, String name, Coordinates coordinates, Boolean realHero, Boolean hasToothpick, Double impactSpeed, String soundtrackName, WeaponType weaponType, Mood mood, Car car) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.realHero = realHero;
        this.hasToothpick = hasToothpick;
        this.impactSpeed = impactSpeed;
        this.soundtrackName = soundtrackName;
        this.weaponType = weaponType;
        this.mood = mood;
        this.car = car;
    }



    @Override
    public String toString() {
        return "HumanBeing{\"id\": " + id + ", " +
                "\"name\": \"" + name + "\", " +
                "\"coordinates\": \"" + coordinates + "\", " +
//                "\"creationDate:\"" + creationDate.format(DateTimeFormatter.ISO_DATE_TIME) + ", " +
                "\"realHero\": \"" + realHero + "\", " +
                "\"hasToothpick\": \"" + hasToothpick + "\", " +
                "\"impactSpeed\": \"" + impactSpeed + "\", " +
                "\"soundtrackName\": \"" + soundtrackName + "\", " +
                "\"weaponType\": " + (weaponType == null ? "null" : "\"" + weaponType + "\"") + "\", " +
                "\"mood\": \"" + mood + "\", " +
                "\"car\": \"" + car + "}";
    }

    public boolean validate() {
        if ((id == null) | (id <= 0) && (name == null) && coordinates == null && car == null && mood == null && realHero == null && impactSpeed > 118 && soundtrackName == null) {
            return false;
        }
//        if () return false;
//        if () return false;
//        if () return false;
////        if (creationDate == null) return false;
//        if () return false;
//        if () return false;
//        if () return false;
//        if (coordinates == null) return false;
//        if () return false;
//        if () return false;
        else {
            return true;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return false;
        if (obj == null | getClass() != obj.getClass()) return false;
        HumanBeing that = (HumanBeing) obj;
        return Objects.equals(id, that.getId());
    }

    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, realHero, hasToothpick, impactSpeed, soundtrackName, weaponType, mood, car);
    }
    @XmlElement(name = "id")
    public void setId(Integer id) {
        this.id = id;
    }
    @XmlElement(name = "name")
    public void setName(String name) {
        this.name = name;
    }
    @XmlElement(name = "coordinates")
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
//    @XmlElement(name = "creationDate")
//    public void setCreationDate(LocalDateTime creationDate) {
//        this.creationDate = creationDate;
//    }
    @XmlElement(name = "realHero")
    public void setRealHero(Boolean realHero) {
        this.realHero = realHero;
    }
    @XmlElement(name = "hasToothpick")
    public void setHasToothpick(Boolean hasToothpick) {
        this.hasToothpick = hasToothpick;
    }
    @XmlElement(name = "impactSpeed")
    public void setImpactSpeed(Double impactSpeed) {
        this.impactSpeed = impactSpeed;
    }
    @XmlElement(name = "soundtrackName")
    public void setSoundtrackName(String soundtrackName) {
        this.soundtrackName = soundtrackName;
    }
    @XmlElement(name = "weaponType")
    public void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
    }
    @XmlElement(name = "mood")
    public void setMood(Mood mood) {
        this.mood = mood;
    }
    @XmlElement(name = "car")
    public void setCar(Car car) {
        this.car = car;
    }




    public Integer getId() {
        return id;
    }

    @Override
    public int compareTo(Element element) {
        return (int) (id - element.getId());
    }


    public String getName() {
        return name;
    }


    public Coordinates getCoordinates() {
        return coordinates;
    }


//    public LocalDateTime getCreationDate() {
//        return creationDate;
//    }


    public Boolean getRealHero() {
        return realHero;
    }


    public Boolean getHasToothpick() {
        return hasToothpick;
    }


    public Double getImpactSpeed() {
        return impactSpeed;
    }


    public String getSoundtrackName() {
        return soundtrackName;
    }


    public WeaponType getWeaponType() {
        return weaponType;
    }


    public Mood getMood() {
        return mood;
    }


    public Car getCar() {
        return car;
    }

}
