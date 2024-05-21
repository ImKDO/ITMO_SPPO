package org.example.runner;


import org.example.AskManager.Ask;
import org.example.commands.abstractCommandClass.Command;
import org.example.CommandManager;
import org.example.ExecutionResponse;
import org.example.SetCommand;
import org.example.console.Console;
import org.example.models.Coordinates;
import org.example.models.HumanBeing;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Класс для запуска программы и работы со скриптом
 */

public class Runner {
    private final Console console;
    private final List<String> scriptStack = new ArrayList<>(1024);
    private final CommandManager commandManager;
    private int lengthRecursion = -1;
    private SocketChannel socketChannel;

    public Runner(Console console, CommandManager commandManager, SocketChannel socketChannel) {
        this.console = console;
        this.commandManager = commandManager;
        this.socketChannel = socketChannel;
    }

    /**
     * Интерактивный режим
     */
    public void interactiveMode() throws Exception {
        try {
            ExecutionResponse commandStatus;
            String[] userCommand = {"", ""};

            while (true) {
                console.prompt();
                userCommand = (console.readln().trim() + " ").split(" ", 2);
                userCommand[0] = userCommand[0].trim();
                userCommand[1] = userCommand[1].trim();
                commandStatus = sendToServer(userCommand);

                if (commandStatus.getMessage().equals("exit")) break;
                console.println(commandStatus.getMessage());
            }
        } catch (Exception exception) {
            console.println(exception.getMessage());
        }

    }

    private boolean checkRecursion(String argument, Scanner scriptScanner) {
        var i = 1;
        for (String script : scriptStack) {
            i++;
            if (argument.equals(script)) {
                if (lengthRecursion < 0) {
                    console.selectConsoleScanner();
                    console.println("Была замечена рекурсия! Введите максимальную глубину рекурсии (0..500)");
                    while (lengthRecursion < 0 || lengthRecursion > 500) {
                        try {
                            console.print("> ");
                            lengthRecursion = Integer.parseInt(console.readln().trim());
                        } catch (NumberFormatException e) {
                            console.println("длина не распознана");
                        }
                    }
                    console.selectFileScanner(scriptScanner);
                }

                if (i > lengthRecursion || i > 500) {
                    lengthRecursion = -1;
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * Режим для запуска скрипта.
     *
     * @param argument Аргумент скрипта
     * @return Код завершения.
     */
    private ExecutionResponse scriptMode(String argument) {
        String[] userCommand = {"", ""};
        StringBuilder executionOutput = new StringBuilder();

        if (!new File(argument).exists()) return new ExecutionResponse(false, "Файл не существет!");
        if (!Files.isReadable(Paths.get(argument))) return new ExecutionResponse(false, "Прав для чтения нет!");

        scriptStack.add(argument);
        try (Scanner scriptScanner = new Scanner(new File(argument))) {

            ExecutionResponse commandStatus;

            if (!scriptScanner.hasNext()) throw new NoSuchElementException();
            console.selectFileScanner(scriptScanner);
            do {
                userCommand = (console.readln().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();

                executionOutput.append(console.getPrompt() + String.join(" ", userCommand) + "\n");
                boolean needLaunch = true;
                if (userCommand[0].equals("execute_script")) {
                    needLaunch = checkRecursion(userCommand[1], scriptScanner);
                }

                commandStatus = needLaunch ? sendToServer(userCommand) : new ExecutionResponse("Превышена глубина рекурсии");

            } while (!commandStatus.getMessage().equals("exit") && console.isCanReadln());

            return new ExecutionResponse(commandStatus.getExitCode(), executionOutput.toString());
        } catch (FileNotFoundException exception) {
            return new ExecutionResponse(false, "Файл со скриптом не найден!");
        } catch (NoSuchElementException exception) {
            return new ExecutionResponse(false, "Файл со скриптом пуст!");
        } catch (IllegalStateException exception) {

            console.printError("Непредвиденная ошибка!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            scriptStack.remove(scriptStack.size() - 1);
        }
        return new ExecutionResponse("");
    }


    /**
     * Загружает команды.
     *
     * @param userCommand Команда для запуска
     * @return Код завершения.
     */
    private ExecutionResponse sendToServer(String[] userCommand) throws IOException {
        if (userCommand[0].equals("")) return new ExecutionResponse("");
        Command command = commandManager.getCommands().get(userCommand[0]);
        if (command == null) {
            return new ExecutionResponse(false, "Команда '" + userCommand[0] + "' не найдена.");
        } else {
            switch (userCommand[0]) {
                case "execute_script":
                    boolean response1 = command.apply(userCommand[0]).getExitCode();
                    if (response1) {
                        ExecutionResponse response2 = scriptMode(userCommand[1]);

                        return new ExecutionResponse(response2.getExitCode(), "\n" + response2.getMessage().trim());
                    } else {
                        return new ExecutionResponse(response1, "a");
                    }
                case "exit":
                    console.println("Выход из клиента");
                    System.exit(1);
                case "add", "update":
                    int defaultID = 2;
                    HumanBeing humanBeing = new Ask(console).askHumanBeing(defaultID);
                    SetCommand.sendCommand(SetCommand
                                                    .builder()
                                                    .command(userCommand[0])
                                                    .humanBeing(humanBeing)
                                                    .args(userCommand[1])
                                                    .clientSocket(socketChannel)
                                                    .build());

                default:
                    HumanBeing obj = new HumanBeing(); //1,"23",new Coordinates(),LocalDateTime.now(),
                    SetCommand setCommand = new SetCommand(userCommand[0],"123",obj,socketChannel);
                    SetCommand.sendCommand(setCommand);

                    console.println("Команда отправлена на сервер");

                    return new ExecutionResponse("Команда " + command + " успешно исполнена");
            }
        }

    }


}

