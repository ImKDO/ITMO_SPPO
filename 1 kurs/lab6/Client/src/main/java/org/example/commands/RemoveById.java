//package org.example.commands;
//
//
//import org.example.console.Console;
//
//import org.example.commands.abstractCommandClass.Command;
//
///**
// *  "remove_by_id id" - удалить элемент по id
// */
//public class RemoveById extends Command {
//    private final Console console;
//
//
//    public RemoveById(Console console) {
//        super("remove_by_id id", "удалить элемент из коллекции по ID");
//        this.console = console;
//
//    }
//
//    /**
//     * Выполняет команду
//     *
//     * @return Успешность выполнения команды.
//     */
//    @Override
//    public ExecutionResponse apply(String arguments) {
//        if (arguments.isEmpty())
//            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
//        long id = 0;
//        try {
//            id = Long.parseLong(arguments.trim());
//        } catch (NumberFormatException e) {
//            return new ExecutionResponse(false, "ID не распознан");
//        }
//        for (int element = 0; element < collectionManager.getCollection().size(); element++) {
//            if(collectionManager.getCollection().get(element).getId() == (int) id){
//                collectionManager.getCollection().remove(element);
//                return new ExecutionResponse( "HumanBeing удален");
//            }
//
//        }
//        return new ExecutionResponse(false, "Не существующий ID");
//    }
//}
