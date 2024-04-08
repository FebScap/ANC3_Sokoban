package sokoban.model.Cell;

import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;

public class ElementStack {
    private final MapProperty<Integer, GameObject> elements = new SimpleMapProperty<>();
    public ElementStack() {
        elements.getValue().put(0, new Ground());
        elements.getValue().put(1, null);
        elements.getValue().put(2, null);
    }

    public void addElement(GameObject element) {
        if (element instanceof Wall || element instanceof Ground) {
            elements.getValue().put(0, element);
            elements.getValue().put(1, null);
            elements.getValue().put(2, null);
        } else if (element instanceof Player || element instanceof Box) {
            elements.getValue().put(1, element);
        } else if (element instanceof Target) {
            elements.getValue().put(2, element);
        }
    }

    public void removeElement(GameObject element) {
        //TODO
        if (element instanceof Wall) {
            elements.getValue().put(0, new Ground());
        } else if (element instanceof Player || element instanceof Box) {
            elements.getValue().put(1, null);
        } else if (element instanceof Target) {
            elements.getValue().put(2, null);

        }
    }

    public MapProperty<Integer, GameObject> getElementsProperty() {
        return elements;
    }
}
