package org.example.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import org.example.examinationData.Element;
import org.example.examinationData.Validation;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Класс HumanBeing
 */
@JacksonXmlRootElement(localName = "HumanBeing")
public class HumanBeing extends Element implements Validation, Serializable {

    public Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    private String name; //Поле не может быть null, Строка не может быть пустой

    private Coordinates coordinates; //Поле не может быть null

    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    private Boolean realHero; //Поле не может быть null

    private Boolean hasToothpick; //Поле может быть null

    private Double impactSpeed; //Максимальное значение поля: 118, Поле может быть null

    private String soundtrackName; //Поле не может быть null

    private WeaponType weaponType; //Поле может быть null

    private Mood mood; //Поле не может быть null

    private Car car; //Поле не может быть null

    private String user;

    public HumanBeing() {
    }

    public HumanBeing(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public HumanBeing(String user, Integer id, String name, Coordinates coordinates, Boolean realHero, Boolean hasToothpick, Double impactSpeed, LocalDateTime creationDate, String soundtrackName, WeaponType weaponType, Mood mood, Car car) {
        this.user = user;
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.realHero = realHero;
        this.hasToothpick = hasToothpick;
        this.impactSpeed = impactSpeed;
        this.creationDate = creationDate;
        this.soundtrackName = soundtrackName;
        this.weaponType = weaponType;
        this.mood = mood;
        this.car = car;
    }

    @Override
    public String toString() {
        return "HumanBeing{\"user\": " + user + ", " +
               "\"id\": " + id + ", " +
               "\"name\": \"" + name + "\", " +
               "\"coordinates\": \"" + coordinates + "\", " +
               "\"creationDate:\"" + creationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + ", " +
               "\"realHero\": \"" + realHero + "\", " +
               "\"hasToothpick\": \"" + hasToothpick + "\", " +
               "\"impactSpeed\": \"" + impactSpeed + "\", " +
               "\"soundtrackName\": \"" + soundtrackName + "\", " +
               "\"weaponType\": " + (weaponType == null ? "null" : "\"" + weaponType + "\"") + "\", " +
               "\"mood\": \"" + mood + "\", " +
               "\"car\": \"" + car + "}";
    }

    public boolean validate() {
        if ((id != null) && (id > 0) && (!name.isEmpty() && !name.equals("null")) && (impactSpeed < 118) && !(soundtrackName == null) && !(mood == null) && (car.getName() != null)){
            return true;
        } else {
            return false;
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

    //    @XmlElement(name = "id")
    public void setId(Integer id) {
        this.id = id;
    }

    //    @XmlElement(name = "name")
    public void setName(String name) {
        this.name = name;
    }

    //    @XmlElement(name = "coordinates")
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    //    @XmlElement(name = "realHero")
    public void setRealHero(Boolean realHero) {
        this.realHero = realHero;
    }

    //    @XmlElement(name = "hasToothpick",nillable = true )
    public void setHasToothpick(Boolean hasToothpick) {
        this.hasToothpick = hasToothpick;
    }


    //    @XmlElement(name = "impactSpeed")
    public void setImpactSpeed(Double impactSpeed) {
        this.impactSpeed = impactSpeed;
    }

    //    @XmlElement(name = "soundtrackName")
    public void setSoundtrackName(String soundtrackName) {
        this.soundtrackName = soundtrackName;
    }

    //    @XmlElement(name = "weaponType")
    public void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
    }

    //    @XmlElement(name = "mood")
    public void setMood(Mood mood) {
        this.mood = mood;
    }

    //    @XmlElement(name = "car")
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

    //    @XmlTransient
    public LocalDateTime getCreationDate() {
        return creationDate;
    }


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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
