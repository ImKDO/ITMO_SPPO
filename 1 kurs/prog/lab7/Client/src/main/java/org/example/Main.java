package org.example;

import org.example.commands.*;
import org.example.console.StandardConsole;
import org.example.runner.Runner;
import org.example.CommandManager;
import org.example.users.Login;
import org.example.users.Register;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import static org.apache.commons.lang3.SerializationUtils.serialize;
import static org.example.users.Login.login;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static int port = 1242;

    public static void main(String[] args) {
        SocketChannel socketChannel = null;
        var console = new StandardConsole();

        var commandManager = new CommandManager() {{
            register("update", new Update(console));
            register("help", new Help(console));
            register("info", new Info(console));
            register("show", new Show(console));
            register("add", new Add(console));
            register("remove_by_id", new RemoveById(console));
            register("clear", new Clear(console));
            register("execute_script", new ExecuteScript(console));
            register("remove_greater", new RemoveGreater(console));
            register("remove_last", new RemoveLast(console));
            register("counter_greater_than_soundtrack_name", new CounterGreaterThanSoundtrackName(console));
            register("count_by_mood", new CountByMood(console));
            register("print_field_descending_car", new PrintFieldDescendingCar(console));
            register("sort", new Sort(console));
        }};

        try {
            // Подключение клиента к серверу
            socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("localhost", port));

            // Авторизация или регистрация
            String login = "";
            while (true) {
                console.println("Регистрация или авторизация? (reg, log)");
                var line = console.readln();
                if (line == null) {
                    System.exit(0);
                }
                line = line.trim();
                if (line.equals("reg")) {
                    Register registerUser = new Register(console, socketChannel);
                    login = registerUser.register();
                    break;
                } else if (line.equals("log")) {
                    login(console, socketChannel);
                    break;
                }
            }

            new Runner(login, console, commandManager, socketChannel).interactiveMode();
        } catch (ClosedChannelException e) {
            console.printError("Сервер с портом " + port + " в данный момент недоступен");
        } catch (IOException | ClassNotFoundException e) {
            console.printError("Произошла проблема с обменом данных: " + e.getMessage());
        } finally {
            try {
                if (socketChannel != null && socketChannel.isOpen()) {
                    socketChannel.close();
                }
            } catch (IOException e) {
                console.printError("Ошибка при закрытии соединения: " + e.getMessage());
            }
        }
    }
}