package sokoban.model;

import javafx.beans.property.ReadOnlyObjectProperty;
import sokoban.model.Cell.Cell;
import sokoban.model.Cell.CellValue;
import sokoban.model.Cell.Ground;
import sokoban.model.Cell.Wall;

import java.util.Arrays;

public class Grid {
    private final int line;
    private final int col;

    private final Cell[][] matrix;

    public Grid(int line, int col) {
        this.line = line;
        this.col = col;
        matrix = new Cell[col][];
        for (int i = 0; i < col; ++i) {
            matrix[i] = new Cell[col];
            for (int j = 0; j < col; ++j) {
                matrix[i][j] = new Cell();
            }
        }
    }

    public int getLine() {
        return this.line;
    }
    public int getCol() {
        return this.col;
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
