package org.example.commands;

import org.example.Collection.ExecutionResponse;
import org.example.console.Console;
import org.example.managers.CollectionManager;
import org.example.models.Mood;

import java.util.List;

public class CountByMood extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public CountByMood(Console console, CollectionManager collectionManager) {
        super("count_by_mood mood", "показать количество коллекций с заданным mood");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(List<Object> list) {
        if (((String) list.get(1)).isEmpty()) {
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        } else {
            Mood mood;
            try {
                mood = Mood.valueOf(list.get(1).toString());
            } catch (Exception e) {
                return new ExecutionResponse(false, "Введите правильно mood");
            }
            return new ExecutionResponse("Количество " + mood + " в коллекции равно " + collectionManager.countByMood(mood));
        }
    }
}
