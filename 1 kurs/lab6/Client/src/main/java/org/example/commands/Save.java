//package org.example.commands;
//
//
//
//
//import javax.xml.bind.JAXBException;
//import java.io.IOException;
//import org.example.commands.abstractCommandClass.Command;
//
///**
// * "save" - сохранить коллекцию в файл
// */
//public class Save extends Command {
//    private CollectionManager collectionManager;
//
//    public Save(CollectionManager collectionManager) {
//        super("save", "сохранить коллекцию в файл");
//
//    }
//    @Override
//    public ExecutionResponse apply(String argument) {
//
//        try {
//            collectionManager.saveCollection();
//            return new ExecutionResponse("Коллекция успешно сохранена в файл");
//        } catch (IOException e){
//            return new ExecutionResponse("Коллекция пуста");
//        } catch (JAXBException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
