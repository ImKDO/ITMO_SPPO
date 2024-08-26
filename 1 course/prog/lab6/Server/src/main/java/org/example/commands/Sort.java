package org.example.commands;

import org.example.Collection.ExecutionResponse;
import org.example.console.Console;
import org.example.managers.CollectionManager;
import org.example.models.HumanBeing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    public ExecutionResponse apply(List<Object> list) {
        if (list.get(1).toString().isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");

        Comparator<HumanBeing> sortHumanBeing = new sortHumanBeing();

// Сортируем коллекцию с использованием Stream API и собираем в список
        List<HumanBeing> sortedList = collectionManager.getCollection()
                .stream()
                .sorted(sortHumanBeing)
                .collect(Collectors.toList());

// Устанавливаем отсортированную коллекцию обратно в collectionManager
        collectionManager.setCollection((ArrayList<HumanBeing>) sortedList);

        return new ExecutionResponse("Коллекция отсортировна!");
    }
}
