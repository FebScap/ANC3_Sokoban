package sokoban.model.Cell.MultipleLayer;

import javafx.beans.property.ObjectProperty;
import sokoban.model.Cell.CellValue;

public abstract class GameObject extends MultipleLayer {
    protected GameObject(ObjectProperty<CellValue> value) {
        super(value);
    }
}
