package sokoban.viewmodel.play;

import sokoban.model.play.Board4Play;
import sokoban.viewmodel.api.GridViewModel;

public class GridViewModel4Play extends GridViewModel {
    private final Board4Play board4Play;

    GridViewModel4Play(Board4Play board4Play) {
        this.board4Play = board4Play;
    }

    public CellViewModel4Play getCellViewModel(int line, int col) {
        return new CellViewModel4Play(line, col, board4Play);
    }
}
