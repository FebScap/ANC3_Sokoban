package sokoban.model.Cell;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Wall extends Cell {
    public Wall() {
        super(new SimpleObjectProperty<>(CellValue.WALL));
    }
}
