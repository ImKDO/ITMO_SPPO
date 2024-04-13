package project.lab5.commands;

import project.lab5.AskManager.Ask;
import project.lab5.Collection.ExecutionResponse;
import project.lab5.console.Console;
import project.lab5.managers.CollectionManager;

public class CountByMood extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public CountByMood(Console console, CollectionManager collectionManager) {
        super("count_by_mood mood", "Показать количество коллекций с заданным mood");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String argument) throws Ask.AskBreak {
        if (argument.isEmpty()) {
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        }else {
            var mood = Ask.askMood(console);
            return new ExecutionResponse("Количество " + mood + " в коллекции равно " + collectionManager.countByMood(mood));
        }
    }
}
