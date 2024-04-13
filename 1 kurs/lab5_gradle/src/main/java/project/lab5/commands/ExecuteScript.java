package project.lab5.commands;


import project.lab5.Collection.Executable;
import project.lab5.Collection.ExecutionResponse;
import project.lab5.console.Console;

/**
 *  "execute_script" - исполнить скрипт из указанного файла
 */
public class ExecuteScript extends Command implements Executable {
    private final Console console;

    public ExecuteScript(Console console) {
        super("execute_script file_name", "исполнить скрипт из указанного файла");
        this.console = console;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(String arguments) {
        if (arguments.isEmpty()) return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        return new ExecutionResponse("Выполнение скрипта '" + arguments + "'...");
    }
}