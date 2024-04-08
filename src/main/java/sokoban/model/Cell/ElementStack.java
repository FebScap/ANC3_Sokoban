package sokoban.model.Cell;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.HashMap;

public class ElementStack {
    private final HashMap <Integer, ObjectProperty<GameObject>> elements = new HashMap<>();
    public ElementStack() {
        elements.put(0, new SimpleObjectProperty<>(new Ground()));
        elements.put(1, new SimpleObjectProperty<>());
        elements.put(2, new SimpleObjectProperty<>());
    }

    public void addElement(GameObject element) {
        if (element instanceof Wall || element instanceof Ground) {
            elements.get(0).setValue(element);
            elements.get(1).setValue(null);
            elements.get(2).setValue(null);
        } else if (element instanceof Player || element instanceof Box) {
            elements.get(1).setValue(element);
        } else if (element instanceof Target) {
            elements.get(2).setValue(element);
        }
    }

    public void removeElement(GameObject element) {
        //TODO
        if (element instanceof Wall) {
            elements.get(0).setValue(new Ground());
        } else if (element instanceof Player || element instanceof Box) {
            elements.get(1).setValue(null);
        } else if (element instanceof Target) {
            elements.get(2).setValue(null);
        }
    }

    public HashMap<Integer, ObjectProperty<GameObject>> getElements() {
        return elements;
    }
}
