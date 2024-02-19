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
            elements.clear();
            elements.add(new Ground());
            switch (value) {
                case PLAYER -> elements.add(new Player());
                case TARGET -> elements.add(new Target());
                case BOX -> elements.add(new Box());
                case PLAYER_TARGET -> {
                    elements.add(new Player());
                    elements.add(new Target());
                }
                case BOX_TARGET -> {
                    elements.add(new Box());
                    elements.add(new Target());
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
