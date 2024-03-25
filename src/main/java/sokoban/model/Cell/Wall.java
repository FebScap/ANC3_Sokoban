package sokoban.model.Cell;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Wall extends GameObject {
    public Wall() {
        super(new SimpleObjectProperty<>(CellValue.WALL));
    }
}
