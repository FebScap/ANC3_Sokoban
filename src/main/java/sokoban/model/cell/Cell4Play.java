package sokoban.model.cell;

import javafx.beans.property.MapProperty;
import sokoban.model.cell.Cell;
import sokoban.model.cell.ElementStack4Play;
import sokoban.model.cell.GameObject;
import sokoban.model.cell.Ground;

public class Cell4Play extends Cell {
    private final ElementStack4Play stack4Play;
    public Cell4Play() {
        this.stack4Play = new ElementStack4Play();
    }
    public MapProperty<Integer, GameObject> getElementsProperty() {
        return this.stack4Play.getElementsProperty();
    }
    public void addElement(GameObject value) {
        this.stack4Play.addElement(value);
    }

    public void removeElement(GameObject value) {
        this.stack4Play.removeElement(value);
    }

    public boolean isEmpty() {
        return this.stack4Play.getElementsProperty().getValue().get(0) instanceof Ground
                && this.stack4Play.getElementsProperty().getValue().get(1) == null
                && this.stack4Play.getElementsProperty().getValue().get(2) == null;
    }
}
