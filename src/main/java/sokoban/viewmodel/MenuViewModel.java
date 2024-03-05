package sokoban.viewmodel;

import sokoban.model.Board;
import sokoban.model.Cell.CellValue;

public class MenuViewModel {
    private final Board board;
    private static CellValue actualValue;

    MenuViewModel(Board board) {
        this.board = board;
    }
    public ToolViewModel getToolViewModel(int line) {
        return new ToolViewModel(line, this);
    }

    public void select(CellValue value) {
        actualValue = value;
    }
}
