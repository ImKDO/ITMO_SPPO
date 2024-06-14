package org.example.commands;

import org.example.Collection.ExecutionResponse;
import org.example.console.Console;
import org.example.managers.CommandManager;

import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.Main.logger;

/**
 * "help" - Вывести справку по доступным командам"
 */
public class Help extends Command {
    private final Console console;
    private final CommandManager commandManager;

    public Help(Console console, CommandManager commandManager) {
        super("help", "вывести справку по доступным командам");
        this.console = console;
        this.commandManager = commandManager;
    }

    /**
     * Выполняет команду
     *
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(List<Object> list) {


        return new ExecutionResponse(commandManager
                .getCommands()
                .values()
                .stream()
                .map(command -> String.format(" %-35s%-1s%n", command.getName(), command.getDescription()))
                .collect(Collectors.joining("\n")));
    }
}
