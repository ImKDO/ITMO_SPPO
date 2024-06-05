package org.example.commands;


import org.example.console.Console;

import org.example.commands.abstractCommandClass.Command;

/**
 * "update id" - обновить значение элемента коллекции, id которого равен заданному
 */
public class Update extends Command {
    private final Console console;

    public Update(Console console) {
        super("update id {element}", "обновить элемент");
        this.console = console;
    }

    public ExecutionResponse apply(String arguments) {

        boolean executeCommand;
        if (arguments.isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        long id = 0;
        try {
            id = Integer.parseInt(arguments);
        } catch (NumberFormatException ignored) {
        }
        if (id < 0) {
            return new ExecutionResponse("HumanBeing с таким id не существует!");
        }
        return new ExecutionResponse("HumanBeing с id = " + id + " обновлен");
    }
}
