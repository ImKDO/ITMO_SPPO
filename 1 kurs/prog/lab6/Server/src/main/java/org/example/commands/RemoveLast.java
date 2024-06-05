package org.example.commands;

import org.example.Collection.ExecutionResponse;
import org.example.console.Console;
import org.example.managers.CollectionManager;

import java.util.List;

/**
 * Команда remove_last - удаляет последний элемент в коллекции
 */
public class RemoveLast extends Command {
    private final Console console;
    private CollectionManager collectionManager;

    public RemoveLast(Console console, CollectionManager collectionManager) {
        super("remove_last", "удаляет элемент коллекции по id");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполнение команды remove_last
     * @param list Аргумент для выполнения
     * @return
     */
    public ExecutionResponse apply(List<Object> list) {
        if (list.get(1).toString().isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        if(!collectionManager.getCollection().isEmpty()) {
            collectionManager.getCollection().remove(collectionManager.getCollection().size() - 1);
            return new ExecutionResponse("Последний элемент коллекции удален");
        }
        else {
            return new ExecutionResponse("Коллекция пуста");
        }
    }
}
