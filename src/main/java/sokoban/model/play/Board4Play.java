package sokoban.model.play;

import javafx.beans.property.MapProperty;
import sokoban.model.api.Board;
import sokoban.model.api.cell.GameObject;

public class Board4Play extends Board {
    private final Grid4Play grid4Play;

    public Board4Play(int line, int col) {
        this.grid4Play = new Grid4Play(line, col);
    }

    public Grid4Play getGrid() {
        return this.grid4Play;
    }

    public void play(int line, int col, GameObject value) {
        grid4Play.play(line, col, value);
    }

    public MapProperty<Integer, GameObject> valueProperty(int line, int col) {
        return grid4Play.valueProperty(line, col);
    }

    public boolean isEmpty(int line, int col) {
        return grid4Play.isEmpty(line, col);
    }
}
