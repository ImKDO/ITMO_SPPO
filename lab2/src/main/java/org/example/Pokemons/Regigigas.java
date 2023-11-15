package org.example.Pokemons;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;
import org.example.Moves.Regigigas.*;
public class Regigigas extends Pokemon {
    public Regigigas (String name, int level){
        super(name,level);
        super.setType(Type.PSYCHIC,Type.GRASS);
        super.setStats(100,100,100,100,100,100);

        FocusBlast focusBlast = new FocusBlast(90,100);
        super.setMove(focusBlast);
        AerialAce energyBall = new AerialAce(90,100);
        super.setMove(focusBlast);
        Thunderbolt  thunderbolt = new Thunderbolt(0,0);
        super.setMove(focusBlast);
        Thunder thunder = new Thunder(60,Double.POSITIVE_INFINITY);
        super.setMove(focusBlast);
    }
}