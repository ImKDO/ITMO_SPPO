package org.example.Nature;

import org.example.Creature.Creature;
import org.example.InterfaceNature.InterfaceNature;

public class Nature extends Creature implements InterfaceNature {
    public Nature(String name) {
        super(name);
        this.name = name;

    }

    @Override
    public void do_action(Nature o, String action) {

        class ActionTask implements Runnable {
            private Nature o;
            private String action;


            public ActionTask(Nature o, String action) {
                this.o = o;
                this.action = action;
            }


            @Override
            public void run() {
                System.out.println(o.hashCode() + " действие " + o.toString() + "а: " + action);
            }
        }


        ActionTask task = new ActionTask(o, action);


        new Thread(task).start();
    }



    // нестатический вложенный класс
    public class Plant {
        private String name;
        private String color;

        public Plant(String name, String color) {
            this.name = name;
            this.color = color;
        }

        public void grow() {
            System.out.println(name + " растет " + color + " цветом");
        }
    }

    // анонимный класс
    InterfaceNature animal = new InterfaceNature() {
        @Override
        public void do_action(Nature o, String action) {
            System.out.println("Природа " + action + " с " + o.name);
        }
    };
}