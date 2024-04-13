package project.lab5.commands;

import project.lab5.Collection.ExecutionResponse;
import project.lab5.console.Console;
import project.lab5.managers.CollectionManager;
import project.lab5.models.HumanBeing;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Команда print_field_descending_car - выдает все car в порядке убывания
 */
public class PrintFieldDescendingCar extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public PrintFieldDescendingCar(Console console, CollectionManager collectionManager) {
        super("print_field_descending_car car", "Выдать все car в порядке убывания");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String arguments) {
        if (arguments.isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        ArrayList<String> nameCars = new ArrayList<>();
        for(HumanBeing element: collectionManager.getCollection()){
            nameCars.add(element.getCar().getName());
        }
        Collections.sort(nameCars);
        for(String element: nameCars){
            console.println(element);
        }
        return new ExecutionResponse("Команда успешно выполнена");
    }
}