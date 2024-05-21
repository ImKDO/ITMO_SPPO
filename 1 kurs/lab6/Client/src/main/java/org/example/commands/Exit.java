package org.example.commands;


import org.example.ExecutionResponse;
import org.example.console.Console;
import org.example.commands.abstractCommandClass.Command;

/**
 * "exit" - выйти из программы без сохранения
 */
public class Exit extends Command {
    private final Console console;

    public Exit(Console console){
        super("exit", "выход из клиента");
        this.console = console;
    }
    @Override
    public ExecutionResponse apply(String argument) {
            console.println("Выход из программы");
            System.exit(0);
        return null;
    }
}
