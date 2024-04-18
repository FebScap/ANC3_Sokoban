package sokoban.model;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sokoban.model.cell.GameObject;

public class Board4Design extends Board {
    private final int MAX_FILLED_CELLS;
    private static final int NB_OF_PLAYER = 1;

    private static final int MIN_OF_BOX = 0;

    private static final int MIN_OF_TARGET = 0;

    private final static StringProperty actualBoardSave = new SimpleStringProperty();

    private final BooleanBinding isFull;
    private final BooleanBinding validatePlayer;
    private final Grid4Design grid4Design;

    public Board4Design(int line, int col) {
        this.grid4Design = new Grid4Design(line, col);
        this.MAX_FILLED_CELLS = line*col/2;
        isFull = grid4Design.filledCellsCountProperty().isEqualTo(this.MAX_FILLED_CELLS);
        validatePlayer = grid4Design.filledPlayerCountProperty().isEqualTo(NB_OF_PLAYER);
    }

    public Grid4Design getGrid() {
        return this.grid4Design;
    }

    public StringProperty getActualBoardSave() {
        return actualBoardSave;
    }

    public void setActualBoardSave(String s) {
        actualBoardSave.setValue(s);
    }

    public void play(int line, int col, GameObject value) {
        if (!isFull() || !isEmpty(line, col)) {
            grid4Design.play(line, col, value);
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

    public MapProperty<Integer, GameObject> valueProperty(int line, int col) {
        return grid4Design.valueProperty(line, col);
    }

    public boolean isEmpty(int line, int col) {
        return grid4Design.isEmpty(line, col);
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

