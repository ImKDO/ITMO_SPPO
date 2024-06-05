package org.example.commands;


import org.example.console.Console;

import org.example.commands.abstractCommandClass.Command;

/**
 * Команда sort - сортирует коллекцию в естественном порядке
 */
public class Sort extends Command{
    private final Console console;

    public Sort(Console console) {
        super("sort", "сортирует коллекцию в естественном порядке ");
        this.console = console;

    }



    @Override
    public ExecutionResponse apply(String arguments) {
        return new ExecutionResponse("Коллекция отсортировна!");
    }
}
