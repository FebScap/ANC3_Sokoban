package sokoban.viewmodel.play;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.LongBinding;
import javafx.beans.property.IntegerProperty;
import javafx.stage.Stage;
import sokoban.model.design.Board4Design;
import sokoban.model.play.Board4Play;
import sokoban.view.design.BoardView4Design;
import sokoban.viewmodel.api.BoardViewModel;
import sokoban.viewmodel.design.BoardViewModel4Design;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

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


    public void finishButton(Stage primaryStage) {
        File file = new File("src/main/resources/playing.xsb");
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            int line = lines.size();
            int col = lines.get(0).length();
            Board4Design board4Design = new Board4Design(line, col);
            BoardViewModel4Design vm = new BoardViewModel4Design(board4Design);
            new BoardView4Design(primaryStage, vm, file);
            file.delete();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void movePlayer(int direction) {board4Play.movePlayer(direction);
    }
    public IntegerProperty getMoveCount() {return board4Play.getGrid().moveCountProperty();}
    public IntegerBinding getGoalsReachedCount() {return board4Play.getGrid().goalsReachedCountProperty();}
    public IntegerBinding getFilledBoxsCountProperty() {return board4Play.getGrid().filledBoxsCountProperty();}
    public BooleanBinding getVictoryProperty() {return board4Play.victoryProperty();}

    public Boolean deleteTempFile() {
        File file = new File("src/main/resources/playing.xsb");
        return file.delete();
    }
}
