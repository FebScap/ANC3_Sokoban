package sokoban.model.Cell.MultipleLayer;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import sokoban.model.Cell.CellValue;

public class Player extends GameObject {
    public Player() {
        super(new SimpleObjectProperty<>(CellValue.PLAYER));
    }
}
