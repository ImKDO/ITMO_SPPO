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

        // создание объекта локального класса
        ActionTask task = new ActionTask(o, action);

        // запуск локального класса в отдельном потоке
        new Thread(task).start();
    }

    // статический вложенный класс, представляющий животное
    public static class Animal {
        private String name;
        private String sound;

        public Animal(String name, String sound) {
            this.name = name;
            this.sound = sound;
        }

        public void makeSound() {
            System.out.println(name + " делает " + sound);
        }
    }

    // нестатический вложенный класс, представляющий растение
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
}
