package sokoban.model.Cell;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Wall extends Element {
    public Wall() {
        super(new SimpleObjectProperty<>(CellValue.WALL));
    }
}
