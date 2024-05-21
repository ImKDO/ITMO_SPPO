//package org.example.commands;
//
//
//import org.example.console.Console;
//
//import org.example.models.HumanBeing;
//
//import java.util.Collections;
//import java.util.Comparator;
//import org.example.commands.abstractCommandClass.Command;
//
///**
// * Команда sort - сортирует коллекцию в естественном порядке
// */
//public class Sort extends Command{
//    private final Console console;
//    private CollectionManager collectionManager;
//
//    public Sort(Console console) {
//        super("sort", "сортирует коллекцию в естественном порядке ");
//        this.console = console;
//
//    }
//
//
//
//    @Override
//    public ExecutionResponse apply(String arguments) {
//        if (arguments.isEmpty())
//            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
//
//        Comparator<HumanBeing> sortHumanBeing = new sortHumanBeing();
//        Collections.sort(collectionManager.getCollection(), sortHumanBeing);
//
//        return new ExecutionResponse("Коллекция отсортировна!");
//    }
//}
