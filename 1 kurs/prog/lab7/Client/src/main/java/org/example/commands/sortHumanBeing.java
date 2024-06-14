package org.example.commands;

import org.example.models.HumanBeing;

import java.util.Comparator;
import org.example.commands.abstractCommandClass.Command;

/**
 * Перегрузка Comparator для установления собственной естественной сортировки
 */
public class sortHumanBeing implements Comparator<HumanBeing> {
    @Override
    public int compare(HumanBeing o1, HumanBeing o2) {
        return o1.getId() - o2.getId();
    }
}
