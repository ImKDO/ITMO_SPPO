package org.example.Human;

import org.example.Creatures.Creatures;
import org.example.Interface_Human.Interface_Human;

public class Human extends Creatures implements Interface_Human {
    String name;
    public Human(String name){
        super(name);
        this.name = name;
    }
    @Override
    public void do_action(Human o, String action){
        System.out.println(o.toString() + ": " + action);
    }

}
