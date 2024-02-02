package org.example.Human;

import org.example.Creature.Creature;
import org.example.InterfaceHuman.InterfaceHuman;

public class Human extends Creature implements InterfaceHuman {
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
