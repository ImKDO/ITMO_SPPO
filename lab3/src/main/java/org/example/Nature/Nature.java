package org.example.Nature;

import org.example.Creatures.Creatures;
import org.example.Interface_Nature.Interface_Nature;

public class Nature extends Creatures implements Interface_Nature {
    public Nature(String name) {
        super(name);
        this.name = name;

    }

    @Override
    public void do_action(Nature o, String action) {
        System.out.println(o.hashCode() + " действие " + o.toString() + "а: " + action);
    }

}
