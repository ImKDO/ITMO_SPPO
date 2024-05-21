//package org.example.commands;
//
//
//import org.example.console.Console;
//
//import org.example.commands.abstractCommandClass.Command;
//
///**
// *  "counter_greater_than_soundtrack_name" soundtrack - считает количество элементов с заданным soundtrack
// */
//public class CounterGreaterThanSoundtrackName extends Command {
//    private final Console console;
//
//
//    public CounterGreaterThanSoundtrackName(Console console) {
//        super("counter_greater_than_soundtrack_name soundtrack ", "показать количество коллекций с заданным soundtrackName");
//        this.console = console;
//
//    }
//
//    @Override
//    public ExecutionResponse apply(String argument) {
//        if (argument.isEmpty()) {
//            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
//        }
//        String soundtrack = argument;
//        int count = 0;
//        for (int element = 0; element < collectionManager.getCollection().size(); element++) {
//            if (collectionManager.getCollection().get(element).getSoundtrackName().equals(soundtrack)) {
//                count++;
//            }
//        }
//        return new ExecutionResponse("Количество " + soundtrack + " в коллекции равно " + count);
//    }
//}
