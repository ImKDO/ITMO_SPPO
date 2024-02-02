package org.example.Pokemons.Pichu.Pikachu.Raichu;
import org.example.Moves.Pichu.Pikachu.Raichu.TailWhip;
import org.example.Pokemons.Pichu.Pikachu.Pikachu;
import org.example.Moves.Pichu.ThunderShock;
import org.example.Moves.Pichu.Pikachu.Nuzzle;
import org.example.Moves.Pichu.Facade;
import ru.ifmo.se.pokemon.Type;

public class Raichu extends Pikachu {
    public Raichu(String name, int level) {
        super(name, level);
        super.setType(Type.ICE, Type.GROUND);
        super.setStats(50,50,40,30,30,50);

        Facade facade = new Facade(120,100);
        super.setMove(facade);
        ThunderShock thunderShock = new ThunderShock(60,100);
        super.setMove(thunderShock);
        Nuzzle nuzzle = new Nuzzle(55,95);
        super.setMove(nuzzle);
        TailWhip tailWhip = new TailWhip(90,100);
    }
}