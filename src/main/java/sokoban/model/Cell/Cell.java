package sokoban.model.Cell;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Cell {
    private final ObjectProperty<CellValue> value = new SimpleObjectProperty<>(CellValue.GROUND);
    private final ElementStack stack;
    private final Wall wall = new Wall();
    private boolean isWall;
    public Cell() {
        this.isWall = false;
        this.stack = new ElementStack(CellValue.GROUND);
    }

    public CellValue getValue() {
        return this.value.getValue();
    }
    private void setValue(CellValue value) {
        this.value.setValue(value);
    }
    public ReadOnlyObjectProperty<CellValue> valueProperty() {
        return this.value;
    }

    public void setType(CellValue value){
        if (value.equals(CellValue.WALL)) {
            this.isWall = true;
            setValue(value);
        } else {
            stack.setElement(value);
            setValue(value);
        }
    }

    public Object getElement() {
        if (isWall) {
            return wall;
        } else {
            return stack;
        }
    }
    public boolean isEmpty() {
        return this.value.get() == CellValue.GROUND;
    }
}
