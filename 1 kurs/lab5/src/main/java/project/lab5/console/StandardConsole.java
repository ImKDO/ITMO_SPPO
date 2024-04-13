package project.lab5.console;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class StandardConsole implements Console {


    private static final String P = "$ ";

    private static Scanner fileScanner = null;

    private static Scanner defScanner = new Scanner(System.in);

    /**
     * Выводит obj.toString() в консоль
     * @param o Объект для печати
     */
    public void print(Object o){
        System.out.print(o);
    }

    /**
     * Выводит obj.toString() + \n в консоль
     * @param o Объект для печати
     */
    public void println(Object o){
        System.out.println(o);
    }

    /**
     * Выводит ошибка: obj.toString() в консоль
     * @param o Ошибка для печати
     */
    public void printError(Object o){
        System.out.print("Error:" + o);
    }

    /**
     * Считать строку;
     */
    public String readln() throws NoSuchElementException, IllegalStateException{
        return (fileScanner != null?fileScanner:defScanner).nextLine();
    }

    /**
     * Возможно ли считать строку
     */
    @Override
    public boolean isCanReadln() throws IllegalStateException {
        return (fileScanner!=null?fileScanner:defScanner).hasNextLine();
    }
    /**
     * Выводит таблицу из 2 колонок
     * @param elementLeft Левый элемент колонки.
     * @param elementRight Правый элемент колонки.
     */
    @Override
    public void printTable(Object elementLeft, Object elementRight) {
        System.out.printf(" %-35s%-1s%n", elementLeft, elementRight);
    }
    /**
     * Выводит prompt1 текущей консоли
     */
    public void prompt(){
        print(P);
    }
    /**
     * @return prompt1 текущей консоли
     */
    public String getPrompt(){
        return P;
    }
    public void selectFileScanner(Scanner scanner){
        this.fileScanner = scanner;
    }
    public void selectConsoleScanner(){
        this.fileScanner = null;
    }
}
