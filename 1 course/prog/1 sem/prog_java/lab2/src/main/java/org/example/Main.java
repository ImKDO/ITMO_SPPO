package org.example;
import org.example.Pokemons.Nosepass.Probopass.Probopass;
import org.example.Pokemons.Nosepass.Nosepass;
import org.example.Pokemons.Pichu.Pikachu.Raichu.Raichu;
import org.example.Pokemons.Pichu.Pikachu.Pikachu;
import org.example.Pokemons.Pichu.Pichu;
import ru.ifmo.se.pokemon.*;
import org.example.Pokemons.*;
// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Battle battle = new Battle();
        Regigigas celebi = new Regigigas("Regigigas",1);
        Nosepass shellos = new Nosepass("Nosepass",1);
        Probopass gastrodon = new Probopass("Probopass",2);
        Pichu swinub = new Pichu("Pichu",1);
        Pikachu piloswine = new Pikachu("Pikachu",2);
        Raichu mamoswine = new Raichu("Raichu",3);
        battle.addAlly(celebi);
        battle.addAlly(shellos);
        battle.addAlly(gastrodon);
        battle.addFoe(swinub);
        battle.addFoe(piloswine);
        battle.addFoe(mamoswine);
        battle.go();

    }
}