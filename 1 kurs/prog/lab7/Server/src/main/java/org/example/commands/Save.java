package org.example.commands;

import org.example.Collection.ExecutionResponse;
import org.example.managers.CollectionManager;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

import static org.example.Main.logger;

/**
 * "save" - сохранить коллекцию в файл
 */
public class Save extends Command {
    private CollectionManager collectionManager;
    private static boolean dropServer;

    public Save(CollectionManager collectionManager, boolean dropServer) {
        super("save", "сохранить коллекцию в файл");
        this.collectionManager = collectionManager;
        Save.dropServer = dropServer;
    }



    @Override
    public ExecutionResponse apply(List<Object> list) {
        if (!dropServer) {
            try {
                collectionManager.saveCollection();
                return new ExecutionResponse("Коллекция успешно сохранена в файл");
            } catch (IOException e) {
                return new ExecutionResponse("Коллекция пуста");
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                collectionManager.saveCollection();
                logger.severe("Дроп сервера");
                System.exit(0);
                return new ExecutionResponse("Коллекция успешно сохранена в файл");
            } catch (IOException e) {
                return new ExecutionResponse("Коллекция пуста");
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
