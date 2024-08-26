package org.example.commands;


import org.example.console.Console;

import org.example.commands.abstractCommandClass.Command;


public class CountByMood extends Command {
    private final Console console;


    public CountByMood(Console console) {
        super("count_by_mood mood", "показать количество коллекций с заданным mood");
        this.console = console;

    }

    @Override
    public ExecutionResponse apply(String argument) {
        return new ExecutionResponse("Заглушка");
    }
}
