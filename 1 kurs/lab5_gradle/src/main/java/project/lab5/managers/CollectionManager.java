package project.lab5.managers;

import project.lab5.models.*;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Класс для управления с коллекциями
 */
public class CollectionManager {
    static Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    String name; //Поле не может быть null, Строка не может быть пустой
    Coordinates coordinates; //Поле не может быть null
    java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    Boolean realHero; //Поле не может быть null
    Boolean hasToothpick; //Поле может быть null
    float impactSpeed; //Максимальное значение поля: 118
    String soundtrackName; //Поле не может быть null
    WeaponType weaponType; //Поле не может быть null
    Mood mood; //Поле не может быть null
    Car car; //Поле не может быть null

    private ArrayList<HumanBeing> collection = new ArrayList<HumanBeing>();
    private Map<Integer, HumanBeing> humanBeingMap = new HashMap<>();
    private LocalDateTime lastSaveTime;
    private LocalDateTime lastInitTime;
    private final DumpManager dumpManager;
    private int currentId = 1;

    public CollectionManager(DumpManager dumpManager) {
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.dumpManager = dumpManager;
    }

    /**
     * Последнее время инициализации
     * @return
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    /**
     * Последнее время сохранения
     * @return
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    public void setLastSaveTime(LocalDateTime lastSaveTime) {
        this.lastSaveTime = lastSaveTime;
    }

    /**
     * Получить коллекцию
     * @return
     */
    public ArrayList<HumanBeing> getCollection() {
        return collection;
    }

    /**
     * Получить объект HumanBeing по id
     * @return
     */
    public HumanBeing byId(Integer id) {
        return humanBeingMap.get(id);
    }

    /**
     * Содержет ли колекции HumanBeing
     * @return
     */
    public boolean isContain(HumanBeing element) {
        return element == null | byId(element.getId()) != null;
    }
    /**
     * Создание нового ID
     * @return
     */
    public int nextId() {
        while (byId(++currentId) != null);
        return currentId;
    }

    /**
     * Добавляет элемент в коллекцию
     * @return
     */
    public boolean add(HumanBeing humanBeing){
        if(isContain(humanBeing)) {return false;}
        humanBeingMap.put(humanBeing.getId(),humanBeing);
        collection.add(humanBeing);
        return true;
    }

    /**
     * loadCollection - загрузить коллекцию из файла в программу
     * @return
     * @throws Exception
     */
    public boolean loadCollection() throws Exception {
        humanBeingMap.clear();
        dumpManager.readColletion(collection);
        lastInitTime = LocalDateTime.now();
        for (var e : collection)
            if (e.getId() == null) {
                collection.clear();
                return false;
            } else {
                if (e.getId()>currentId) currentId = e.getId();
                humanBeingMap.put(e.getId(), e);
            }

        return true;
    }

    /**
     * Очистить коллекцию
     */
    public void clearCollection(){
        collection.clear();
    }
    public boolean remove(Integer id){
        var a = byId(id);
        if (a == null) return false;
        humanBeingMap.remove(a.getId());
        collection.remove(a);
        return true;
    }

//    /**
//     * Инициализировать коллекцию
//     * @return
//     * @throws Exception
//     */
//    public boolean init() throws Exception {
//        collection.clear();
//        humanBeingMap.clear();
//        dumpManager.readColletion(collection);
//        lastInitTime = LocalDateTime.now();
//        for (var e : collection)
//            if (byId(e.getId()) != null) {
//                collection.clear();
//                humanBeingMap.clear();
//                return false;
//            } else {
//                if (e.getId()>currentId) currentId = e.getId();
//                humanBeingMap.put(e.getId(), e);
//            }
//        return true;
//    }

    /**
     * Счетчик объектов равных заданному mood
     * @param mood
     * @return
     */
    public int countByMood(Mood mood){
        int count = 0;
        for (HumanBeing element: collection){
            if (element.getMood().equals(mood)){
                count += 1;
            }
        }
        return count;
    }

    /**
     * Коллекция успешно записана
     * @throws IOException
     */
    public void saveCollection() throws IOException, JAXBException {
        DumpManager.writeCollection(collection);
        lastSaveTime = LocalDateTime.now();

    }
    @Override
    public String toString(){
        if (collection.isEmpty()) return "Коллекция пуста";
        StringBuilder info = new StringBuilder();
        for (var objHumanBeing: collection){
            info.append(objHumanBeing + "\n\n");
        }
        return info.toString().trim();

    }
}
