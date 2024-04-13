package project.lab5.commands;

import project.lab5.AskManager.Ask;
import project.lab5.Collection.ExecutionResponse;
import project.lab5.console.Console;
import project.lab5.managers.CollectionManager;
import project.lab5.models.HumanBeing;

import java.util.List;

/**
 * Команда "update" - обновить значение элемента коллекции, id которого равен заданному
 */
public class Update extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Update(Console console, CollectionManager collectionManager) {
        super("update id {element}", "обновить значение элемента коллекции, id которого равен заданному");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    public ExecutionResponse apply(String arguments) throws Ask.AskBreak {
        try {
            if (arguments.isEmpty())
                return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
            console.println("Введите id объекта, в котором вы хотите поменять значение элементов");
            List<HumanBeing> collection = collectionManager.getCollection();
            Integer id = Ask.askId(console);

            console.println("* Обновление HumanBeing с id " + id + " :");

            HumanBeing a = Ask.askHumanBeing(console, id - 1);
            collection.get(id - 1);


            if (a != null) {
                collectionManager.getCollection().get(id - 1).setId(id);
                collectionManager.getCollection().get(id - 1).setName(a.getName());
                collectionManager.getCollection().get(id - 1).setCoordinates(a.getCoordinates());
                collectionManager.getCollection().get(id - 1).setRealHero(a.getRealHero());
                collectionManager.getCollection().get(id - 1).setHasToothpick(a.getHasToothpick());
                collectionManager.getCollection().get(id - 1).setImpactSpeed(a.getImpactSpeed());
                collectionManager.getCollection().get(id - 1).setSoundtrackName(a.getSoundtrackName());
                collectionManager.getCollection().get(id - 1).setWeaponType(a.getWeaponType());
                collectionManager.getCollection().get(id - 1).setMood(a.getMood());
                collectionManager.getCollection().get(id - 1).setCar(a.getCar());
                System.out.println(collectionManager);
                return new ExecutionResponse("HumanBeing успешно изменен!");
            } else return new ExecutionResponse(false, "HumanBeing не создан!");
        } catch (Ask.AskBreak e) {
            return new ExecutionResponse(false, "Отмена...");
        }
    }
}
