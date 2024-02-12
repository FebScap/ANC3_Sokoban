package sokoban.model.Cell.MultipleLayer;

import javafx.beans.property.ObjectProperty;
import sokoban.model.Cell.Cell;
import sokoban.model.Cell.CellValue;

public abstract class MultipleLayer extends Cell {
    protected MultipleLayer(ObjectProperty<CellValue> value) {
        super(value);
    }
}
