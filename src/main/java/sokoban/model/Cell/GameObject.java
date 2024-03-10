package sokoban.model.Cell;

import javafx.beans.property.ObjectProperty;

public abstract class GameObject extends Element {
    protected GameObject(ObjectProperty<CellValue> value) {
        super(value);
    }
}
