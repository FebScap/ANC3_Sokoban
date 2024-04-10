package sokoban.model.design;

import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import sokoban.model.api.cell.*;

public class ElementStack4Design extends ElementStack {
    private final MapProperty<Integer, GameObject> elements = new SimpleMapProperty<>(FXCollections.observableHashMap());
    public ElementStack4Design() {
        elements.put(0, new Ground());
        elements.put(1, null);
        elements.put(2, null);
    }

    public void addElement(GameObject element) {
        if (element instanceof Wall || element instanceof Ground) {
            elements.put(0, element);
            elements.put(1, null);
            elements.put(2, null);
        } else if (element instanceof Player || element instanceof Box) {
            if (elements.get(0) instanceof Wall) {
                elements.put(0, new Ground());
            }
            elements.put(1, element);
        } else if (element instanceof Target) {
            if (elements.get(0) instanceof Wall) {
                elements.put(0, new Ground());
            }
            elements.put(2, element);
        }
    }

    public void removeElement(GameObject element) {
        //TODO
        if (element instanceof Wall) {
            elements.getValue().put(0, new Ground());
        } else if (element instanceof Player || element instanceof Box) {
            elements.put(1, null);
        } else if (element instanceof Target) {
            elements.put(2, null);

        }
    }

    public MapProperty<Integer, GameObject> getElementsProperty() {
        return elements;
    }
}
