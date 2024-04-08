package sokoban.model.Cell;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class Cell {
    private final ElementStack stack;
    private static Player player = new Player();
    private static ObjectProperty<Pair<Integer, Integer>> playerPos;
    public Cell() {
        this.stack = new ElementStack();
    }

    public HashMap<Integer, ObjectProperty<GameObject>> getElements() {
        return this.stack.getElements()getElements();
    }
    public void addElement(GameObject value) {
        this.stack.addElement(value);
    }

    public boolean isEmpty() {
        return this.stack.getElements().get(0).getValue() instanceof Ground
                && this.stack.getElements().get(1).getValue() == null
                && this.stack.getElements().get(2).getValue() == null;
    }

    public void setPlayerPos (int line, int col) {
        playerPos.setValue(new Pair<>(line,col));
    }
}
