package org.example.commands;

import org.example.Collection.ExecutionResponse;
import org.example.console.Console;

import java.util.List;

import static org.example.Main.logger;

/**
 * "exit" - выйти из программы без сохранения
 */
public class Exit extends Command {
    private final Console console;

    public Exit(Console console){
        super("exit", "выйти из программы без сохранения");
        this.console = console;
    }
    @Override
    public ExecutionResponse apply(List<Object> list) {
            logger.severe("Завершение работы сервера");
            System.exit(1);
            return new ExecutionResponse("привет");
    }
}
