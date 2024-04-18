package sokoban.model;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.MapProperty;
import sokoban.model.cell.Box;
import sokoban.model.cell.GameObject;
import sokoban.model.cell.Player;
import sokoban.model.cell.Wall;

public class Board4Play extends Board {
    private final Grid4Play grid4Play;
    private final BooleanBinding victory;


    public Board4Play(int line, int col) {
        this.grid4Play = new Grid4Play(line, col);
        this.victory = grid4Play.goalsReachedCountProperty().isEqualTo(grid4Play.filledBoxsCountProperty());
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
        int playerCol = getGrid().getPosPlayerCol();
        int playerLine = getGrid().getPosPlayerLine();
        switch (direction) {
            case 0 :
                if (playerLine > 0 && playerCanMove(playerLine - 1, playerCol)
                        && tryMoveBox(playerLine - 1, playerCol, 0)) {
                    getGrid().setCell(playerLine - 1, playerCol, new Player());
                }
                break;
            case 1 :
                if (playerCol < getGrid().getCol() - 1 && playerCanMove(playerLine, playerCol + 1)
                        && tryMoveBox(playerLine, playerCol + 1, 1)) {
                    getGrid().setCell(playerLine, playerCol + 1, new Player());
                }
                break;
            case 2 :
                if (playerLine < getGrid().getLine() - 1 && playerCanMove(playerLine + 1, playerCol)
                        && tryMoveBox(playerLine + 1, playerCol, 2)) {
                    getGrid().setCell(playerLine + 1, playerCol, new Player());
                }
                break;
            case 3 :
                if (playerCol > 0 && playerCanMove(playerLine, playerCol - 1)
                        && tryMoveBox(playerLine, playerCol - 1, 3)) {
                    getGrid().setCell(playerLine, playerCol - 1, new Player());
                }
                break;
        }
    }

    public Boolean playerCanMove(int line, int col) {
        return !(getGrid().valueProperty(line, col).get(0) instanceof Wall || getVictory());
    }

    public Boolean boxCanMove(int line, int col) {
        return !(getGrid().valueProperty(line, col).get(0) instanceof Wall
                    || getGrid().valueProperty(line, col).get(1) instanceof Box);
    }

    public boolean tryMoveBox(int line, int col, int direction) {
        Cell4Play cellToGo = grid4Play.getCell(line, col);
        if (cellToGo.getElementsProperty().get(1) instanceof Box) {
            switch (direction) {
                case 0:
                    if (line-1 >= 0 && boxCanMove(line - 1, col)) {
                        getGrid().setCell(line - 1, col, new Box());
                        return true;
                    } else {
                        return false;
                    }
                case 1:
                    if (col+1 < getGrid().getCol() && boxCanMove(line, col + 1)) {
                        getGrid().setCell(line, col + 1, new Box());
                        return true;
                    } else {
                        return false;
                    }
                case 2:
                    if (line+1 < getGrid().getLine() && boxCanMove(line + 1, col)) {
                        getGrid().setCell(line + 1 , col, new Box());
                        return true;
                    } else {
                        return false;
                    }
                case 3:
                    if (col-1 >= 0 && boxCanMove(line, col - 1)) {
                        getGrid().setCell(line, col - 1, new Box());
                        return true;
                    } else {
                        return false;
                    }
            }
        }
        return true;
    }

    public MapProperty<Integer, GameObject> valueProperty(int line, int col) {
        return grid4Play.valueProperty(line, col);
    }

    public boolean isEmpty(int line, int col) {
        return grid4Play.isEmpty(line, col);
    }

    public Boolean getVictory() {
        return victory.get();
    }

    public BooleanBinding victoryProperty() {
        return victory;
    }
}
