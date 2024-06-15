package org.example;

import org.example.commands.*;
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
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.*;

import static org.example.users.LoginUser.login;
import static org.example.users.RegistUser.regist;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static int port = 1242;
    public static final Logger logger = Logger.getLogger(Main.class.getName());
    private static final ExecutorService clientHandlerPool = Executors.newCachedThreadPool();
    private static final ReadWriteLock lock = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        var console = new StandardConsole();
        var collectionManager = new CollectionManager();

        try {
            var connection = ConnectionManager.open();
            var statement = connection.createStatement();
            Handler fileHandler = new FileHandler("C:\\Users\\crav4\\OneDrive\\Рабочий стол\\univer\\prog\\lab6\\Server\\src\\main\\java\\org\\example\\utils\\logs\\Main.xml");
            logger.addHandler(fileHandler);

            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(true);
            logger.severe("Сервер начал работу на порту " + port);

            var commandManager = initializeCommandManager(console, collectionManager, connection, statement);

            loadInitialData(statement, collectionManager, console);
            new Thread(new ConsoleMode(console, collectionManager)).start();

            while (true) {
                SocketChannel clientChannel = serverSocketChannel.accept();
                logger.info("Подключился клиент");
                clientHandlerPool.submit(() -> handleClient(clientChannel, connection, commandManager, console, serverSocketChannel));
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static CommandManager initializeCommandManager(StandardConsole console, CollectionManager collectionManager, Connection connection, java.sql.Statement statement) {
        return new CommandManager() {{
            register("help", new Help(console, this));
            register("info", new Info(console, collectionManager, connection));
            register("show", new Show(console, collectionManager, statement));
            register("add", new Add(console, collectionManager, connection));
            register("update", new Update(console, collectionManager, connection));
            register("remove_by_id", new RemoveById(console, collectionManager, connection));
            register("clear", new Clear(console, collectionManager, connection));
            register("save", new Save(collectionManager, false));
            register("execute_script", new ExecuteScript(console));
            register("exit", new Exit(console));
            register("remove_greater", new RemoveGreater(console, collectionManager));
            register("remove_last", new RemoveLast(console, collectionManager, connection));
            register("counter_greater_than_soundtrack_name", new CounterGreaterThanSoundtrackName(console, collectionManager));
            register("count_by_mood", new CountByMood(console, collectionManager));
            register("print_field_descending_car", new PrintFieldDescendingCar(console, collectionManager));
            register("sort", new Sort(console, collectionManager));
        }};
    }

    private static void loadInitialData(java.sql.Statement statement, CollectionManager collectionManager, StandardConsole console) throws SQLException {
        String sql = "SELECT * FROM collections";
        String sqlCount = "SELECT COUNT(*) FROM collections;";
        int count = 0;

        ResultSet rs = statement.executeQuery(sqlCount);
        while (rs.next()) {
            count += rs.getInt(1);
        }
        rs.close();

        rs = statement.executeQuery(sql);
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
            String weaponTypeStr = rs.getString("weapontype");
            WeaponType weaponType = null;
            if (weaponTypeStr != null) {
                weaponType = WeaponType.valueOf(weaponTypeStr);
            }
            String moodStr = rs.getString("mood");
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
        console.println(count);
    }

    private static void handleClient(SocketChannel clientChannel, Connection connection, CommandManager commandManager, StandardConsole console, ServerSocketChannel serverSocketChannel) {
        try  {
            ObjectOutputStream oos = new ObjectOutputStream(clientChannel.socket().getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(clientChannel.socket().getInputStream());
            String request = (String) ois.readObject();
            String login;
            if ("reg".equals(request.trim())) {
                login = regist(ois, oos, connection, logger, clientChannel);
            } else {
                login = login(connection, oos, ois, logger, clientChannel);
            }

            Runner runner = new Runner(console, serverSocketChannel, commandManager,clientChannel,  login);
            runner.run();

        } catch (IOException | ClassNotFoundException e) {
            logger.severe("Ошибка при обработке клиента: " + e.getMessage());
            e.printStackTrace();
        } finally {
        }
    }
}