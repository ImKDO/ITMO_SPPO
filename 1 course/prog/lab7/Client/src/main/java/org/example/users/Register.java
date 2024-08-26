package org.example.users;

import org.example.console.Console;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class Register {
    Console console;
    SocketChannel socketChannel;
    public Register(Console console, SocketChannel socketChannel) {
        this.console = console;
        this.socketChannel = socketChannel;
    }

    public  String register() throws IOException, ClassNotFoundException {
        boolean response = true;
        ObjectOutputStream oos = new ObjectOutputStream(socketChannel.socket().getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socketChannel.socket().getInputStream());
        oos.writeObject("reg");

        String login = "";

        while(response){

            console.prompt();

            console.println("Введите логин");
            login = console.readln();

            oos.writeObject(login);
            oos.flush();


            response = (boolean) ois.readObject();
            console.println(response);
            if(response) {
                console.println("Пользователь с таким логином уже зарегистрирован!");
                continue;
            }
            console.prompt();
            console.println("Введите пароль");
            String password = console.readln();

            oos.writeObject(password);
            oos.flush();

            break;
        }
        console.println("Пользователь успешно зарегистрирован");
        return login;
    }
}
