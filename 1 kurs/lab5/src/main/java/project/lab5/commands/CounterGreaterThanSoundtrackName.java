package project.lab5.commands;

import project.lab5.AskManager.Ask;
import project.lab5.Collection.ExecutionResponse;
import project.lab5.console.Console;
import project.lab5.managers.CollectionManager;

/**
 * Команда counter_greater_than_soundtrack_name soundtrack - считает количество элементов с заданным soundtrack
 */
public class CounterGreaterThanSoundtrackName extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public CounterGreaterThanSoundtrackName(Console console, CollectionManager collectionManager) {
        super("counter_greater_than_soundtrack_name soundtrack", "Показать количество коллекций с заданным soundtrackName");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String argument) throws Ask.AskBreak {
        if (argument.isEmpty()) {
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        }
        console.println("Введите soundtrack");
        String soundtrack = console.readln().trim();
        int count = 0;
        for (int element = 0; element < collectionManager.getCollection().size(); element++) {
            if (collectionManager.getCollection().get(element).getSoundtrackName().equals(soundtrack)) {
                count++;
            }
        }
        return new ExecutionResponse("Количество " + soundtrack + " в коллекции равно " + count);
    }
}
