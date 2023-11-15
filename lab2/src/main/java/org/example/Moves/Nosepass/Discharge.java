package org.example.Moves.Nosepass;
import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;

public class Discharge extends PhysicalMove {
    public Discharge(double pow, double acc) {
        super(Type.GROUND, pow, acc);
    }
    @Override
    protected String describe(){
        String[] pie = this.getClass().toString().split("\\.");
        return "Атакует с помощью  " + pie[pie.length - 1];
    }
}
