package sokoban.viewmodel;

import javafx.application.Platform;
import javafx.beans.binding.LongBinding;
import javafx.collections.ObservableMap;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import sokoban.model.Board4Design;
import sokoban.model.Board4Play;
import sokoban.model.cell.*;
import sokoban.utils.dialog.DoSave;
import sokoban.utils.dialog.NewFile;
import sokoban.view.BoardView4Design;
import sokoban.view.BoardView4Play;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

public class BoardViewModel4Design extends BoardViewModel {
    private final GridViewModel4Design gridViewModel;
    private final MenuViewModel menuViewModel;
    private final Board4Design board4Design;

    public BoardViewModel4Design(Board4Design board4Design) {
        this.board4Design = board4Design;
        gridViewModel = new GridViewModel4Design(board4Design);
        menuViewModel = new MenuViewModel(board4Design);
    }

    public int gridWidth() {
        return board4Design.getGrid().getLine();
    }

    public int gridHeight() {
        return board4Design.getGrid().getCol();
    }

    public GridViewModel4Design getGridViewModel() {
        return gridViewModel;
    }
    public MenuViewModel getMenuViewModel() {
        return menuViewModel;
    }

    public int maxFilledCells() {
        return board4Design.maxFilledCells();
    }

    public LongBinding filledCellsCountProperty() {
        return board4Design.getGrid().filledCellsCountProperty();
    }
    public LongBinding filledPlayerCountProperty() {return board4Design.getGrid().filledPlayerCountProperty();}
    public LongBinding filledTargetsCountProperty() {return board4Design.getGrid().filledTargetsCountProperty();}
    public LongBinding filledBoxsCountProperty() {return board4Design.getGrid().filledBoxsCountProperty();}

    private void newFileDialog(Stage stage) {
        Optional<Pair<String, String>> newFile = NewFile.NewFile();
        newFile.ifPresent(widthHeight -> {
            Board4Design newBoard4Design = new Board4Design(Integer.parseInt(widthHeight.getKey()),Integer.parseInt(widthHeight.getValue()));
            BoardViewModel4Design vm = new BoardViewModel4Design(newBoard4Design);
            new BoardView4Design(stage, vm);
        });
    }

    public void exit(Stage stage) {
        int result = DoSave.doSave();
        if (result == 0) {
           if (save(stage)) {
               Platform.exit();
               System.exit(0);
           }
        } else if (result == 1) {
            Platform.exit();
            System.exit(0);
        }
    }

    /// Sauvegarde du fichier, return true si la sauvegarde est faite
    public boolean save(Stage stage) {
        File file;
        FileChooser choose = new FileChooser();
        choose.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sokoban Board Files (*.xsb)", "*.xsb"));
        choose.setInitialFileName("level.xsb");
        choose.setInitialDirectory(new File("src/main/resources"));
        file = choose.showSaveDialog(stage);

        String str = fileStringBuilder();
        if (file != null) {
            if (file.getName().endsWith(".xsb")) {
                try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(file), StandardCharsets.UTF_8))) {
                    writer.write(str);
                    stage.setTitle("Sokoban");
                    saveActualBoard();
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

    public void saveActualBoard() {
        board4Design.setActualBoardSave(fileStringBuilder());
    }

    public void openFile(Stage stage) {
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File("src/main/resources"));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sokoban Board Files (*.xsb)", "*.xsb"));
        try {
            File file = chooser.showOpenDialog(stage);
            if (file != null) {
                List<String> lines = Files.readAllLines(file.toPath());
                int line = lines.size();
                int col = lines.get(0).length();

                Board4Design newBoard4Design = new Board4Design(line, col);
                BoardViewModel4Design vm = new BoardViewModel4Design(newBoard4Design);
                new BoardView4Design(stage, vm, file);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier");
        }
    }

    /** Verify is the file has been changed before making an action
     * <p>
     * Type 0 : Open a dialog to create a new file
     * Type 1 : Open a file
     * Type 2 : Change the application title with modified star
     */
    public void fileModified(Stage stage, int type) {
        String oldString = board4Design.getActualBoardSave().getValue();
        if (!fileStringBuilder().contentEquals(oldString)) {
            if (type != 2) {
                int result = DoSave.doSave();
                if (result == 0) {
                    save(stage);
                    switch (type) {
                        case 0 -> newFileDialog(stage);
                        case 1 -> openFile(stage);
                    }
                } else if (result == 1) {
                    switch (type) {
                        case 0 -> newFileDialog(stage);
                        case 1 -> openFile(stage);
                    }
                }
            } else {
                stage.setTitle("Sokoban (*)");
            }
        } else {
            switch (type) {
                case 0 -> newFileDialog(stage);
                case 1 -> openFile(stage);
                case 2 -> stage.setTitle("Sokoban");
            }
        }
    }

    private String fileStringBuilder() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < board4Design.getGrid().getLine(); i++) {
            for (int j = 0; j < board4Design.getGrid().getCol(); j++) {
                ObservableMap<Integer, GameObject> stack = gridViewModel.getCellViewModel(i, j).valueProperty().getValue();
                if (stack.get(0) instanceof Ground) {
                    if (stack.get(1) instanceof Box) {
                        if (stack.get(2) instanceof Target) { //instance box target
                            str.append('*');
                        } else { //instance de box
                            str.append('$');
                        }
                    } else if (stack.get(1) instanceof Player){
                        if (stack.get(2) instanceof Target) { //instance de player target
                            str.append('+');
                        } else { //instance de player
                            str.append('@');
                        }
                    } else if (stack.get(2) instanceof Target) {
                        str.append('.');
                    } else { // instance de ground
                        str.append(' ');                    }
                } else { //Instance de wall
                    str.append('#');                }

                if (j == board4Design.getGrid().getCol() - 1)
                    str.append('\n');
            }
        }
        return str.toString();
    }

    public void playButton(Stage primaryStage) {
        int line = board4Design.getGrid().getLine();
        int col = board4Design.getGrid().getCol();

        Board4Play board4Play = new Board4Play(line,col);
        board4Play.setActualBoardSave(fileStringBuilder());
        BoardViewModel4Play vm = new BoardViewModel4Play(board4Play);
        new BoardView4Play(primaryStage, vm);
    }
}
