package org.example.commands;


import org.example.console.Console;

import org.example.commands.abstractCommandClass.Command;

/**
 *  "remove_by_id id" - удалить элемент по id
 */
public class RemoveById extends Command {
    private final Console console;


    public RemoveById(Console console) {
        super("remove_by_id id", "удалить элемент из коллекции по ID");
        this.console = console;

    }

    /**
     * Выполняет команду
     *
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(String arguments) {
        return new ExecutionResponse("Заглушка");
    }
}
