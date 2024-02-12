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
    public Board(int width, int height) {
        this.grid = new Grid(width, height);
        MAX_FILLED_CELLS = width*height/2;
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
