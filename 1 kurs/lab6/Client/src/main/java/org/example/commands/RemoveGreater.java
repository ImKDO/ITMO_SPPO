//package org.example.commands;
//
//
//import org.example.console.Console;
//
//import org.example.commands.abstractCommandClass.Command;
//
///**
// * "remove_greater" - удалить из коллекции все элементы, превышающие element
// */
//public class RemoveGreater extends Command {
//    private final Console console;
//
//
//    public RemoveGreater(Console console) {
//        super("remove_greater {element}", "удалить из коллекции все элементы, превышающие element");
//        this.console = console;
//
//    }
//    @Override
//    public ExecutionResponse apply(String arguments) {
//        if (arguments.isEmpty()) return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
//
//        int count = 0;
//        long id = 0;
//        try {
//            id = Long.parseLong(arguments.trim());
//        } catch (NumberFormatException e) {
//            return new ExecutionResponse(false, "ID не распознан");
//        }
//        final int  size = collectionManager.getCollection().size();
//        for (int element = 0; element < size; element++) {
//            if(collectionManager.getCollection().get(element - count).getId() > id){
////                console.println();
//                collectionManager.getCollection().remove(element - count);
//                count++;
//            }
//        }
//        return new ExecutionResponse(" все HumanBeing с большим id удалились");
//    }
//}
