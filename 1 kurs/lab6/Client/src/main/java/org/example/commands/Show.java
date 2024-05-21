package org.example.commands;


import org.example.ExecutionResponse;
import org.example.console.Console;

import org.example.commands.abstractCommandClass.Command;

/**
 * "show" - вывести в стандартный поток вывода все элементы коллекции в строковом представлении
 */

public class Show extends Command {
    private final Console console;


    public Show(Console console) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.console = console;

    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(String arguments) {
        return new ExecutionResponse("Заглушка");
    }
}
