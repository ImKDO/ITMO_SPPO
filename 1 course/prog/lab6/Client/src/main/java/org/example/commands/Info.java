package org.example.commands;


import org.example.commands.abstractCommandClass.Command;
import org.example.console.Console;

/**
 * "info" - Вывести информацию о коллекции
 */

public class Info extends Command {
    private final Console console;


    public Info(Console console) {
        super("info", "вывести информацию о коллекции");
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
