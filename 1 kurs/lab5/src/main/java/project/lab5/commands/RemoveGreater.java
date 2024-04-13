package project.lab5.commands;

import project.lab5.Collection.ExecutionResponse;
import project.lab5.console.Console;
import project.lab5.managers.CollectionManager;

/**
 * Команда remove_greater - удаляет из коллекции все элементы большие element
 */
public class RemoveGreater extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public RemoveGreater(Console console, CollectionManager collectionManager) {
        super("remove_greater {element}", "удалить из коллекции все элементы, превышающие заданный");
        this.console = console;
        this.collectionManager = collectionManager;
    }
    @Override
    public ExecutionResponse apply(String arguments) {
        if (arguments.isEmpty()) return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");

        console.println("Введите id элемента, после которого все HumanBeing с большим id удалятся ");
        Integer id = Integer.valueOf(console.readln().trim());
        Integer sizeCollection = collectionManager.getCollection().size();
        int count = 0;

        for (int element = 0; element < sizeCollection; element++) {
            if(collectionManager.getCollection().get(element - count).getId() > id){
                collectionManager.getCollection().remove(element - count);
                count++;
            }
        }
        return new ExecutionResponse(" все HumanBeing с большим id удалились");
    }
}
