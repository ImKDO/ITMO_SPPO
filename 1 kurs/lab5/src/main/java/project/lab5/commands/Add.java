package project.lab5.commands;

import project.lab5.AskManager.Ask;
import project.lab5.managers.CollectionManager;
import project.lab5.Collection.ExecutionResponse;
import project.lab5.console.Console;
import project.lab5.models.HumanBeing;

/**
 * Команда 'add'. Добавляет новый элемент в коллекцию.
 */
public class Add extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Add(Console console, CollectionManager collectionManager) {
        super("add {element}", "добавить новый элемент в коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды и сообщение об успешности.
     */
    @Override
    public ExecutionResponse apply(String arguments) {
        try {
            if (arguments.isEmpty()) return new ExecutionResponse(false,
                    "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
            console.println("* Создание нового HumanBeing:");
            HumanBeing a = Ask.askHumanBeing(console, collectionManager.nextId());
//&& !a.validate()
            if (a != null) {
                collectionManager.add(a);
                return new ExecutionResponse("HumanBeing успешно добавлен!");
            } else
                return new ExecutionResponse(false, "false HumanBeing не создан!");
        } catch (Ask.AskBreak e) {
            return new ExecutionResponse(false, "Отмена...");
        }
    }
}
