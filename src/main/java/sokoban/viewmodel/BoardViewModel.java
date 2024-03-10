package sokoban.viewmodel;

import javafx.application.Platform;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import sokoban.model.Board;
import sokoban.model.Cell.Cell;
import sokoban.model.Cell.CellValue;
import sokoban.model.Grid;
import sokoban.utils.DialogWindow;
import sokoban.view.BoardView;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

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


    public void NewItem(Stage stage) {
        Optional<Pair<String, String>> newFile = DialogWindow.NewFile();
        newFile.ifPresent(widthHeight -> {
            Board newBoard = new Board(Integer.parseInt(widthHeight.getKey()),Integer.parseInt(widthHeight.getValue()));
            BoardViewModel vm = new BoardViewModel(newBoard);
            new BoardView(stage, vm);
            //TODO : Corriger la création de la nouvelle scene (out of bound) + validations
        });
    }
    public void Exit(Stage stage) {
        int result = DialogWindow.doSave();
        if (result == 0) {
           if (Save(stage)) {
               Platform.exit();
               System.exit(0);
           }
        } else if (result == 1) {
            Platform.exit();
            System.exit(0);
        }
    }

    public boolean Save(Stage stage) {
        FileChooser choose = new FileChooser();
        choose.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sokoban Board Files (*.xsb)", "*.xsb"));
        choose.setInitialFileName("level.xsb");
        choose.setInitialDirectory(new File("src/main/resources"));
        File file = choose.showSaveDialog(stage);

        StringBuilder str = new StringBuilder();
        for (int i = 0; i < board.getGrid().getLine(); i++) {
            for (int j = 0; j < board.getGrid().getCol(); j++) {
                CellValue value = gridViewModel.getCellViewModel(i, j).valueProperty().getValue();
                switch (value) {
                    case GROUND -> str.append(' ');
                    case WALL -> str.append('#');
                    case TARGET -> str.append('.');
                    case BOX -> str.append('$');
                    case BOX_TARGET -> str.append('*');
                    case PLAYER -> str.append('@');
                    case PLAYER_TARGET -> str.append('+');
                }
                if (j == board.getGrid().getCol() - 1)
                    str.append('\n');
            }
        }
        if (file != null) {
            if (file.getName().endsWith(".xsb")) {
                try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(file), StandardCharsets.UTF_8))) {
                    writer.write(str.toString());
                    return true;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw new RuntimeException(file.getName() + " has no valid file-extension.");
            }
        }
        return false;
    }

    public void OpenFile() {
        //TODO : Open File
    }
}
