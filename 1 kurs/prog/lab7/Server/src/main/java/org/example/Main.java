package org.example;

import org.example.commands.*;
import org.example.console.Console;
import org.example.console.StandardConsole;
import org.example.dataBase.ConnectionManager;
import org.example.managers.CollectionManager;
import org.example.managers.CommandManager;
import org.example.models.*;
import org.example.runner.Runner;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static int port = 38423;
    //Создаем лог для Main
    public static final Logger logger = Logger.getLogger(Main.class.getName());
    private static SocketChannel socketChannel;


    public static void main(String[] args) {

        var console = new StandardConsole();
        var collectionManager = new CollectionManager();


        try (var connection = ConnectionManager.open();
             var statement = connection.createStatement()) {

            Handler fileHandler = new FileHandler("C:\\Users\\crav4\\OneDrive\\Рабочий стол\\univer\\prog\\lab6\\Server\\src\\main\\java\\org\\example\\utils\\logs\\Main.xml");
            logger.addHandler(fileHandler);

            //Подъем сервера
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(port));
            new Thread(new ConsoleMode(console, collectionManager)).start();
            socketChannel = SocketChannel.open();
            //Логирование подключения сервера
            logger.severe("Сервер начал работу на порту " + port);


            try {
                var commandManager = new CommandManager() {{
                    register("help", new Help(console, this));
                    register("info", new Info(console, collectionManager));
                    register("show", new Show(console, collectionManager, statement));
                    register("add", new Add(console, collectionManager, connection));
                    register("update", new Update(console, collectionManager));
                    register("remove_by_id", new RemoveById(console, collectionManager));
                    register("clear", new Clear(console, collectionManager, connection));
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


                String sql = """
                        SELECT * FROM collections
                        """;
                String sqlCount = """
                        SELECT COUNT(*) FROM collections;
                        """;
                int count = 0;
                try {
                    ResultSet resultSet = statement.executeQuery(sqlCount);
                    while (resultSet.next()) {
                        count += resultSet.getInt(1);
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                try {
                    ResultSet rs = statement.executeQuery(sql);
                    while (rs.next()) {
                        String user_fk = rs.getString("user_fk");
                        int id_object = rs.getInt("id_object");
                        String name = rs.getString("name");
                        Coordinates coordinates = new Coordinates(rs.getLong("x"), rs.getDouble("y"));
                        LocalDateTime creationdate = rs.getTimestamp("creationdate").toLocalDateTime();
                        boolean realHero = rs.getBoolean("realhero");
                        Boolean hastoothpick = rs.getBoolean("hastoothpick");
                        double impactspeed = rs.getDouble("impactspeed");
                        String soundtrackname = rs.getString("soundtrackname");
                        WeaponType weaponType = (WeaponType) rs.getObject("weapontype");
                        String moodStr = (rs.getString("mood"));
                        Mood mood = Mood.valueOf(moodStr.toUpperCase());
                        Car car = new Car(rs.getString("car_name"), rs.getBoolean("car_cool"));

                        HumanBeing humanBeing = new HumanBeing(
                                user_fk,
                                id_object,
                                name,
                                coordinates,
                                realHero,
                                hastoothpick,
                                impactspeed,
                                creationdate,
                                soundtrackname,
                                weaponType,
                                mood,
                                car);
                        collectionManager.add(humanBeing);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                console.println(count);
                Runner runner = new Runner(console, serverSocketChannel, commandManager, socketChannel);
                runner.run();
            } catch (IOException e) {
                console.printError("Произошла непредвидемая ошибка");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
