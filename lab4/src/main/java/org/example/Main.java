package org.example;


import org.example.Animal.Animal;
import org.example.Human.Human;
import org.example.Nature.Nature;
import org.example.InterfaceAnimal.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Animal rabbit = new Animal("Кролик");
        Animal lark = new Animal("Жаворонок");
        Human cr = new Human("CR");
        Nature sun = new Nature("Солнц");
        Nature forest = new Nature("Лес");
        rabbit.voice(rabbit, "Эй, CR !!!", LevelVoice.HIGH);
        cr.do_action(cr,"Не реагируерт");
        rabbit.move(rabbit, "Отошел немного назад");
        rabbit.move(rabbit,"Заслонил лицо лапкой от солнца");
        rabbit.move(rabbit,"Посмотрел на верхушку");
        rabbit.voice(rabbit,"КРИСТОФЕЕЕЕЕН РОООБИИИИИН!!!", LevelVoice.HIGH);
        rabbit.voice(rabbit,"Эй, слушай, это Кролик!!!", LevelVoice.MEDIUM);
        rabbit.not_voice(rabbit);
        rabbit.move(rabbit,"Присушался");
        forest.do_action(forest, "Лес становится тихим");
        sun.do_action(sun, "Освещает лес");
        lark.voice(lark, "курлык курлык", LevelVoice.HIGH);

    }
}
