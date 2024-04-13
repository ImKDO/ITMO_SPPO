package project.lab5.commands;

import project.lab5.Collection.ExecutionResponse;
import project.lab5.console.Console;
import project.lab5.managers.CollectionManager;

/**
 * Команда remove_last - удаляет последний элемент в коллекции
 */
public class RemoveLast extends Command {
    private final Console console;
    private CollectionManager collectionManager;

    public RemoveLast(Console console, CollectionManager collectionManager) {
        super("remove_last", "Команда remove_last - удаляет элемент коллекции по id");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполнение команды remove_last
     * @param arguments Аргумент для выполнения
     * @return
     */
    public ExecutionResponse apply(String arguments) {
        if (arguments.isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        collectionManager.getCollection().remove(collectionManager.getCollection().size() - 1);
        return new ExecutionResponse(false, "Последний элемент коллекции удален");
    }
}
