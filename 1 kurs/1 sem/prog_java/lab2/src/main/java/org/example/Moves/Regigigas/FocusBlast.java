package org.example.Moves.Regigigas;

import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Type;

public class FocusBlast extends SpecialMove {
    public FocusBlast(double pow, double acc) {
        super(Type.PSYCHIC, pow, acc);
    }
    @Override
    protected String describe(){
        String[] pie = this.getClass().toString().split("\\.");
        return "Атакует с помощью  " + pie[pie.length - 1];
    }
}