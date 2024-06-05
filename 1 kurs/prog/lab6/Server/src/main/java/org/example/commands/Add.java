package org.example.commands;

import org.example.managers.CollectionManager;
import org.example.Collection.ExecutionResponse;
import org.example.console.Console;
import org.example.models.HumanBeing;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 'add'- добавить новый элемент в коллекцию.
 */
public class Add extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    int cnt = 0;

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
    public ExecutionResponse apply(List<Object> list) {
        if(cnt == 0){
            ((HumanBeing) list.get(2)).setCreationDate(LocalDateTime.now());
            ((HumanBeing) list.get(2)).setId(collectionManager.nextId());
            collectionManager.add((HumanBeing) list.get(2));
            ++cnt;
            return new ExecutionResponse("HumanBeing успешно добавлен!");
        } else {
            return new ExecutionResponse("");
        }
    }
}
