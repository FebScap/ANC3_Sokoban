package sokoban.model.Cell.MultipleLayer;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import sokoban.model.Cell.CellValue;

public class Box extends GameObject {

    public Box() {
        super(new SimpleObjectProperty<>(CellValue.BOX));
    }
}
