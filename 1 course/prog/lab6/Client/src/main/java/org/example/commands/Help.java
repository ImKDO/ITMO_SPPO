package org.example.commands;


import org.example.console.Console;

import java.io.Serializable;

import org.example.commands.abstractCommandClass.Command;

/**
 * "help" - Вывести справку по доступным командам"
 */
public class Help extends Command implements Serializable {
    private final Console console;

    public Help(Console console) {
        super("help", "вывести справку по доступным командам");
        this.console = console;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(String arguments) {

        return new ExecutionResponse(true, "заглушка");
    }
}
