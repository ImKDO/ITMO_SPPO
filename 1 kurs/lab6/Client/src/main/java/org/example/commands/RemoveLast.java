//package org.example.commands;
//
//
//import org.example.console.Console;
//
//import org.example.commands.abstractCommandClass.Command;
//
///**
// * Команда remove_last - удаляет последний элемент в коллекции
// */
//public class RemoveLast extends Command {
//    private final Console console;
//    private CollectionManager collectionManager;
//
//    public RemoveLast(Console console) {
//        super("remove_last", "удаляет элемент коллекции по id");
//        this.console = console;
//
//    }
//
//    /**
//     * Выполнение команды remove_last
//     * @param arguments Аргумент для выполнения
//     * @return
//     */
//    public ExecutionResponse apply(String arguments) {
//        if (arguments.isEmpty())
//            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
//        if(!collectionManager.getCollection().isEmpty()) {
//            collectionManager.getCollection().remove(collectionManager.getCollection().size() - 1);
//            return new ExecutionResponse("Последний элемент коллекции удален");
//        }
//        else {
//            return new ExecutionResponse("Коллекция пуста");
//        }
//    }
//}
