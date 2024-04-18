package sokoban.viewmodel;

import javafx.beans.property.MapProperty;
import sokoban.model.Board4Design;
import sokoban.model.cell.GameObject;

public class CellViewModel4Design extends CellViewModel {

    private final int line, col;
    private final Board4Design board4Design;

    CellViewModel4Design(int line, int col, Board4Design board4Design) {
        this.line = line;
        this.col = col;
        this.board4Design = board4Design;
    }

    public void play(GameObject value) {
        board4Design.play(line, col, value);
    }

    public MapProperty<Integer, GameObject> valueProperty() {
        return board4Design.valueProperty(line, col);
    }

    public boolean isEmpty() {
        return board4Design.isEmpty(line, col);
    }

    public int getLine() {
        return line;
    }

    public int getCol() {
        return col;
    }
}
