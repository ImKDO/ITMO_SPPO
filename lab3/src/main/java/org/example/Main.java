package org.example;


import org.example.Animal.Animals;
import org.example.Human.Human;
import org.example.Nature.Nature;
import org.example.Interface_Animal.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Animals rabbit = new Animals("Кролик");
        Animals lark = new Animals("Жаворонок");
        Human cr = new Human("CR");
        Nature sun = new Nature("Солнц");
        Nature forest = new Nature("Лес");
        rabbit.voice(rabbit, "Эй, CR !!!", Level_Voice.HIGH);
        cr.do_action(cr,"Не реагируерт");
        rabbit.move(rabbit, "Отошел немного назад");
        rabbit.move(rabbit,"Заслонил лицо лапкой от солнца");
        rabbit.move(rabbit,"Посмотрел на верхушку");
        rabbit.voice(rabbit,"КРИСТОФЕЕЕЕЕН РОООБИИИИИН!!!",Level_Voice.HIGH);
        rabbit.voice(rabbit,"Эй, слушай, это Кролик!!!",Level_Voice.MEDIUM);
        rabbit.not_voice(rabbit);
        rabbit.move(rabbit,"Присушался");
        forest.do_action(forest, "Лес становится тихим");
        sun.do_action(sun, "Освещает лес");
        lark.voice(lark, "курлык курлык",Level_Voice.HIGH);

    }
}
