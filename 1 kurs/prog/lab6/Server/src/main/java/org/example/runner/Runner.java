package org.example.runner;

import org.apache.commons.lang3.SerializationUtils;
import org.example.Collection.ExecutionResponse;

import org.example.Response;
import org.example.SetCommand;
import org.example.commands.*;
import org.example.console.Console;
import org.example.managers.CommandManager;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Handler;

import static org.example.Main.logger;

/**
 * Класс для запуска программы и работы со скриптом
 */

public class Runner {
    private final Console console;

    private ServerSocketChannel serverSocketChannel;

    private final List<String> scriptStack = new ArrayList<>(1024);
    private SocketChannel socketChannel = SocketChannel.open();

    private int lengthRecursion = -1;
    private final CommandManager commandManager;
    List<Object> userCommand;

    public Runner(Console console, ServerSocketChannel serverSocketChannel, CommandManager commandManager) throws IOException {
        this.console = console;
        this.serverSocketChannel = serverSocketChannel;
        this.commandManager = commandManager;
    }

    /**
     * Интерактивный режим
     */
    public void interactiveMode() throws IOException {

        ExecutionResponse commandStatus;
        List<Object> userCommand = new ArrayList<>(3);
        try {
            socketChannel = serverSocketChannel.accept();
        } catch (IOException e) {
            logger.severe("Произошла ошибка при принятии клиента");
        }
        logger.info("Подключился клиент");
        userCommand.add("");
        userCommand.add("");
        userCommand.add("");
        userCommand.add("");
        userCommand.add("");
        while (true) {


            try {
                //Получение команды
                ObjectInputStream objectInputStream = new ObjectInputStream(socketChannel.socket().getInputStream());
                SetCommand setCommand = (SetCommand) objectInputStream.readObject();

                //Обработка введенных данных
                userCommand.set(0, setCommand.getCommand());
                userCommand.set(1, setCommand.getArgs());
                userCommand.set(2, setCommand.getHumanBeing());


                //Запуск команды
                commandStatus = launchCommand(userCommand);
                //Отправка ответа
                Response response = new Response(commandStatus.getMessage());
                byte[] byteRead = SerializationUtils.serialize(response);


                DataOutputStream objectOutputStream = new DataOutputStream(socketChannel.socket().getOutputStream());

                objectOutputStream.write(byteRead);

            } catch (SocketException e) {
                logger.severe("Отключение клиента от сервера");
//                try {
//                    socketChannel.close();
//                } catch (IOException ex) {
//                    console.println(socketChannel.socket());
//                }
                break;
            } catch (EOFException e) {
                console.println("Достигнут конец потока. Возможно, сервер закрыл соединение.");
//                try {
//                    socketChannel.close();
//                } catch (IOException ex) {
//                    console.println("1234567890");
//                }
                break;
            } catch (ClassNotFoundException e) {
                console.println("Класс сериализованного объекта не найден.");
                e.printStackTrace();
            } catch (IOException e) {
                logger.severe("Произошла ошибка ввода-вывода при чтении из потока. (Возможно клиент отключился)");
//                socketChannel.close();
                break;
            } catch (Exception e) {
                console.println("Произошла непредвиденная ошибка.");
                e.printStackTrace();
            }
        }
    }


    /**
     * Загружает команды.
     *
     * @param list Коллекция с командой для запуска
     * @return Код завершения.
     */
    private ExecutionResponse launchCommand(List<Object> list) {
        if ("".equals(list.get(0).toString())) return new ExecutionResponse("");
        Command command = commandManager.getCommands().get(list.get(0).toString());

        if (command == null)
            return new ExecutionResponse(false, "Команда '" + list.get(0).toString() + "' не найдена.");

        switch (list.get(0).toString()) {
            case "exit":
                Command save = commandManager.getCommands().get("save");
                save.setDropServer(true);
                save.apply(list);
            default:
                return command.apply(list);
        }
    }
}