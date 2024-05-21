package org.example.commands;

import org.example.managers.CollectionManager;
import org.example.Collection.ExecutionResponse;
import org.example.console.Console;
import org.example.models.HumanBeing;

import java.nio.ByteBuffer;

/**
 * 'add'- добавить новый элемент в коллекцию.
 */
public class Add extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Add(Console console, CollectionManager collectionManager) {
        super("add {element}", "добавить новый элемент в коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     *
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(String arguments) {

//        HumanBeing o = new Ask(console).askHumanBeing(collectionManager.nextId());
        ByteBuffer buffer = ByteBuffer.wrap(arguments.getBytes());
        if (true) {
//            collectionManager.add(o);
            return new ExecutionResponse("HumanBeing успешно добавлен!");
        } else return new ExecutionResponse(false,"HumanBeing не создана!");
    }
}
