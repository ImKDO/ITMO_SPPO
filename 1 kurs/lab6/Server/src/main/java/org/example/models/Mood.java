package org.example.models;


import java.io.Serializable;

public enum Mood implements Serializable {
    CALM,
    RAGE,
    FRENZY;

    public static String getMood() {
        StringBuilder listMood = new StringBuilder();
        for (var elementMood : values()) {
            listMood.append(elementMood.name()).append(", ");
        }
        return listMood.substring(0, listMood.length() - 2);
    }
}
