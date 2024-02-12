package sokoban.model.Cell.MultipleLayer;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import sokoban.model.Cell.CellValue;

public class Target extends GameObject {
    public Target() {
        super(new SimpleObjectProperty<>(CellValue.TARGET));
    }
}
