package org.example.Interface_Animal;

import org.example.Animal.Animals;

public interface Interface_Animal {
    public String move(Animals o, String action);

    public String voice(Animals o, String action, Level_Voice lvl_v);
    public String not_voice(Animals o);
}
