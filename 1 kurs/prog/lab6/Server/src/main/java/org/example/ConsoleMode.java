package org.example;

import org.example.console.Console;
import org.example.managers.CollectionManager;
import org.example.runner.Runner;

import javax.xml.bind.JAXBException;
import java.io.IOException;

import static org.example.Main.logger;

public class ConsoleMode  implements Runnable{
    private Console console;
    private CollectionManager collectionManager;
    public ConsoleMode(Console console, CollectionManager collectionManager) {
        this.console = console;
        this.collectionManager = collectionManager;
    }
    @Override
    public void run() {
        while(true) {
            //Ввод с консоли
            console.prompt();
            String args = console.readln().trim();
            if(args.equals("exit")) {
                try {
                    collectionManager.saveCollection();
                } catch (IOException | JAXBException e) {
                    logger.severe("Непредвидимый разрыв соединения с клиентом");
                }
                logger.severe("Дроп сервера");
                System.exit(0);
            }
            if(args.equals("save")) {
                try {
                    collectionManager.saveCollection();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (JAXBException e) {
                    logger.severe("Непредвидимый разрыв соединения с клиентом");
                }
                logger.severe("Сохранение коллекции со стороны сервера");
            }
        }
    }
}
