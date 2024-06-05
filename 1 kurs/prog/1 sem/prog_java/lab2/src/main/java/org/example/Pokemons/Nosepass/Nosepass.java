package org.example.Pokemons.Nosepass;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;
import org.example.Moves.Nosepass.Tackle;
import org.example.Moves.Nosepass.ThunderWave;
import org.example.Moves.Nosepass.Discharge;

public class Nosepass extends Pokemon {
    public Nosepass(String name, int level) {
        super(name, level);
        super.setType(Type.WATER);
        super.setStats(100, 100, 100, 100, 100, 100);

        ThunderWave thunderWave = new ThunderWave(90, 100);
        super.setMove(thunderWave);
        Tackle tackle = new Tackle(70, 100);
        super.setMove(tackle);
        Discharge mudBomb = new Discharge(20, 100);
        super.setMove(mudBomb);
    }
}