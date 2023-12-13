package org.example.Interface_Animal;

import org.example.Animal.Animals;

public interface Interface_Animal {
    public void move(Animals o, String action);

    public void voice(Animals o, String action, Level_Voice lvl_v);
    public void not_voice(Animals o);
}
