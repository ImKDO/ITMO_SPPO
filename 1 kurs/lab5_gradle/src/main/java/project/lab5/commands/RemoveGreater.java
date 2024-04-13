package project.lab5.commands;

import project.lab5.Collection.ExecutionResponse;
import project.lab5.console.Console;
import project.lab5.managers.CollectionManager;

/**
 * "remove_greater" - удалить из коллекции все элементы, превышающие element
 */
public class RemoveGreater extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public RemoveGreater(Console console, CollectionManager collectionManager) {
        super("remove_greater {element}", "удалить из коллекции все элементы, превышающие element");
        this.console = console;
        this.collectionManager = collectionManager;
    }
    @Override
    public ExecutionResponse apply(String arguments) {
        if (arguments.isEmpty()) return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");

        int count = 0;
        long id = 0;
        try {
            id = Long.parseLong(arguments.trim());
        } catch (NumberFormatException e) {
            return new ExecutionResponse(false, "ID не распознан");
        }
        final int  size = collectionManager.getCollection().size();
        for (int element = 0; element < size; element++) {
            if(collectionManager.getCollection().get(element - count).getId() > id){
//                System.out.println();
                collectionManager.getCollection().remove(element - count);
                count++;
            }
        }
        return new ExecutionResponse(" все HumanBeing с большим id удалились");
    }
}
