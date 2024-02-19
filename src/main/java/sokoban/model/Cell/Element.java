package sokoban.model.Cell;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;

public abstract class Element {
    private final ObjectProperty<CellValue> value;

    public Element(ObjectProperty<CellValue> value) {
        this.value = value;
    }

    public CellValue getValue() {
        return this.value.getValue();
    }

    public boolean isEmpty() {
        return this.value.get() == CellValue.GROUND;
    }

    public void setValue(CellValue value) {
        this.value.setValue(value);
    }

    public ReadOnlyObjectProperty<CellValue> valueProperty() {
        return this.value;
    }
}
