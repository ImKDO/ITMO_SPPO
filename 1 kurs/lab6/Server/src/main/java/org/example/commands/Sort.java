package org.example.commands;

import org.example.Collection.ExecutionResponse;
import org.example.console.Console;
import org.example.managers.CollectionManager;
import org.example.models.HumanBeing;

import java.util.Collections;
import java.util.Comparator;

/**
 * Команда sort - сортирует коллекцию в естественном порядке
 */
public class Sort extends Command{
    private final Console console;
    private CollectionManager collectionManager;

    public Sort(Console console, CollectionManager collectionManager) {
        super("sort", "сортирует коллекцию в естественном порядке ");
        this.console = console;
        this.collectionManager = collectionManager;
    }



    @Override
    public ExecutionResponse apply(String arguments) {
        if (arguments.isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");

        Comparator<HumanBeing> sortHumanBeing = new sortHumanBeing();
        Collections.sort(collectionManager.getCollection(), sortHumanBeing);

        return new ExecutionResponse("Коллекция отсортировна!");
    }
}
