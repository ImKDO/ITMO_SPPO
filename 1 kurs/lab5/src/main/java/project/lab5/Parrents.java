package project.lab5;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "parrents")
public class Parrents {
    private List<Child> childCollection;
    public List<Child> getChildCollection() {
        return childCollection;
    }
    @XmlElement(name = "child")
    public void setChildCollection(List<Child> childCollection) {
        this.childCollection = childCollection;
    }
}
