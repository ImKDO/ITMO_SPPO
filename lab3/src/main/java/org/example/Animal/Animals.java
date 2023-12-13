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
    public void move(Animals o, String action) {
        System.out.println(o.hashCode() + " действие " + o.toString()  + "а: "+ action);
    }

    @Override
    public void voice(Animals o, String action, Level_Voice a) {
        if (o.equals("Жаворонок")) {
            System.out.println(o.hashCode() + " действие " + o.toString() + "а. " + o.toString() + a.getLvl_voice() + " начинает петь: " + '"' + action + '"');
        }
        else{
            System.out.println( o.hashCode() + " действие " + o.toString() + "а. " + o.toString() + a.getLvl_voice() + " говорит: " + '"' + action + '"');
        }
    }
    @Override
    public void not_voice(Animals o){
        System.out.println(o.hashCode() + " 'действие' " + o.toString()  + "а: " + o.toString() + " замолчал");
    }
}
