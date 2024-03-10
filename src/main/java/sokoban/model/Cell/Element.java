package sokoban.model.Cell;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;

public abstract class Element {
    private final ObjectProperty<CellValue> value;

    public Element(ObjectProperty<CellValue> value) {
        this.value = value;
    }
}
