package org.example.Pokemons.Pichu;

import org.example.Moves.Pichu.ThunderShock;
import org.example.Moves.Pichu.Facade;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Pichu extends Pokemon {
    public Pichu(String name, int level) {
        super(name, level);
        super.setType(Type.ICE, Type.GROUND);
        super.setStats(50,50,40,30,30,50);

        Facade facade = new Facade(120,100);
        super.setMove(facade);
        ThunderShock thunderShock = new ThunderShock(60,100);
        super.setMove(thunderShock);
    }
}