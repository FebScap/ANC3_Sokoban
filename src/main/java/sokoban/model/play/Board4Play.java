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

    /** Move the player to the selected direction
     * <p>
     * direction 0 : up
     * direction 1 : right
     * direction 2 : down
     * direction 3 : left
     */
    public void movePlayer(int direction) {
        switch (direction) {
            case 0 :
                if (getGrid().getPosPlayerCol() != 0) {
                    getGrid().setPosPlayerCol(getGrid().getPosPlayerCol() - 1);
                }
                break;
            case 1 :
                if (getGrid().getPosPlayerLine() != getGrid().getPosPlayerLine()) {
                    getGrid().setPosPlayerLine(getGrid().getPosPlayerCol() + 1);
                }
                break;
            case 2 :
                if (getGrid().getPosPlayerCol() != getGrid().getCol()) {
                    getGrid().setPosPlayerCol(getGrid().getPosPlayerCol() + 1);
                }
                break;
            case 3 :
                if (getGrid().getPosPlayerLine() != 0) {
                    getGrid().setPosPlayerLine(getGrid().getPosPlayerCol() - 1);
                }
                break;
        }
    }

    public MapProperty<Integer, GameObject> valueProperty(int line, int col) {
        return grid4Play.valueProperty(line, col);
    }

    public boolean isEmpty(int line, int col) {
        return grid4Play.isEmpty(line, col);
    }
}
