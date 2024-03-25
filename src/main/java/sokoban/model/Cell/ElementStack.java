package sokoban.model.Cell;

import javafx.beans.property.ObjectProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ElementStack {
    private final HashMap <Integer, GameObject> elements = new HashMap<>();
    public ElementStack() {
        elements.put(0, new Ground());
        elements.put(1, null);
        elements.put(2, null);
    }

    public void addElement(GameObject element) {
        if (element instanceof Wall) {
            elements.clear();
            elements.put(0, element);
        } else if (element instanceof Player) {
            elements.put(1, element);
        } else if (element instanceof Target) {
            elements.put(2, element);
        } else if (element instanceof Box) {
            elements.put(1, element);
        } else if (element instanceof Ground) {
            elements.clear();
            elements.put(0, element);
        }
    }

    public void removeElement(GameObject element) {
        //TODO 
    }

    public CellValue getValue() {
        return value;
    }
    public List<GameObject> getElements() {
        return elements;
    }
}
