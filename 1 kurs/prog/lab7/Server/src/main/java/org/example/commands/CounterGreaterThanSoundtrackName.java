package org.example.commands;

import org.example.Collection.ExecutionResponse;
import org.example.console.Console;
import org.example.managers.CollectionManager;

import java.util.List;

/**
 *  "counter_greater_than_soundtrack_name" soundtrack - считает количество элементов с заданным soundtrack
 */
public class CounterGreaterThanSoundtrackName extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public CounterGreaterThanSoundtrackName(Console console, CollectionManager collectionManager) {
        super("counter_greater_than_soundtrack_name soundtrack ", "показать количество коллекций с заданным soundtrackName");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(List<Object> list) {
        if (list.get(1).toString().isEmpty()) {
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        }
        String soundtrack = list.get(1).toString();
        int count = 0;
        for (int element = 0; element < collectionManager.getCollection().size(); element++) {
            if (collectionManager.getCollection().get(element).getSoundtrackName().equals(soundtrack) & collectionManager.getCollection().get(element).getUser().equals(list.get(3).toString())) {
                count++;
            }
        }
        return new ExecutionResponse("Количество " + soundtrack + " в коллекции равно " + count);
    }
}
