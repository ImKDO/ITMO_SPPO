package org.example.users;

import org.example.console.Console;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.SocketChannel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import static org.example.Main.logger;
import static org.example.dataBase.UserRegistration.haveRegisterUser;
import static org.example.dataBase.UserRegistration.registerUser;

public class RegistUser {
    public static String regist(ObjectInputStream ois, ObjectOutputStream oos, Connection connection, Logger logger, SocketChannel socketChannel){
        while(true){
            try {
                String login = (String) ois.readObject();

                Boolean responseHaveRegisterUser = haveRegisterUser(login, connection);
                if (responseHaveRegisterUser) {
                    oos.writeObject(true);
                    continue;
                }
                oos.writeObject(false);
                String password = (String) ois.readObject();

                registerUser(login, password, connection);
                return login;
            } catch (IOException e) {
                logger.severe("Произошли технические шоколадки в IO с клиентом " + socketChannel.socket().getRemoteSocketAddress());
            } catch (SQLException e) {
                logger.severe("Произошли технические шоколадки с sql запросом с клиентом " + socketChannel.socket().getRemoteSocketAddress());
            } catch (ClassNotFoundException e) {
                logger.severe("Произошли технические шоколадки в сериализации с клиентом " + socketChannel.socket().getRemoteSocketAddress());

            }
        }
    }
}
