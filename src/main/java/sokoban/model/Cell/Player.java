package sokoban.model.Cell;

import javafx.beans.property.SimpleObjectProperty;
import sokoban.model.Cell.CellValue;
import sokoban.model.Cell.GameObject;

public class Player extends GameObject {
    public Player() {
        super(new SimpleObjectProperty<>(CellValue.PLAYER));
    }
}
