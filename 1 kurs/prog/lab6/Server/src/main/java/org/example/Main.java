package org.example;

import org.example.commands.*;
import org.example.console.Console;
import org.example.console.StandardConsole;
import org.example.managers.CollectionManager;
import org.example.managers.CommandManager;
import org.example.managers.DumpManager;
import org.example.runner.Runner;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static int port = 38423;
    //Создаем лог для Main
    public static final Logger logger = Logger.getLogger(Main.class.getName());
    private static final Map<SocketChannel, ByteBuffer> sockets = new ConcurrentHashMap<>();


    public static void main(String[] args) {

        var console = new StandardConsole();
        String value = System.getenv("LAB5_FILE");
        var dumpManager = new DumpManager(value, console);
        var collectionManager = new CollectionManager(dumpManager);


        try {
            Handler fileHandler = new FileHandler("C:\\Users\\crav4\\OneDrive\\Рабочий стол\\univer\\prog\\lab6\\Server\\src\\main\\java\\org\\example\\utils\\logs\\Main.xml");
            logger.addHandler(fileHandler);

            //Подъем сервера
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(port));
            //Логирование подключения сервера
            logger.severe("Сервер начал работу на порту " + port);

            //Принятие клиента


            //Загрузка коллекции из файла XML
            collectionManager.loadCollection();
            new Thread(new ConsoleMode(console,collectionManager)).start();
            while (true) {


                var commandManager = new CommandManager() {{
                    register("help", new Help(console, this));
                    register("info", new Info(console, collectionManager));
                    register("show", new Show(console, collectionManager));
                    register("add", new Add(console, collectionManager));
                    register("update", new Update(console, collectionManager));
                    register("remove_by_id", new RemoveById(console, collectionManager));
                    register("clear", new Clear(console, collectionManager));
                    register("save", new Save(collectionManager, false));
                    register("execute_script", new ExecuteScript(console));
                    register("exit", new Exit(console));
                    register("remove_greater", new RemoveGreater(console, collectionManager));
                    register("remove_last", new RemoveLast(console, collectionManager));
                    register("counter_greater_than_soundtrack_name", new CounterGreaterThanSoundtrackName(console, collectionManager));
                    register("count_by_mood", new CountByMood(console, collectionManager));
                    register("print_field_descending_car", new PrintFieldDescendingCar(console, collectionManager));
                    register("sort", new Sort(console, collectionManager));
                }};
                Runner runner = new Runner(console, serverSocketChannel, commandManager);

                runner.interactiveMode();
            }
        } catch (Exception e) {
            console.printError("Произошла непредвидемая ошибка");
            e.printStackTrace();
        }
    }
}
