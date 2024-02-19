package sokoban.model;

import javafx.beans.property.ReadOnlyObjectProperty;
import sokoban.model.Cell.CellValue;

public class Board {
    private final int MAX_FILLED_CELLS;

    private final Grid grid;

    public Board() {
        this.grid = new Grid(10, 15);
        MAX_FILLED_CELLS = 75;
    }
    public Board(int line, int col) {
        this.grid = new Grid(line, col);
        MAX_FILLED_CELLS = line*col/2;
    }

    public Grid getGrid() {
        return this.grid;
    }

    public void play(int line, int col, CellValue value) {
        grid.setCell(line, col, value);
    }

    public int maxFilledCells() {
        return this.MAX_FILLED_CELLS;
    }


    public ReadOnlyObjectProperty<CellValue> valueProperty(int line, int col) {
        return grid.valueProperty(line, col);
    }

    public boolean isEmpty(int line, int col) {
        return grid.isEmpty(line, col);
    }
}
