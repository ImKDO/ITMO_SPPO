package org.example.commands;


import org.example.Collection.ExecutionResponse;
import org.example.console.Console;
import org.example.managers.CollectionManager;
import org.example.models.HumanBeing;

import java.util.List;

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

    public ExecutionResponse apply(List<Object> list) {

        if (String.valueOf(list.get(1)).isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        long id = 0;
        try {
            id = Integer.parseInt(list.get(1).toString());
        } catch (NumberFormatException ignored) {
        }

        int count = 0;

        for (HumanBeing humanBeing : collectionManager.getCollection()) {
            if (humanBeing.getId() == id) {
                HumanBeing hB = (HumanBeing) list.get(2);
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
                console.println(collectionManager);
            }
            count++;
        }
        return new ExecutionResponse("HumanBeing с таким id не существует!");
    }
}
