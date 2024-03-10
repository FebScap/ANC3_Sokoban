package sokoban.model;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import sokoban.model.Cell.CellValue;

public class Board {
    private final int MAX_FILLED_CELLS;
    private static final int NB_OF_PLAYER = 1;

    private static final int MIN_OF_BOX = 1;

    private static final int MIN_OF_TARGET = 1;

    private final BooleanBinding isFull;
    private final BooleanBinding validatePlayer;


    private final Grid grid;

    public Board(int line, int col) {
        this.grid = new Grid(line, col);
        this.MAX_FILLED_CELLS = line*col/2;
        isFull = grid.filledCellsCountProperty().isEqualTo(this.MAX_FILLED_CELLS);
        validatePlayer = grid.filledPlayerCountProperty().isEqualTo(this.NB_OF_PLAYER);
    }

    public Grid getGrid() {
        return this.grid;
    }

    public void play(int line, int col, CellValue value) {
        if (!isFull() || grid.getValue(line, col) != CellValue.GROUND) {
            grid.play(line, col, value);
        }
    }

    public int maxFilledCells() {
        return this.MAX_FILLED_CELLS;
    }

    public Boolean isFull() {
        return isFull.get();
    }

    public Boolean validatePlayer() {
        return validatePlayer.get();
    }

    public ReadOnlyObjectProperty<CellValue> valueProperty(int line, int col) {
        return grid.valueProperty(line, col);
    }

    public boolean isEmpty(int line, int col) {
        return grid.isEmpty(line, col);
    }

    public static int getNB_OF_PLAYER() {
        return NB_OF_PLAYER;
    }

    public static int getMIN_OF_BOX() {
        return MIN_OF_BOX;
    }

    public static int getMIN_OF_TARGET() {
        return MIN_OF_TARGET;
    }
}

