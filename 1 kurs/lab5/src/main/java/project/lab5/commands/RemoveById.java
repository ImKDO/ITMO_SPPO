package project.lab5.commands;

import project.lab5.AskManager.Ask;
import project.lab5.Collection.ExecutionResponse;
import project.lab5.console.Console;
import project.lab5.managers.CollectionManager;

/**
 * Команда remove_by_id id - удалить элемент по id
 */
public class RemoveById extends Command {
    private final Console console;
    private CollectionManager collectionManager;

    public RemoveById(Console console, CollectionManager collectionManager) {
        super("remove_by_id id", "Команда remove_by_id - удаляет элемент коллекции по id");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    public ExecutionResponse apply(String arguments) {
        try {
            if (arguments.isEmpty())
                return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
            console.println("Введите id объекта, который хотите удалить");
            int id = Ask.askId(console);
            collectionManager.getCollection().remove(id - 1);
            return new ExecutionResponse("Элемент с id " + id + " удален");
        } catch (Ask.AskBreak e) {
            throw new RuntimeException(e);
        }
    }
}
