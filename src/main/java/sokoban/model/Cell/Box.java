package sokoban.model.Cell;

import javafx.beans.property.SimpleObjectProperty;

public class Box extends GameObject {

    public Box() {
        super(new SimpleObjectProperty<>(CellValue.BOX));
    }
}
