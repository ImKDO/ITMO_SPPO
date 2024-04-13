package project.lab5.commands;

import project.lab5.Collection.ExecutionResponse;
import project.lab5.managers.CollectionManager;

import java.io.IOException;

/**
 * Команда save - сохранить коллекцию в файл
 */
public class Save extends Command {
    private CollectionManager collectionManager;

    public Save(CollectionManager collectionManager) {
        super("save", "Команда save - сохраняет коллекцию в файл");
        this.collectionManager = collectionManager;
    }
    @Override
    public ExecutionResponse apply(String argument) {
        if (argument.isEmpty()) return new ExecutionResponse(false,
                "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        try {
            collectionManager.saveCollection();
            return new ExecutionResponse("Коллекция успешно сохранена в файл");
        } catch (IOException e){
            return new ExecutionResponse("Коллекция пуста");
        }
    }
}
