package sokoban.model.Cell;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import sokoban.model.Cell.MultipleLayer.MultipleLayer;

public abstract class Cell {
    private final ObjectProperty<CellValue> value;

    protected Cell(ObjectProperty<CellValue> value) {
        this.value = value;
    }

    public CellValue getValue() {
        return this.value.getValue();
    }

    public void setValue(CellValue value) {
        this.value.setValue(value);
    }

    public boolean isEmpty() {
        return this.value.get() == CellValue.GROUND;
    }

    public ReadOnlyObjectProperty<CellValue> valueProperty() {
        return this.value;
    }

}
