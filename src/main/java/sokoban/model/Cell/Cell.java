package sokoban.model.Cell;

import javafx.beans.property.MapProperty;
import javafx.beans.property.ObjectProperty;
import javafx.util.Pair;

public class Cell {
    private final ElementStack stack;
    private static final Player player = new Player();
    private static ObjectProperty<Pair<Integer, Integer>> playerPos;
    public Cell() {
        this.stack = new ElementStack();
    }

    public MapProperty<Integer, GameObject> getElementsProperty() {
        return this.stack.getElementsProperty();
    }
    public void addElement(GameObject value) {
        this.stack.addElement(value);
    }

    public void removeElement(GameObject value) {
        this.stack.removeElement(value);
    }

    public boolean isEmpty() {
        return this.stack.getElementsProperty().getValue().get(0) instanceof Ground
                && this.stack.getElementsProperty().getValue().get(1) == null
                && this.stack.getElementsProperty().getValue().get(2) == null;
    }

    public void setPlayerPos (int line, int col) {
        playerPos.setValue(new Pair<>(line,col));
    }
}
