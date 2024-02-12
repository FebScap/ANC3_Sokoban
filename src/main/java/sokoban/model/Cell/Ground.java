package sokoban.model.Cell;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Ground extends Cell {
    public Ground() {
        super(new SimpleObjectProperty<>(CellValue.GROUND));
    }
}
