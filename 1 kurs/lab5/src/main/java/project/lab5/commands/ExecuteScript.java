package project.lab5.commands;


import project.lab5.Collection.ExecutionResponse;
import project.lab5.console.Console;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Команда execute_script - исполнить скрипт из указанного файла
 */
public class ExecuteScript extends Command {
    private final Console console;

    public ExecuteScript(Console console) {
        super("execute_script <file_name>", "исполнить скрипт из указанного файла");
        this.console = console;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(String arguments) {
        try(FileInputStream file=new FileInputStream("test_execute_script.txt")){
            if (arguments.isEmpty()) return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
            console.println("Напишите полный адрес к скрипту");
            System.setIn(file);
            return new ExecutionResponse("Выполнение скрипта '" + arguments + "'...");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}