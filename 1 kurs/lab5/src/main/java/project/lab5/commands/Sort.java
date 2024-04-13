//package project.lab5.commands;
//
//import project.lab5.Collection.ExecutionResponse;
//import project.lab5.console.Console;
//import project.lab5.managers.CollectionManager;
//
//import java.util.Collections;
//import java.util.Comparator;
//
///**
// * Команда sort - сортирует коллекцию в естественном порядке
// */
//public class Sort extends Command{
//    private final Console console;
//    private CollectionManager collectionManager;
//
//    public Sort(Console console, CollectionManager collectionManager) {
//        super("remove_last", "Команда remove_last - удаляет элемент коллекции по id");
//        this.console = console;
//        this.collectionManager = collectionManager;
//    }
//
//
//
//    @Override
//    public ExecutionResponse apply(String arguments) {
//        if (arguments.isEmpty())
//            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
//
//        Comparator sortHumanBeing = new sortHumanBeing();
//        Collections.sort(collectionManager.getCollection(), sortHumanBeing);
//
//        return new ExecutionResponse("Коллекция отсортировна!");
//    }
//}
