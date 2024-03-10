package sokoban.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.LongBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import sokoban.model.Cell.*;

import java.util.Arrays;

public class Grid {
    private final int line;
    private final int col;

    private final Cell[][] matrix;

    private final LongBinding filledCellsCount;

    public Grid(int line, int col) {
        this.line = line;
        this.col = col;
        matrix = new Cell[col][line];
        for (int i = 0; i < col; ++i) {
            matrix[i] = new Cell[col];
            for (int j = 0; j < col; ++j) {
                matrix[i][j] = new Cell();
            }
        }

        filledCellsCount = Bindings.createLongBinding(() -> Arrays
                .stream(matrix)
                .flatMap(Arrays::stream)
                .filter(cell -> !cell.isEmpty())
                .count());

        System.out.println(filledCellsCount);

    }

    void play(int line, int col, CellValue value) {
        this.setCell(line, col, value);
        //matrix[line][col].setType(value);
        filledCellsCount.invalidate();
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
        matrix[line][col].setType(value);
    }

    public boolean isEmpty(int line, int col) {
        return matrix[line][col].isEmpty();
    }

    public LongBinding filledCellsCountProperty() {return filledCellsCount;}
}
