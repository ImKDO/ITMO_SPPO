package org.example.commands;


import org.example.console.Console;

import org.example.commands.abstractCommandClass.Command;

/**
 * Команда remove_last - удаляет последний элемент в коллекции
 */
public class RemoveLast extends Command {
    private final Console console;

    public RemoveLast(Console console) {
        super("remove_last", "удаляет элемент коллекции по id");
        this.console = console;

    }

    /**
     * Выполнение команды remove_last
     * @param arguments Аргумент для выполнения
     * @return
     */
    public ExecutionResponse apply(String arguments) {
        return new ExecutionResponse("Заглушка");
    }
}
