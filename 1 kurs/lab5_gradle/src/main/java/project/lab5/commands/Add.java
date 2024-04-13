package project.lab5.commands;

import project.lab5.AskManager.Ask;
import project.lab5.managers.CollectionManager;
import project.lab5.Collection.ExecutionResponse;
import project.lab5.console.Console;
import project.lab5.models.HumanBeing;

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

        HumanBeing o = new Ask(console).askHumanBeing(collectionManager.nextId());

        if (o != null && o.validate()) {
            collectionManager.add(o);
            return new ExecutionResponse("HumanBeing успешно добавлен!");
        } else return new ExecutionResponse(false,"HumanBeing не создана!");
    }
}
