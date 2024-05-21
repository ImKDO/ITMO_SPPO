package org.example.commands;

import org.example.Collection.ExecutionResponse;
import org.example.managers.CollectionManager;

import javax.xml.bind.JAXBException;
import java.io.IOException;

/**
 * "save" - сохранить коллекцию в файл
 */
public class Save extends Command {
    private CollectionManager collectionManager;

    public Save(CollectionManager collectionManager) {
        super("save", "сохранить коллекцию в файл");
        this.collectionManager = collectionManager;
    }
    @Override
    public ExecutionResponse apply(String argument) {

        try {
            collectionManager.saveCollection();
            return new ExecutionResponse("Коллекция успешно сохранена в файл");
        } catch (IOException e){
            return new ExecutionResponse("Коллекция пуста");
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
