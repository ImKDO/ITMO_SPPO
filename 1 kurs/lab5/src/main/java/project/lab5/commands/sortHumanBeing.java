package project.lab5.commands;

import project.lab5.models.HumanBeing;

import java.util.Comparator;

public class sortHumanBeing implements Comparator<HumanBeing> {
    @Override
    public int compare(HumanBeing o1, HumanBeing o2) {
        return o1.getId() - o2.getId();
    }
}
