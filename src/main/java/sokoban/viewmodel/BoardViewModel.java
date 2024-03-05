package sokoban.viewmodel;

import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import sokoban.model.Board;
import sokoban.model.Grid;

public class BoardViewModel {
    private final GridViewModel gridViewModel;
    private final MenuViewModel menuViewModel;
    private final Board board;

    public BoardViewModel(Board board) {
        this.board = board;
        gridViewModel = new GridViewModel(board);
        menuViewModel = new MenuViewModel(board);
    }

    public int gridWidth() {
        return board.getGrid().getLine();
    }

    public int gridHeight() {
        return board.getGrid().getCol();
    }

    public GridViewModel getGridViewModel() {
        return gridViewModel;
    }
    public MenuViewModel getMenuViewModel() {
        return menuViewModel;
    }

    public int maxFilledCells() {
        return board.maxFilledCells();
    }

    public IntegerProperty filledCellsCountProperty() {
        return new SimpleIntegerProperty(0);
    }


}
