package sokoban.view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sokoban.model.Board;
import sokoban.viewmodel.BoardViewModel;

import java.io.File;
import java.util.Objects;

public class BoardView extends BorderPane {
    // ViewModel
    private final BoardViewModel boardViewModel;

    // Constantes de mise en page
    private static final int SCENE_MIN_WIDTH = 800;
    private static final int SCENE_MIN_HEIGHT = 600;

    // Composants principaux
    private final Label headerLabel = new Label("");

    private final Label errorPlayer = new Label("A player is required");
    private final Label errorBox = new Label("A box is required");
    private final Label errorTarget = new Label("A Target is required");
    private final Label errorBoxesTargets = new Label("Number of boxes and targets must be equal");

    private final VBox headerBox = new VBox();
    private final MenuBar menuBar = new MenuBar();
    private final Menu menuFile = new Menu("File");

    public BoardView(Stage primaryStage, BoardViewModel boardViewModel) {
        this.boardViewModel = boardViewModel;
        start(primaryStage, null);
    }

    public BoardView(Stage primaryStage, BoardViewModel boardViewModel, File file) {
        this.boardViewModel = boardViewModel;
        start(primaryStage, file);
    }

    private void start(Stage stage, File file) {
        // Mise en place des composants principaux
        configMainComponents(stage, file);

        // Mise en place de la scène et affichage de la fenêtre
        Scene scene = new Scene(this, SCENE_MIN_WIDTH, SCENE_MIN_HEIGHT);
        String cssFile = Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm();
        scene.getStylesheets().add(cssFile);

        stage.setScene(scene);
        stage.show();
        stage.setMinHeight(stage.getHeight());
        stage.setMinWidth(stage.getWidth());

        //creation et suppression du fichier temporaire
        boardViewModel.save(stage, false);
        stage.setOnCloseRequest(e -> boardViewModel.deleteTempFile());
    }

    private void configMainComponents(Stage stage, File file) {
        stage.setTitle("Sokoban");
        this.setOnMouseClicked(e -> boardViewModel.fileModified(stage, 2));
        createGrid(file);
        createHeader(stage);
    }

    private void createHeader(Stage stage) {
        MenuItem menuItemNew = new MenuItem("New..."),
                menuItemOpen = new MenuItem("Open..."),
                menuItemSave = new MenuItem("Save As..."),
                menuItemExit = new MenuItem("Exit");
        menuFile.getItems().addAll(menuItemNew, menuItemOpen, menuItemSave, menuItemExit);

        //LISTENER DU MENU
        menuItemNew.setOnAction(e -> boardViewModel.fileModified(stage, 0));
        menuItemOpen.setOnAction(e -> boardViewModel.fileModified(stage, 1));
        menuItemSave.setOnAction(e -> boardViewModel.save(stage, true));
        menuItemExit.setOnAction(e -> boardViewModel.exit(stage));
        menuBar.getMenus().add(menuFile);

        headerLabel.textProperty().bind(boardViewModel.filledCellsCountProperty()
                .asString("Number of filled cells: %d of " + boardViewModel.maxFilledCells()));
        headerLabel.getStyleClass().add("header");

        headerBox.getChildren().add(menuBar);
        headerBox.getChildren().add(headerLabel);
        headerBox.setAlignment(Pos.CENTER);
        setTop(headerBox);


        //errorPlayer.textProperty().bind(boardViewModel.filledPlayerCountProperty().asString("A player is required"));

        errorPlayer.setTextFill(Color.INDIANRED);
        errorPlayer.setVisible(false);
        errorPlayer.setManaged(false);

        errorBox.setTextFill(Color.INDIANRED);
        errorBox.setVisible(false);
        errorBox.setManaged(false);

        errorTarget.setTextFill(Color.INDIANRED);
        errorTarget.setVisible(false);
        errorTarget.setManaged(false);

        errorBoxesTargets.setTextFill(Color.INDIANRED);
        errorBoxesTargets.setVisible(false);
        errorBoxesTargets.setManaged(false);

        errorPlayer.visibleProperty().bind(Bindings.notEqual(boardViewModel.filledPlayerCountProperty(), Board.getNB_OF_PLAYER()));
        errorPlayer.managedProperty().bind(errorPlayer.visibleProperty());

        errorBox.visibleProperty().bind(Bindings.equal(boardViewModel.filledBoxsCountProperty(), Board.getMIN_OF_BOX()));
        errorBox.managedProperty().bind(errorBox.visibleProperty());

        errorTarget.visibleProperty().bind(Bindings.equal(boardViewModel.filledTargetsCountProperty(), Board.getMIN_OF_TARGET()));
        errorTarget.managedProperty().bind(errorTarget.visibleProperty());

        errorBoxesTargets.visibleProperty().bind(Bindings.notEqual(boardViewModel.filledBoxsCountProperty(), boardViewModel.filledTargetsCountProperty()));
        errorBoxesTargets.managedProperty().bind(errorBoxesTargets.visibleProperty());

        headerBox.getChildren().add(errorPlayer);
        headerBox.getChildren().add(errorBox);
        headerBox.getChildren().add(errorTarget);
        headerBox.getChildren().add(errorBoxesTargets);

    }

    private void createGrid(File file) {
        DoubleBinding menuWidth = Bindings.createDoubleBinding(
                ()-> Math.floor(Math.min((heightProperty().doubleValue() - 40),
                        (widthProperty().doubleValue() - 40) / widthProperty().doubleValue())
                ),
                heightProperty(),
                widthProperty());

        DoubleBinding gridWidth = Bindings.createDoubleBinding(
                () -> Math.floor((widthProperty().get() - menuWidth.get())
                        / boardViewModel.gridWidth()) * boardViewModel.gridWidth(),
                menuWidth,
                widthProperty()
        );

        DoubleBinding gridHeight = Bindings.createDoubleBinding(
                () -> Math.floor((heightProperty().get() - headerBox.heightProperty().get())
                        / boardViewModel.gridHeight()) * boardViewModel.gridHeight(),
                heightProperty(),
                headerBox.heightProperty());

        GridView gridView;
        if (file == null) {
            gridView = new GridView(boardViewModel.getGridViewModel(),
                    gridWidth,
                    gridHeight,
                    boardViewModel.gridWidth(),
                    boardViewModel.gridHeight());
        } else {
            gridView = new GridView(boardViewModel.getGridViewModel(),
                    gridWidth,
                    file);
        }

        // Grille carrée
//        gridView.minHeightProperty().bind(gridWidth);
//        gridView.minWidthProperty().bind(gridWidth);
//        gridView.maxHeightProperty().bind(gridWidth);
//        gridView.maxWidthProperty().bind(gridWidth);

        //creation du menu
        MenuView menuView = new MenuView(boardViewModel.getMenuViewModel(), menuWidth);
        menuView.maxWidthProperty().bind(widthProperty());
        menuView.maxHeightProperty().bind(heightProperty().subtract(headerBox.heightProperty()));

        menuView.setAlignment(Pos.CENTER);
        gridView.setAlignment(Pos.CENTER);

        setLeft(menuView);
        setCenter(gridView);
    }
}
