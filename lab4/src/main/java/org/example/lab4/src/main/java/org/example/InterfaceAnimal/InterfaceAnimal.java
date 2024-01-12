package org.example.InterfaceAnimal;

import org.example.Animal.Animal;

public interface InterfaceAnimal {
    public void move(Animal o, String action);

    public void voice(Animal o, String action, LevelVoice lvl_v);
    public void not_voice(Animal o);
}
