package org.example.commands;




import org.example.commands.abstractCommandClass.Command;

/**
 * "save" - сохранить коллекцию в файл
 */
public class Save extends Command {

    public Save() {
        super("save", "сохранить коллекцию в файл");

    }
    @Override
    public ExecutionResponse apply(String argument) {

        return new ExecutionResponse(true, "заглушка");
    }
}
