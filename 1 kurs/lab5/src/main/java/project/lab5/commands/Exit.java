package project.lab5.commands;

import project.lab5.Collection.ExecutionResponse;
import project.lab5.console.Console;

public class Exit extends Command {
    private final Console console;

    public Exit(Console console){
        super("exit", "Выйти из программы без сохранения");
        this.console = console;
    }
    @Override
    public ExecutionResponse apply(String argument) {
            console.println("Выход из программы");
            System.exit(1);
            // Если тебе не нравится стидеть в моей программе - пошел нахуй
            return new ExecutionResponse("huy pososi");
    }
}
