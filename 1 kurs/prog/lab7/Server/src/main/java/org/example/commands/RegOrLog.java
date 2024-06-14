//package org.example.commands;
//
//import org.example.Collection.ExecutionResponse;
//import org.example.console.Console;
//
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.nio.channels.SocketChannel;
//import java.sql.Connection;
//import java.util.List;
//
//import static org.example.users.LoginUser.login;
//import static org.example.users.RegistUser.regist;
//
//public class RegOrLog extends Command {
//    private Console console;
//    private Connection connection;
//
//    public RegOrLog(Console console, Connection connection) {
//        super("reg | log", "регистрация или вход в аккаунт");
//        this.console = console;
//        this.connection = connection;
//    }
//
//    @Override
//    public ExecutionResponse LogOrReg(List<String> list){
//        ObjectOutputStream oos;
//        ObjectInputStream ois;
//
//        try {
//             oos = new ObjectOutputStream(socketChannel.socket().getOutputStream());
//             ois = new ObjectInputStream(socketChannel.socket().getInputStream());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            String request = (String) ois.readObject();
//            if (request.trim().equals("reg")) {
//                login = regist(ois, oos, connection);
//            } else if (request.equals("log")) {
//                login = login(ois, oos, connection);
//            }
//        } catch (ClassNotFoundException | IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
