package org.example.commands;


import org.example.console.Console;

import org.example.commands.abstractCommandClass.Command;

/**
 * "remove_greater" - удалить из коллекции все элементы, превышающие element
 */
public class RemoveGreater extends Command {
    private final Console console;


    public RemoveGreater(Console console) {
        super("remove_greater {element}", "удалить из коллекции все элементы, превышающие element");
        this.console = console;

    }
    @Override
    public ExecutionResponse apply(String arguments) {
        if (arguments.isEmpty()) return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");

        return new ExecutionResponse(" все HumanBeing с большим id удалились");
    }
}
