package org.example.users;

import org.example.console.Console;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;

public class Login {
    public static void login(Console console, SocketChannel socketChannel) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socketChannel.socket().getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socketChannel.socket().getInputStream());

            oos.writeObject("log");
            while (true) {

                console.println("Введите логин");
                String login = console.readln();
                login = login.trim();

                oos.writeObject(login);


                console.println("Введите пароль");
                String password = console.readln();
                password = password.trim();
                oos.writeObject(password);

                boolean responsePw = (boolean) ois.readObject();

                if (responsePw) {
                    console.println("Успешная авторизация!");
                    break;
                } else {
                    console.println("Неправильно, попробуй еще раз");
                }
            }
        } catch (IOException e) {
            console.println("Шоколадки с IO");
        } catch (ClassNotFoundException e) {
            console.println("Шоколадки с сериализацией");
        }
    }
}
