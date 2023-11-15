package org.example.Moves.Pichu.Pikachu;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Type;

public class Nuzzle extends SpecialMove {
    public Nuzzle(double pow, double acc) {
        super( Type.ICE, pow, acc);
    }
    @Override
    protected String describe(){
        String[] pie = this.getClass().toString().split("\\.");
        return "Атакует с помощью  " + pie[pie.length - 1];
    }
}
