//package org.example.commands;
//
//
//import org.example.console.Console;
//
//import org.example.models.Mood;
//import org.example.commands.abstractCommandClass.Command;
//
//
//public class CountByMood extends Command {
//    private final Console console;
//
//
//    public CountByMood(Console console) {
//        super("count_by_mood mood", "показать количество коллекций с заданным mood");
//        this.console = console;
//
//    }
//
//    @Override
//    public ExecutionResponse apply(String argument) {
//        if (argument.isEmpty()) {
//            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
//        } else {
//            Mood mood;
//            try {
//                mood = Mood.valueOf(argument);
//            } catch (Exception e) {
//                return new ExecutionResponse(false, "Введите правильно mood");
//            }
//            return new ExecutionResponse("Количество " + mood + " в коллекции равно " + collectionManager.countByMood(mood));
//        }
//    }
//}
