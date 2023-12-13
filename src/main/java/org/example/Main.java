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
        System.out.println(rabbit.voice(rabbit, "Эй, CR !!!", Level_Voice.HIGH));
        System.out.println(cr.do_action(cr,"Не реагируерт"));
        System.out.println(rabbit.move(rabbit, "Отошел немного назад"));
        System.out.println(rabbit.move(rabbit,"Заслонил лицо лапкой от солнца"));
        System.out.println(rabbit.move(rabbit,"Посмотрел на верхушку"));
        System.out.println(rabbit.voice(rabbit,"КРИСТОФЕЕЕЕЕН РОООБИИИИИН!!!",Level_Voice.HIGH));
        System.out.println(rabbit.voice(rabbit,"Эй, слушай, это Кролик!!!",Level_Voice.MEDIUM));
        System.out.println(rabbit.not_voice(rabbit));
        System.out.println(rabbit.move(rabbit,"Присушался"));
        System.out.println(forest.do_action(forest, "Лес становится тихим"));
        System.out.println(sun.do_action(sun, "Освещает лес"));
        System.out.println(lark.voice(lark, "курлык курлык",Level_Voice.HIGH));

    }
}
