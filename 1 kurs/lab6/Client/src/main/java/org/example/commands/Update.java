package org.example.commands;


import org.example.ExecutionResponse;
import org.example.console.Console;

import org.example.models.HumanBeing;
import org.example.commands.abstractCommandClass.Command;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * "update id" - обновить значение элемента коллекции, id которого равен заданному
 */
public class Update extends Command {
    private final Console console;
    ByteBuffer messageCommand = ByteBuffer.allocate(4);
    SocketChannel clientSocket;

    public Update(Console console, SocketChannel clientSocket) {
        super("update id {element}", "обновить элемент");
        this.console = console;
        this.clientSocket = clientSocket;
    }

    public ExecutionResponse apply(String arguments) {

        boolean executeCommand;
        try {
            executeCommand =  Boolean.parseBoolean(String.valueOf(clientSocket.read(messageCommand)));
            messageCommand.flip();
            console.println(executeCommand);
            if (arguments.isEmpty())
                return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
            long id = 0;
            try {
                id = Integer.parseInt(arguments);
            } catch (NumberFormatException ignored) {
            }

        } catch (IOException e) {
            return new ExecutionResponse("Командна не сработала!");
        }
        return new ExecutionResponse("HumanBeing с таким id не существует!");
    }
}
