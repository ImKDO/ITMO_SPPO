package project.lab5.models;

import java.util.List;

/**
 * Класс оболочка для коллекции, состоящей из объектов HumanBeing
 */
public class CollectionWrapper {

    private List<HumanBeing> collectionHumanBeing;

    public CollectionWrapper(List<HumanBeing> collectionHumanBeing) {
        this.collectionHumanBeing = collectionHumanBeing;
    }

    public List<HumanBeing> getCollectionHumanBeing() {
        return collectionHumanBeing;
    }

    public void setCollectionHumanBeing(List<HumanBeing> collectionHumanBeing) {
        this.collectionHumanBeing = collectionHumanBeing;
    }
}
