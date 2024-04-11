package sokoban.viewmodel.play;

import javafx.beans.property.MapProperty;
import sokoban.model.api.cell.GameObject;
import sokoban.model.play.Board4Play;
import sokoban.viewmodel.api.CellViewModel;

public class CellViewModel4Play extends CellViewModel {
    private final int line, col;
    private final Board4Play board4Play;

    CellViewModel4Play(int line, int col, Board4Play board4Play) {
        this.line = line;
        this.col = col;
        this.board4Play = board4Play;
    }

    public void play(GameObject value) {
        board4Play.play(line, col, value);
    }

    public MapProperty<Integer, GameObject> valueProperty() {
        return board4Play.valueProperty(line, col);
    }

    public boolean isEmpty() {
        return board4Play.isEmpty(line, col);
    }

    public int getLine() {
        return line;
    }

    public int getCol() {
        return col;
    }
}
