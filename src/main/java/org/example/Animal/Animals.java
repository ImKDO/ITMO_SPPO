package org.example.Animal;

import org.example.Creatures.Creatures;
import org.example.Interface_Animal.Interface_Animal;
import org.example.Interface_Animal.Level_Voice;

public class Animals extends Creatures implements Interface_Animal {
    String name;

    public Animals(String name) {
        super(name);
        this.name = name;
    }

    @Override
    public String move(Animals o, String action) {
        return o.hashCode() + " действие " + o.toString()  + "а: "+ action;
    }

    @Override
    public String voice(Animals o, String action, Level_Voice a) {
        if (o.equals("Жаворонок")) {
            return o.hashCode() + " действие " + o.toString() + "а. " + o.toString() + a.getLvl_voice() + " начинает петь: " + '"' + action + '"';
        }
        else{
            return o.hashCode() + " действие " + o.toString() + "а. " + o.toString() + a.getLvl_voice() + " говорит: " + '"' + action + '"';
        }
    }
    @Override
    public String not_voice(Animals o){
        return o.hashCode() + " 'действие' " + o.toString()  + "а: " + o.toString() + " замолчал";
    }
}
