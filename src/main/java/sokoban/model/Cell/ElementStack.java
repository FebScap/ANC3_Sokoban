package sokoban.model.Cell;

import javafx.beans.property.ObjectProperty;

import java.util.ArrayList;
import java.util.List;

public class ElementStack {
    private CellValue value;
    private final List<GameObject> elements = new ArrayList<GameObject>();
    public ElementStack(CellValue value) {
        this.value = value;
    }

    public void setElement(CellValue value) {
        if (!value.equals(CellValue.WALL)) {
            this.value = value;
            this.elements.clear();
            this.elements.add(new Ground());
            switch (value) {
                case PLAYER -> this.elements.add(new Player());
                case TARGET -> this.elements.add(new Target());
                case BOX -> this.elements.add(new Box());
                case PLAYER_TARGET -> {
                    this.elements.add(new Player());
                    this.elements.add(new Target());
                }
                case BOX_TARGET -> {
                    this.elements.add(new Box());
                    this.elements.add(new Target());
                }
            }
        } else {
            throw new RuntimeException("ElementStack value can't be type of " + value.toString());
        }
    }

    public CellValue getValue() {
        return value;
    }
    public List<GameObject> getElements() {
        return elements;
    }
}
