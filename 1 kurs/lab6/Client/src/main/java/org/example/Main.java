package org.example;

import net.bytebuddy.asm.Advice;
import org.example.commands.*;
import org.example.console.Console;
import org.example.console.StandardConsole;
import org.example.runner.Runner;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.apache.commons.lang3.SerializationUtils.serialize;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static int port = 38423;

    public static void main(String[] args) throws IOException, InterruptedException {

        var console = new StandardConsole();
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", port));
        socketChannel.configureBlocking(false);


        console.println("Клиент подключился к серверу");
        var commandManager = new CommandManager() {{
            register("update", new Update(console, socketChannel));
            register("help", new Help(console, socketChannel));
//                register("info", new Info(console, collectionManager));
            register("show", new Show(console));
            register("add", new Add(console));
//                register("remove_by_id", new RemoveById(console, collectionManager));
//                register("clear", new Clear(console, collectionManager));
//                register("save", new Save(collectionManager));
//                register("execute_script", new ExecuteScript(console));
            register("exit", new Exit(console));
//                register("remove_greater", new RemoveGreater(console, collectionManager));
//                register("remove_last", new RemoveLast(console, collectionManager));
//                register("counter_greater_than_soundtrack_name", new CounterGreaterThanSoundtrackName(console, collectionManager));
//                register("count_by_mood", new CountByMood(console, collectionManager));
//                register("print_field_descending_car", new PrintFieldDescendingCar(console, collectionManager));
//                register("sort", new Sort(console, collectionManager));
        }};

        try {
            new Runner(console, commandManager, socketChannel).interactiveMode();
            socketChannel.close();
        } catch (Exception e) {

        }
    }

}