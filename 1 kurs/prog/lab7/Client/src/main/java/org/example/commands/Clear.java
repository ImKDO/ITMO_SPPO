package org.example.commands;


import org.example.console.Console;

import org.example.commands.abstractCommandClass.Command;

/**
 *  "clear" - очистить коллекцию
 */
public class Clear extends Command {
    private final Console console;


    public Clear(Console console) {
        super("clear", "очистить коллекцию");

        this.console = console;
    }
    @Override
    public ExecutionResponse apply(String argument) {

        return new ExecutionResponse("Заглушка");
    }
}
