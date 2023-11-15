package org.example.Moves.Regigigas;

import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;

public class Thunderbolt extends StatusMove {
    public Thunderbolt(double pow, double acc) {
        super(Type.NORMAL, pow, acc);
    }
    @Override
    protected String describe(){
        String[] pie = this.getClass().toString().split("\\.");
        return "Атакует с помощью  " + pie[pie.length - 1];
    }
}