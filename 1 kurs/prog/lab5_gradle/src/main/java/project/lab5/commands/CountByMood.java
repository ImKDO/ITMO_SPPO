package project.lab5.commands;

import project.lab5.Collection.ExecutionResponse;
import project.lab5.console.Console;
import project.lab5.managers.CollectionManager;
import project.lab5.models.Mood;

public class CountByMood extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public CountByMood(Console console, CollectionManager collectionManager) {
        super("count_by_mood mood", "показать количество коллекций с заданным mood");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String argument) {
        if (argument.isEmpty()) {
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        } else {
            Mood mood;
            try {
                mood = Mood.valueOf(argument);
            } catch (Exception e) {
                return new ExecutionResponse(false, "Введите правильно mood");
            }
            return new ExecutionResponse("Количество " + mood + " в коллекции равно " + collectionManager.countByMood(mood));
        }
    }
}
