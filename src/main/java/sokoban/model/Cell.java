package sokoban.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

abstract class Cell {
    private final ObjectProperty<Object> value = new SimpleObjectProperty<>("EMPTY");

    public Cell() {

    }

    Object getValue() {
        return value.getValue();
    }

    void setValue(Object value) {
        if (value instanceof MultipleLayer || value instanceof Wall)
            this.value.setValue(value);
        else
            throw new RuntimeException("Object must be instance of MultipleLayer or Wall");

    }

    boolean isEmpty() {
        return value.get() == "EMPTY";
    }

    ReadOnlyObjectProperty<Object> valueProperty() {
        return value;
    }

}
