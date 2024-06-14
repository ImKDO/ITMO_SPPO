package org.example.users;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.SocketChannel;
import java.sql.Connection;
import java.sql.SQLException;

import static org.example.Main.logger;
import static org.example.dataBase.UserLogin.userLogin;

public class LoginUser {
    public static String login(Connection connection, ObjectOutputStream oos, ObjectInputStream ois) {
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
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
