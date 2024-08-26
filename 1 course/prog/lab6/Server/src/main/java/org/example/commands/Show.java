package org.example.commands;

import org.example.Collection.ExecutionResponse;
import org.example.console.Console;
import org.example.managers.CollectionManager;

import java.util.List;

/**
 * "show" - вывести в стандартный поток вывода все элементы коллекции в строковом представлении
 */

public class Show extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Show(Console console, CollectionManager collectionManager) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(List<Object> list) {


        return new ExecutionResponse(collectionManager.toString());
    }
}
