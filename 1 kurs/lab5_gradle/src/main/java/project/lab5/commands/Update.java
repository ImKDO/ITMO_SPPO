package project.lab5.commands;

import project.lab5.AskManager.Ask;
import project.lab5.Collection.ExecutionResponse;
import project.lab5.console.Console;
import project.lab5.managers.CollectionManager;
import project.lab5.models.HumanBeing;

/**
 * "update id" - обновить значение элемента коллекции, id которого равен заданному
 */
public class Update extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Update(Console console, CollectionManager collectionManager) {
        super("update id {element}", "Обновить значение элемента коллекции, id которого равен заданному");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    public ExecutionResponse apply(String arguments) {

        if (arguments.isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        long id = 0;
        try {
            id = Integer.parseInt(arguments);
        } catch (NumberFormatException e) {
        }

        int count = 0;

        for (HumanBeing humanBeing : collectionManager.getCollection()) {
            if (humanBeing.getId() == id) {
                HumanBeing hB = new Ask(console).askHumanBeing((int) id);
                console.println("* Обновление HumanBeing с id " + id + " :");
                collectionManager.getCollection().get(count).setId((int) id);
                collectionManager.getCollection().get(count).setName(hB.getName());
                collectionManager.getCollection().get(count).setCoordinates(hB.getCoordinates());
                collectionManager.getCollection().get(count).setRealHero(hB.getRealHero());
                collectionManager.getCollection().get(count).setHasToothpick(hB.getHasToothpick());
                collectionManager.getCollection().get(count).setImpactSpeed(hB.getImpactSpeed());
                collectionManager.getCollection().get(count).setSoundtrackName(hB.getSoundtrackName());
                collectionManager.getCollection().get(count).setWeaponType(hB.getWeaponType());
                collectionManager.getCollection().get(count).setMood(hB.getMood());
                collectionManager.getCollection().get(count).setCar(hB.getCar());
                System.out.println(collectionManager);
            }
            count++;
        }
        return new ExecutionResponse("HumanBeing с таким id не существует!");
    }
}
