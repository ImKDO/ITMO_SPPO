package org.example.users;

import org.example.console.Console;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.SQLException;

import static org.example.Main.logger;
import static org.example.dataBase.UserRegistration.haveRegisterUser;
import static org.example.dataBase.UserRegistration.registerUser;

public class RegistUser {
    public static String regist(ObjectInputStream ois, ObjectOutputStream oos, Connection connection){
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
                logger.severe("Произошла ошибка при принятии клиента");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
