package project.lab5.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "collection")
public class CollectionHumanBeing {
    private List<HumanBeing> collectionHumanBeing;

    public List<HumanBeing> getCollectionHumanBeing() {
        return collectionHumanBeing;
    }

    @XmlElement(name = "HumanBeing")
    public void setCollectionHumanBeing(List<HumanBeing> collectionHumanBeing) {this.collectionHumanBeing = collectionHumanBeing;}
}
