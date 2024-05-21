package org.example.commands;


import org.example.ExecutionResponse;
import org.example.commands.abstractCommandClass.Command;
import org.example.console.Console;


/**
 * 'add'- добавить новый элемент в коллекцию.
 */
public class Add extends Command {
    private final Console console;


    public Add(Console console) {
        super("add {element}", "добавить новый элемент в коллекцию");
        this.console = console;

    }

    /**
     * Выполняет команду
     *
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(String arguments) {

//        HumanBeing o = new Ask(console).askHumanBeing(collectionManager.nextId());

        if (true) {
//            collectionManager.add(o);
            return new ExecutionResponse("Заглушка");
        } else return new ExecutionResponse(false,"HumanBeing не создана!");
    }
}
