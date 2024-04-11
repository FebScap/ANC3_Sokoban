package sokoban.viewmodel.play;

import javafx.beans.binding.LongBinding;
import sokoban.model.play.Board4Play;
import sokoban.viewmodel.api.BoardViewModel;

public class BoardViewModel4Play extends BoardViewModel {
    private final GridViewModel4Play gridViewModel4Play;
    private final Board4Play board4Play;

    public BoardViewModel4Play(Board4Play board4Play) {
        this.board4Play = board4Play;
        gridViewModel4Play = new GridViewModel4Play(board4Play);
    }

    public int gridWidth() {
        return board4Play.getGrid().getLine();
    }

    public int gridHeight() {
        return board4Play.getGrid().getCol();
    }

    public GridViewModel4Play getGridViewModel4Play() {
        return gridViewModel4Play;
    }


    public LongBinding filledPlayerCountProperty() {return board4Play.getGrid().filledPlayerCountProperty();}



}
