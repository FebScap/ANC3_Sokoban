package sokoban.viewmodel.design;

import sokoban.model.design.Board4Design;
import sokoban.viewmodel.api.GridViewModel;

public class GridViewModel4Design extends GridViewModel {
    private final Board4Design board4Design;

    GridViewModel4Design(Board4Design board4Design) {
        this.board4Design = board4Design;
    }

    public CellViewModel4Design getCellViewModel(int line, int col) {
        return new CellViewModel4Design(line, col, board4Design);
    }
}
