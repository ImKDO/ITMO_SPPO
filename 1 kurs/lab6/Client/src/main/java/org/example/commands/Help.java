package org.example.commands;


import org.example.ExecutionResponse;
import org.example.console.Console;
import org.example.CommandManager;

import java.io.Serializable;
import java.nio.channels.SocketChannel;
import java.util.stream.Collectors;
import org.example.commands.abstractCommandClass.Command;

/**
 * "help" - Вывести справку по доступным командам"
 */
public class Help extends Command implements Serializable {
    private final Console console;
    private static SocketChannel socketChannel;

    public Help(Console console,  SocketChannel socketChannel) {
        super("help", "вывести справку по доступным командам");
        this.console = console;
        this.socketChannel = socketChannel;
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
