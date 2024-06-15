package org.example.runner;

import org.example.Collection.ExecutionResponse;

import org.example.SetCommand;
import org.example.console.Console;
import org.example.dataBase.ConnectionManager;
import org.example.managers.CommandManager;

import java.io.*;
import java.net.SocketException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.sql.Connection;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static org.example.Main.logger;
import static org.example.runner.LaunchCommand.launchCommand;
import static org.example.runner.ResponseCommand.response;
import static org.example.users.LoginUser.login;
import static org.example.users.RegistUser.regist;

/**
 * Класс для запуска программы и работы со скриптом
 */

public class Runner {
    private final Console console;

    private ServerSocketChannel serverSocketChannel;
    private SocketChannel socketChannel;
    private static final ForkJoinPool responseSendingPool = new ForkJoinPool();
    private final CommandManager commandManager;

    private final ExecutorService requestReaderPool;
    private final ExecutorService requestProcessorPool;
    private final ForkJoinPool responseSenderPool;

    private final String login;

    private static final ReadWriteLock lock = new ReentrantReadWriteLock();


    public Runner(Console console, ServerSocketChannel serverSocketChannel, CommandManager commandManager, SocketChannel socketChannel, String login) throws IOException {
        this.console = console;
        this.serverSocketChannel = serverSocketChannel;
        this.commandManager = commandManager;
        this.socketChannel = socketChannel;
        this.login = login;

        this.requestReaderPool = Executors.newCachedThreadPool();
        this.requestProcessorPool = Executors.newCachedThreadPool();
        this.responseSenderPool = new ForkJoinPool();
    }

    /**
     * Интерактивный режим
     */
    public void run() {
//        while (true) {
        List<Object> userCommand = new ArrayList<>(3);
        userCommand.add("");
        userCommand.add("");
        userCommand.add("");
        userCommand.add("");
        userCommand.add("");
        userCommand.add("");


        requestReaderPool.execute(() -> {
            try {
                while (true) {
                    ObjectInputStream objectInputStream = new ObjectInputStream(socketChannel.socket().getInputStream());
                    //Получение команды
                    SetCommand setCommand = (SetCommand) objectInputStream.readObject();
                    console.println("adasdsa");
                    console.println(setCommand.getCommand());
                    //Обработка введенных данных
                    userCommand.set(0, setCommand.getCommand());
                    userCommand.set(1, setCommand.getArgs());
                    userCommand.set(2, setCommand.getHumanBeing());
                    userCommand.set(3, login);
                    RequestProcessorPool(userCommand);
                }

            } catch (SocketException e) {
                logger.severe("Отключение клиента от сервера");
                try {
                    socketChannel = serverSocketChannel.accept();
                    logger.info("Подключился клиент");
                } catch (IOException ex) {
                    logger.info("Проблемы с IO");
                }
//                break;
            } catch (EOFException e) {

                console.println("Достигнут конец потока. Возможно, сервер закрыл соединение.");
//                break;
            } catch (ClassNotFoundException e) {
                console.println("Класс сериализованного объекта не найден.");

            } catch (IOException e) {
                logger.severe("Произошла ошибка ввода-вывода при чтении из потока. (Возможно клиент отключился)");
                e.printStackTrace();
//                break;
            } catch (Exception e) {
                console.println("Произошла непредвиденная ошибка.");

            }

        });
//        }
    }

    private void RequestProcessorPool(List<Object> userCommand) {
        requestProcessorPool.execute(() -> {
            lock.writeLock().lock();
            ExecutionResponse commandStatus;
            try {
                //Запуск команды
                logger.info("Сервер получил запрос команды " + userCommand.get(0));
                commandStatus = launchCommand(userCommand, commandManager);
            } finally {
                lock.writeLock().unlock();
            }
            sendingPool(userCommand, commandStatus);

        });
    }

    private void sendingPool(List<Object> userCommand, ExecutionResponse commandStatus) {
        responseSendingPool.execute(() -> {
            try {
                lock.readLock().lock();
                try {
                    //Отправка ответа
                    response(commandStatus.getMessage(), socketChannel);

                    logger.info("Сервер отправил ответ-исполнение команды " + userCommand.get(0));
                } finally {
                    lock.readLock().unlock();
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}