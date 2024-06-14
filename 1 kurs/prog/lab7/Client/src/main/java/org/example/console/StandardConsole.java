package org.example.console;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Класс для работы с консолью
 */
public class StandardConsole implements Console, Serializable {

    private static String P = "$";
    private static Scanner fileScanner = null;
    private static Scanner defScanner = new Scanner(System.in);

    /**
     * Выводит o.toString() в консоль
     *
     * @param o Объект для печати
     */
    @Override
    public void print(Object o) {
        System.out.print(o);
    }

    /**
     * Выводит o.toString()+\n в консоль
     *
     * @param o Объект для печати
     */
    @Override
    public void println(Object o) {
        System.out.println(o);
    }

    /**
     * Считать строку
     * @return
     * @throws NoSuchElementException
     * @throws IllegalStateException
     */
    public String readln(){
        try {
            if((fileScanner != null ? fileScanner:defScanner).hasNextLine()){
                return (fileScanner != null ? fileScanner:defScanner).nextLine();
            } else {
                return defScanner.nextLine();
            }
        } catch (Exception e) {
            System.out.println("Произошла непредвидимая ошибка!");
            return null;
        }
    }

    /**
     * Проверяет можно ли дальше считывать данные из консоли.
     */
    @Override
    public boolean isCanReadln() throws IllegalStateException {
        return (fileScanner != null ? fileScanner : defScanner).hasNextLine();
    }

    /**
     * Напечатать ошибку
     * @param o
     */
    @Override
    public void printError(Object o) {
        System.err.println("Error." + o);
    }

    @Override
    public void prompt() {
        print(P);
    }

    @Override
    public String getPrompt() {
        return P;
    }

    @Override
    public void selectFileScanner(Scanner scanner) {
        StandardConsole.fileScanner = scanner;
    }

    @Override
    public void selectConsoleScanner() {
        StandardConsole.fileScanner = null;
    }

    @Override
    public Scanner getFileScanner() {
        return fileScanner;
    }
}


