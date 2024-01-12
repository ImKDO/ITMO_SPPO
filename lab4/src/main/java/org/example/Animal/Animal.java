package org.example.Animal;

import org.example.Creature.Creature;
import org.example.InterfaceAnimal.InterfaceAnimal;
import org.example.InterfaceAnimal.LevelVoice;
import org.example.Uncheked.Unchecked;
import org.example.Cheked.*;

public class Animal extends Creature implements InterfaceAnimal {
    String name;

    public Animal(String name) throws Cheked {
        super(name);
        this.name = name;
        // проверяем, что имя не пустое и не содержит цифр или специальных символов
        if (name == null || name.isEmpty() || !name.matches("[a-zA-Zа-яА-Я]+")) {
            throw new Cheked("Неверное имя животного: " + name);
        }
    }

    @Override
    public void move(Animal o, String action) {
        System.out.println(o.hashCode() + " действие " + o.toString() + "а: " + action);
    }

    @Override
    public void voice(Animal o, String action, LevelVoice a) {
        if (o.name != "") {
            if (o.equals("Жаворонок")) {
                System.out.println(o.hashCode() + " действие " + o.toString() + "а. " + o.toString() + a.getLvl_voice() + " начинает петь: " + '"' + action + '"');
            } else {
                System.out.println(o.hashCode() + " действие " + o.toString() + "а. " + o.toString() + a.getLvl_voice() + " говорит: " + '"' + action + '"');
            }
        } else {
            throw new Unchecked("Введите не пустую строку");
        }
    }

    @Override
    public void not_voice(Animal o) {
        if (o.name != "") {
            System.out.println(o.hashCode() + " действие " + o.toString() + "а: " + o.toString() + " замолчал");
        } else {
            throw new Unchecked("Введите не пустую строку");
        }
    }

    // статический вложенный класс, представляющий Медведя
    public static class Bear {
        private String name;
        private String color;

        public Bear(String name, String color) {
            this.name = name;
            this.color = color;
        }

        public void BearGo() {
            System.out.println(name + " идет " + color + " шерсть");
        }
    }
}
