package sokoban.viewmodel;

import javafx.beans.property.MapProperty;
import sokoban.model.Board;
import sokoban.model.Cell.CellValue;
import sokoban.model.Cell.GameObject;

public class CellViewModel {

    private final int line, col;
    private final Board board;

    CellViewModel(int line, int col, Board board) {
        this.line = line;
        this.col = col;
        this.board = board;
    }

    public void play(GameObject value) {
        board.play(line, col, value);
    }

    public MapProperty<Integer, GameObject> valueProperty() {
        return board.valueProperty(line, col);
    }

    public boolean isEmpty() {
        return board.isEmpty(line, col);
    }

    public int getLine() {
        return line;
    }

    public int getCol() {
        return col;
    }
}
