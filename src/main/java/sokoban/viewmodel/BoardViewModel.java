package sokoban.viewmodel;

import sokoban.model.Board;
import sokoban.model.Grid;

public class BoardViewModel {
    private final GridViewModel gridViewModel;
    private final Board board;

    public BoardViewModel(Board board) {
        this.board = board;
        gridViewModel = new GridViewModel(board);
    }

    public int gridWidth() {
        return board.getGrid().getWidth();
    }

    public int gridHeight() {
        return board.getGrid().getHeight();
    }

    public GridViewModel getGridViewModel() {
        return gridViewModel;
    }

    public int maxFilledCells() {
        return board.maxFilledCells();
    }
}
