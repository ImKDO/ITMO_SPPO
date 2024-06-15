package org.example.users;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.SocketChannel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import static org.example.Main.logger;
import static org.example.dataBase.UserLogin.userLogin;

public class LoginUser {
    public static String login(Connection connection, ObjectOutputStream oos, ObjectInputStream ois, Logger logger, SocketChannel socketChannel) throws IOException {
        try {
//            ObjectOutputStream oos = new ObjectOutputStream(socketChannel.socket().getOutputStream());
//            ObjectInputStream ois = new ObjectInputStream(socketChannel.socket().getInputStream());
            while (true) {
                String login = (String) ois.readObject();
                String password = (String) ois.readObject();


                if (userLogin(login, password, connection)) {
                    logger.info("Пользователь " + login + " вошел в систему");
                    oos.writeObject(true);
                    oos.flush();
                    return login;
                } else {
                    oos.writeObject(false);
                }
            }
        } catch (SQLException e) {
            logger.severe("Произошли технические шоколадки в IO с клиентом " + socketChannel.socket().getLocalSocketAddress());
        } catch (IOException e) {
            logger.severe("Произошли технические шоколадки в IO с клиентом " + socketChannel.socket().getLocalSocketAddress());
        } catch (ClassNotFoundException e) {
            logger.severe("Произошли технические шоколадки в сериализации с клиентом " + socketChannel.socket().getRemoteSocketAddress());
        }
        return "";
    }
}
