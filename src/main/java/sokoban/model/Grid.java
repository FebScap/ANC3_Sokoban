package sokoban.model;

import javafx.beans.property.ReadOnlyObjectProperty;
import sokoban.model.Cell.Cell;
import sokoban.model.Cell.CellValue;
import sokoban.model.Cell.Wall;

import java.util.Arrays;

public class Grid {
    private final int width;
    private final int height;

    private final Cell[][] matrix;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        matrix = new Cell[width][];
        for (int i = 0; i < width; ++i) {
            matrix[i] = new Cell[height];
            for (int j = 0; j < height; ++j) {
                matrix[i][j] = new Wall();
            }
        }
    }

    public int getWidth() {
        return this.width;
    }
    public int getHeight() {
        return this.height;
    }

    ReadOnlyObjectProperty<CellValue> valueProperty(int line, int col) {
        return matrix[line][col].valueProperty();
    }

    CellValue getValue(int line, int col) {
        return matrix[line][col].getValue();
    }

    void setCell(int line, int col, CellValue value) {
        matrix[line][col].setValue(value);
    }

    public boolean isEmpty(int line, int col) {
        return matrix[line][col].isEmpty();
    }
}
