package sokoban.model.design;

import javafx.beans.property.MapProperty;
import sokoban.model.api.cell.Cell;
import sokoban.model.api.cell.GameObject;
import sokoban.model.api.cell.Ground;

public class Cell4Design extends Cell {
    private final ElementStack4Design stack4Design;
    public Cell4Design() {
        this.stack4Design = new ElementStack4Design();
    }

    public MapProperty<Integer, GameObject> getElementsProperty() {
        return this.stack4Design.getElementsProperty();
    }
    public void addElement(GameObject value) {
        this.stack4Design.addElement(value);
    }

    public void removeElement(GameObject value) {
        this.stack4Design.removeElement(value);
    }

    public boolean isEmpty() {
        return this.stack4Design.getElementsProperty().getValue().get(0) instanceof Ground
                && this.stack4Design.getElementsProperty().getValue().get(1) == null
                && this.stack4Design.getElementsProperty().getValue().get(2) == null;
    }
}
