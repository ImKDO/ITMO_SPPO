package org.example.runner;


import org.example.AskManager.Ask;
import org.example.Response;
import org.example.commands.abstractCommandClass.Command;
import org.example.CommandManager;
import org.example.commands.ExecutionResponse;
import org.example.SetCommand;
import org.example.console.Console;
import org.example.models.HumanBeing;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static org.apache.commons.lang3.SerializationUtils.deserialize;
import static org.apache.commons.lang3.SerializationUtils.serialize;

/**
 * Класс для запуска программы и работы со скриптом
 */

public class Runner {
    private final Console console;
    private final List<String> scriptStack = new ArrayList<>(1024);
    private final CommandManager commandManager;
    private int lengthRecursion = -1;
    private static int port = 38423;
    SocketChannel socketChannel;

    private String login;


    public Runner(String login, Console console, CommandManager commandManager, SocketChannel socketChannel) {
        this.console = console;
        this.commandManager = commandManager;
        this.socketChannel = socketChannel;
        this.login = login;
    }

    /**
     * Интерактивный режим
     */
    public void interactiveMode() {
        ExecutionResponse commandStatus;
        String[] userCommand = {"", ""};


        while (true) {

            console.prompt();
            userCommand = (console.readln().trim() + " ").split(" ", 2);

            userCommand[0] = userCommand[0].trim();
            userCommand[1] = userCommand[1].trim();
            sendToServer(userCommand);
        }
    }

    private boolean checkRecursion(String argument, Scanner scriptScanner) {
        var i = 0;
        for (String script : scriptStack) {
            i++;
            if (argument.equals(script)) {
                if (lengthRecursion < 0) {
                    console.selectConsoleScanner();
                    console.println("Была замечена рекурсия! Введите максимальную глубину рекурсии (0..3)");
                    while (lengthRecursion < 0 || lengthRecursion > 3) {
                        try {
                            console.print("> ");
                            lengthRecursion = Integer.parseInt(console.readln().trim());
                        } catch (NumberFormatException e) {
                            console.println("длина не распознана");
                        }
                    }
                    console.selectFileScanner(scriptScanner);
                }

                if (i > lengthRecursion || i > 3) {
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
                while (console.isCanReadln() && userCommand[0].isEmpty()) {
                    userCommand = (console.readln().trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                }
                executionOutput.append(console.getPrompt() + String.join(" ", userCommand) + "\n");
                boolean needLaunch = true;
                if (userCommand[0].equals("execute_script")) {
                    needLaunch = checkRecursion(userCommand[1], scriptScanner);
                }

                commandStatus = needLaunch ? sendToServer(userCommand) : new ExecutionResponse("Превышена глубина рекурсии");

//                System.out.println(commandStatus);
                if (userCommand[0].equals("execute_script")) console.selectFileScanner(scriptScanner);
                executionOutput.append(commandStatus.getMessage() + "\n");
            } while (!commandStatus.getMessage().equals("exit") && console.isCanReadln());

            console.selectConsoleScanner();

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
     * Отправить команду на сервер + получить ответ
     *
     * @param userCommand
     * @return
     */

    private ExecutionResponse sendToServer(String[] userCommand) {
        if (userCommand[0].equals("")) return new ExecutionResponse("");
        Command command = commandManager.getCommands().get(userCommand[0]);
        if (userCommand[0].trim().equals("exit")) {
            console.println("Выход из клиента");
            System.exit(0);

        } else if (command == null) {
            console.println("Команда '" + userCommand[0] + "' не найдена.");
        } else {
            ByteBuffer byteBuffer = ByteBuffer.allocate(500_000_000);
            switch (userCommand[0]) {


                case "execute_script":
                    try {
                        //Инициализация данных для отправки
                        HumanBeing obj = new HumanBeing();
                        SetCommand setCommand = new SetCommand(userCommand[0], "", obj, socketChannel);

                        ExecutionResponse response2 = scriptMode(userCommand[1]);
                        //Отправка запроса на сервер
                        SetCommand.sendCommand(setCommand, byteBuffer);

                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            console.println("asdfghjk");
                        }

                        SetCommand.responseServer(byteBuffer);
                        ObjectInputStream on = new ObjectInputStream(new ByteArrayInputStream(byteBuffer.array()));
                        Response response = (Response) on.readObject();
                        System.out.println(response.getResponse());
                        return new ExecutionResponse(response2.getExitCode(), "\n" + response2.getMessage().trim());

                    } catch (IOException e) {
                        console.println("Произошли проблемы с обменом данных");
                    } catch (ClassNotFoundException e) {
                        console.println("Ошибка сериализации объекта");
                    }
                    break;


                case "add", "update":
                    try {
                        int defaultID = -1;
                        HumanBeing humanBeing = new Ask(console, login).askHumanBeing(defaultID);

                        //Отправка команды на сервер
                        SetCommand.sendCommand(SetCommand
                                .builder()
                                .command(userCommand[0])
                                .humanBeing(humanBeing)
                                .args(userCommand[1])
                                .clientSocket(socketChannel)
                                .build(), byteBuffer);

                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        //Получение ответа от сервера
                        SetCommand.responseServer(byteBuffer);
                        ObjectInputStream on = new ObjectInputStream(new ByteArrayInputStream(byteBuffer.array()));
                        Response response = (Response) on.readObject();
                        console.println(response.getResponse());
                    } catch (IOException e) {
                        console.println("adadadada");
                    } catch (ClassNotFoundException e) {
                        console.println("Произошли проблемы с сериализация объектов");
                    }
                    break;


                case "exit":
                    console.println("Выход из клиента");
                    System.exit(0);
                    break;


                default:
                    try {

                        //Инициализация данных для отправки
                        HumanBeing obj = new HumanBeing(); //1,"23",new Coordinates(),LocalDateTime.now(),
                        SetCommand setCommand = new SetCommand(userCommand[0], userCommand[1], obj, socketChannel);

                        //Отправка запроса на сервер
                        SetCommand.sendCommand(setCommand, byteBuffer);

                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            console.println("hjfyffukfjhgkh");
                        }
                        SetCommand.responseServer(byteBuffer);
                        if (socketChannel.isOpen()) {
                            Response response = (Response) (new ObjectInputStream(new ByteArrayInputStream(byteBuffer.array()))).readObject();
                            console.println(response.getResponse());
                        }

                        return new ExecutionResponse("Команда " + command + " успешно исполнена");
                    } catch (IOException e) {
                        try {
                            socketChannel = SocketChannel.open();
                            socketChannel.connect(new InetSocketAddress("localhost", port));
//                            socketChannel.configureBlocking(false);
//                            selector = Selector.open();
//                            socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                        } catch (IOException ex) {
                        }
                        console.println("Проблемы с сервером, перезайдите позже");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
            }
        }
        return new ExecutionResponse("ad");
    }
}

