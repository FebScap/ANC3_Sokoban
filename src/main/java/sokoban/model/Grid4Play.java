package sokoban.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.LongBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleIntegerProperty;
import sokoban.model.cell.*;

import java.util.Arrays;

public class Grid4Play extends Grid {
    private final Cell4Play[][] matrix;
    private final IntegerProperty moveCount = new SimpleIntegerProperty(-1); //Bizarre
    private final IntegerBinding filledBoxsCount;
    private final IntegerBinding goalsReachedCount;

    public Grid4Play(int line, int col) {
        this.line = line;
        this.col = col;
        matrix = new Cell4Play[col][line];
        for (int i = 0; i < col; ++i) {
            matrix[i] = new Cell4Play[col];
            for (int j = 0; j < col; ++j) {
                matrix[i][j] = new Cell4Play();
            }
        }

        filledPlayerCount = Bindings.createLongBinding(() -> Arrays
                .stream(matrix)
                .flatMap(Arrays::stream)
                .filter(cell4Design -> cell4Design.getElementsProperty().getValue().get(1) instanceof Player)
                .count());

        filledBoxsCount = Bindings.createIntegerBinding(() -> Math.toIntExact(Arrays
                .stream(matrix)
                .flatMap(Arrays::stream)
                .filter(cell4Play -> cell4Play.getElementsProperty().getValue().get(1) instanceof Box)
                .count()));

        goalsReachedCount = Bindings.createIntegerBinding(() -> Math.toIntExact(Arrays
                .stream(matrix)
                .flatMap(Arrays::stream)
                .filter(cell4Play -> cell4Play.getElementsProperty().getValue().get(1) instanceof Box && cell4Play.getElementsProperty().getValue().get(2) instanceof Target)
                .count()));
    }

    protected void setCell(int line, int col, GameObject value) {
        if (value instanceof Player && filledPlayerCount.get() == 1) {
            matrix[getPosPlayerLine()][getPosPlayerCol()].removeElement(value);
            setPosPlayerLine(line);
            setPosPlayerCol(col);
        }
        if (value instanceof Player) {
            //moveCount.set(moveCount.get() + 1);

            setPosPlayerLine(line);
            setPosPlayerCol(col);
            moveCount.set(moveCount.get() + 1);
        }
        matrix[line][col].addElement(value);
        goalsReachedCount.invalidate();
        goalsReachedCount.get();
    }

    public Cell4Play getCell(int line, int col) {
        return matrix[line][col];
    }

    void play(int line, int col, GameObject value) {
        this.setCell(line, col, value);
        filledPlayerCount.invalidate();
        filledBoxsCount.invalidate();
    }

    public MapProperty<Integer, GameObject> valueProperty(int line, int col) {
        return matrix[line][col].getElementsProperty();
    }

    public boolean isEmpty(int line, int col) {
        return matrix[line][col].isEmpty();
    }

    public LongBinding filledPlayerCountProperty() {return filledPlayerCount;}
    public IntegerProperty moveCountProperty() {return moveCount;}
    public IntegerBinding goalsReachedCountProperty() {return goalsReachedCount;}
    public IntegerBinding filledBoxsCountProperty() {return filledBoxsCount;}
}
