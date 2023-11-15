package org.example.Pokemons.Nosepass.Probopass;

import org.example.Moves.Nosepass.ThunderWave;
import org.example.Moves.Nosepass.Tackle;
import org.example.Moves.Nosepass.Probopass.IronDefense;
import org.example.Moves.Nosepass.Discharge;
import org.example.Pokemons.Nosepass.Nosepass;
import ru.ifmo.se.pokemon.Type;

public class Probopass extends Nosepass {
    public Probopass(String name, int level) {
        super(name, level);
        super.setType(Type.WATER, Type.GROUND);
        super.setStats(100, 100, 100, 100, 100, 100);

        ThunderWave thunderWave = new ThunderWave(90, 100);
        super.setMove(thunderWave);
        Tackle tackle = new Tackle(70, 100);
        super.setMove(tackle);
        Discharge mudBomb = new Discharge(20, 100);
        super.setMove(mudBomb);
        IronDefense ironDefense = new IronDefense(80, 100);
    }
}