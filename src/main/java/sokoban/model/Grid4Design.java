package sokoban.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.LongBinding;
import javafx.beans.property.MapProperty;
import sokoban.model.cell.*;

import java.util.Arrays;

public class Grid4Design extends Grid {
    private final int line;
    private final int col;

    private final Cell4Design[][] matrix;
    private final LongBinding filledCellsCount;
    private final LongBinding filledPlayerCount;
    private final LongBinding filledBoxsCount;
    private final LongBinding filledTargetsCount;

    public Grid4Design(int line, int col) {
        this.line = line;
        this.col = col;
        matrix = new Cell4Design[col][line];
        for (int i = 0; i < col; ++i) {
            matrix[i] = new Cell4Design[col];
            for (int j = 0; j < col; ++j) {
                matrix[i][j] = new Cell4Design();
            }
        }

        //Compte le nombre de cases remplies et pas le nombre d'éléments

        filledCellsCount = Bindings.createLongBinding(() -> Arrays
                .stream(matrix)
                .flatMap(Arrays::stream)
                .filter(cell4Design -> !cell4Design.isEmpty())
                .count());

        filledPlayerCount = Bindings.createLongBinding(() -> Arrays
                .stream(matrix)
                .flatMap(Arrays::stream)
                .filter(cell4Design -> cell4Design.getElementsProperty().getValue().get(1) instanceof Player)
                .count());

        filledBoxsCount = Bindings.createLongBinding(() -> Arrays
                .stream(matrix)
                .flatMap(Arrays::stream)
                .filter(cell4Design -> cell4Design.getElementsProperty().getValue().get(1) instanceof Box)
                .count());

        filledTargetsCount = Bindings.createLongBinding(() -> Arrays
                .stream(matrix)
                .flatMap(Arrays::stream)
                .filter(cell4Design -> cell4Design.getElementsProperty().getValue().get(2) instanceof Target)
                .count());

    }

    protected void setCell(int line, int col, GameObject value) {
        if (value instanceof Player && filledPlayerCount.get() == 1) {
            matrix[getPosPlayerLine()][getPosPlayerCol()].removeElement(value);
            setPosPlayerLine(line);
            setPosPlayerCol(col);
        }
        if (value instanceof Player) {
            setPosPlayerLine(line);
            setPosPlayerCol(col);
        }
        matrix[line][col].addElement(value);
    }

    void play(int line, int col, GameObject value) {
        this.setCell(line, col, value);
        filledCellsCount.invalidate();
        filledPlayerCount.invalidate();
        filledBoxsCount.invalidate();
        filledTargetsCount.invalidate();
    }

    public int getLine() {
        return this.line;
    }
    public int getCol() {
        return this.col;
    }

    MapProperty<Integer, GameObject> valueProperty(int line, int col) {
        return matrix[line][col].getElementsProperty();
    }

    public boolean isEmpty(int line, int col) {
        return matrix[line][col].isEmpty();
    }

    public LongBinding filledCellsCountProperty() {return filledCellsCount;}
    public LongBinding filledPlayerCountProperty() {return filledPlayerCount;}
    public LongBinding filledTargetsCountProperty() {return filledTargetsCount;}
    public LongBinding filledBoxsCountProperty() {return filledBoxsCount;}
}
