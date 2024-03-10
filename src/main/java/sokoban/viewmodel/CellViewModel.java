package sokoban.viewmodel;

import javafx.beans.property.ReadOnlyObjectProperty;
import sokoban.model.Board;
import sokoban.model.Cell.CellValue;

public class CellViewModel {

    private final int line, col;
    private final Board board;

    CellViewModel(int line, int col, Board board) {
        this.line = line;
        this.col = col;
        this.board = board;
    }

    public void play(CellValue value) {
        board.play(line, col, value);
    }

    public ReadOnlyObjectProperty<CellValue> valueProperty() {
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
